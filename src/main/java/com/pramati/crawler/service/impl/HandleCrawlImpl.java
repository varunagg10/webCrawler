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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HandleCrawlImpl implements HandleCrawl {

    static Logger logger = Logger.getLogger(HandleCrawlImpl.class);

    @Value("${urlToParse}")
    String URL;

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
        DocumentContainer doc = documentDownloader.download(URL);
        String msgURL = handleCrawlFacade.parseMessagesLinkForDateFromDoc(date,doc);

        msgURL = URL+msgURL;
        doc = documentDownloader.download(msgURL);
        List<Element> elements = handleCrawlFacade.extractElementsFromDoc(msgURL,doc);

        for (Element e:elements){
            MessageContainer messageContainer = handleCrawlFacade.extractMessagesFromDoc(msgURL,e);

            writeMsgToFile(messageContainer);
        }
    }

    private void writeMsgToFile(MessageContainer messageContainer) throws BusinesssException {
        String fileName = "";

        fileName = messageContainer.getSubject()+":::"+messageContainer.getDate();

        fileName = filePath +"/" + EncodingHelper.encodeFileName(fileName,encoding);
        FileIOHelper.writeFileToDisk(fileName,messageContainer.getBody());

    }
}
