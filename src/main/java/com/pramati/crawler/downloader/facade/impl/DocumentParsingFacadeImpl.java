package com.pramati.crawler.downloader.facade.impl;

import com.pramati.crawler.downloader.api.DocumentDownloader;
import com.pramati.crawler.downloader.facade.DocumentParsingFacade;
import com.pramati.crawler.exceptions.BusinesssException;
import com.pramati.crawler.model.DocumentContainer;
import com.pramati.crawler.model.MessageContainer;
import com.pramati.crawler.utils.EncodingHelper;
import com.pramati.crawler.utils.FileIOHelper;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DocumentParsingFacadeImpl implements DocumentParsingFacade {

    private static SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");

    @Value("${filePath}")
    private String filePath;

    @Value("${fileEncoding}")
    private String encoding;

    @Autowired
    @Qualifier("webPageDownloadImpl")
    DocumentDownloader documentDownloader;

    public String parseMessagesLinkForDateFromDoc(Date date, DocumentContainer docCont) {
        Document doc = docCont.getDoc();
        String dt = sdf.format(date);
        String nextUrl = doc.select(".date").select(":contains("+dt+")").parents().first().select("a[href]").first().attr("href");
        return nextUrl;
    }

    public List<Element> extractMessagesFromDoc(String msgURL, DocumentContainer docCon) throws BusinesssException {
        List<MessageContainer> messageContainers = null;
        Document doc = docCon.getDoc();
        List<Element> elements = doc.select("a[href*=@]");

        // messageContainers = extractMessages(msgURL,elements);

        return elements;
    }

    private List<MessageContainer> extractMessages(String msgsURL, List<Element> elements) throws BusinesssException {
        List<MessageContainer> messageContainers = new ArrayList<MessageContainer>();
        String msgURL;
        msgURL = msgsURL.split("thread")[0];

        for (Element e:elements){
            String URL = msgURL+e.attr("href");
            DocumentContainer container= documentDownloader.download(URL);
            Document document = container.getDoc();

            String fileName =  document.select(".subject").select(".right").text()+ container.getDoc().select(".date").select(".right").text();
            String data =   document.select("pre").text();

            fileName = filePath+"/" +EncodingHelper.encodeFileName(fileName,encoding);
            FileIOHelper.writeFileToDisk(fileName,data);

        }
        return messageContainers;
    }
}
