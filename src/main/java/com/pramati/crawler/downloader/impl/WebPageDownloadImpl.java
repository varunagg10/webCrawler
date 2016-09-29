package com.pramati.crawler.downloader.impl;

import com.pramati.crawler.downloader.api.DocumentDownloader;
import com.pramati.crawler.exceptions.BusinesssException;
import com.pramati.crawler.model.DocumentContainer;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class WebPageDownloadImpl implements DocumentDownloader{

    static Logger logger =Logger.getLogger(WebPageDownloadImpl.class);

    @Value("${maxDownloadAttemsts}")
    private int maxDownloadAttemsts;

    public DocumentContainer download(String source) throws BusinesssException{
        Document doc = null;
        DocumentContainer container = new DocumentContainer();
        int attpempt =0;

        logger.debug("downloading from web url:"+source);

        while(attpempt<maxDownloadAttemsts) {
            try {
                doc = Jsoup.connect(source).get();
                break;
            } catch (IOException e) {
                logger.error("Exception occured while downloading document form web URL " + source, e);
            }
            attpempt++;
        }

        if(attpempt==maxDownloadAttemsts){
            logger.error("max attepts count reached from URL: "+ source +" count: "+ attpempt);
            throw new BusinesssException("Exception occured while downloading document form web URL "+source);
        }


        container.setDoc(doc);
        return container;
    }
}
