package com.pramati.crawler.service.impl;

import com.pramati.crawler.downloader.api.DocumentDownloader;
import com.pramati.crawler.exceptions.BusinesssException;
import com.pramati.crawler.model.DocumentContainer;
import com.pramati.crawler.model.MessageContainer;
import com.pramati.crawler.service.api.HandleCrawl;
import com.pramati.crawler.service.facade.HandleCrawlFacade;
import com.pramati.crawler.utils.EncodingHelper;
import com.pramati.crawler.utils.FileIOHelper;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HandleCrawlImpl implements HandleCrawl {

    static Logger logger = Logger.getLogger(HandleCrawlImpl.class);

    @Value("${baseURL}")
    private String baseURL;

    @Value("${urlToParse}")
    private String URL;

    @Value("${filePath}")
    private String filePath;

    @Value("${fileEncoding}")
    private String encoding;

    @Autowired
            @Qualifier("webPageDownloadImpl")
    DocumentDownloader documentDownloader;

    @Autowired
    HandleCrawlFacade handleCrawlFacade;

    public void parseDocument() throws BusinesssException{
        Date date = handleCrawlFacade.getDateFromUser();
        DocumentContainer doc = documentDownloader.download(baseURL+"/"+URL);
        String msgURL = handleCrawlFacade.parseMessagesLinkForDateFromDoc(date,doc);

        msgURL = baseURL+"/"+URL+msgURL;
        downloadAndSaveMsgsFromPageURL(msgURL);
        logger.debug("All messages downloaded succesfully");
    }

    private void downloadAndSaveMsgsFromPageURL(String msgURL) throws BusinesssException {
        System.out.println("downloading msgs from : "+ msgURL);
        DocumentContainer doc = documentDownloader.download(msgURL);
        List<Element> elements = handleCrawlFacade.extractElementsFromDoc(msgURL,doc);

        for (Element e:elements){
            MessageContainer messageContainer = handleCrawlFacade.extractMessagesFromDoc(msgURL,e);
            writeMsgToFile(messageContainer);
        }

        parseIfNextPageExists(doc);
    }

    private void parseIfNextPageExists(DocumentContainer doc) throws BusinesssException {
        Elements nextUrlElement =doc.getDoc().select("a[href]:contains(Next)");
        String nextPageUrl = "";

        if(!nextUrlElement.isEmpty()){
            nextPageUrl = nextUrlElement.first().attr("href");
            nextPageUrl = baseURL+nextPageUrl;
            downloadAndSaveMsgsFromPageURL(nextPageUrl);
        }
    }

    private void writeMsgToFile(MessageContainer messageContainer) throws BusinesssException {
        String fileName = "";

        fileName = messageContainer.getSubject()+":::"+messageContainer.getDate();

        fileName = filePath +"/" + EncodingHelper.encodeFileName(fileName,encoding);
        FileIOHelper.writeFileToDisk(fileName,messageContainer.getBody());
    }
}
