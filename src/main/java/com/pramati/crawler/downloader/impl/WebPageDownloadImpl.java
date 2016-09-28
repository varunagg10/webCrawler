package com.pramati.crawler.downloader.impl;

import com.pramati.crawler.downloader.api.DocumentDownloader;
import com.pramati.crawler.exceptions.BusinesssException;
import com.pramati.crawler.model.DocumentContainer;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
public class WebPageDownloadImpl implements DocumentDownloader{

    static Logger logger =Logger.getLogger(WebPageDownloadImpl.class);

    public DocumentContainer download(String source) throws BusinesssException{
        Document doc;
        DocumentContainer container = new DocumentContainer();
        try {
            doc = Jsoup.connect(source).get();
            System.out.println("crawling web " + source);
        }catch(Exception e){
            logger.error(e);
            throw new BusinesssException("Exception occured while downloading document form web URL "+source,e);
        }
        container.setDoc(doc);
        return container;
    }
}
