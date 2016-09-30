package com.pramati.crawler.service.jobs;

import com.pramati.crawler.exceptions.BusinesssException;
import com.pramati.crawler.model.MessageContainer;
import com.pramati.crawler.service.facade.HandleCrawlFacade;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;

public class DownloadAndSaveMsgJob implements Runnable {

    Logger logger = Logger.getLogger(DownloadAndSaveMsgJob.class);

    private String url;

    private Element element;

    private HandleCrawlFacade handleCrawlFacade;

    public DownloadAndSaveMsgJob(String url,Element element,HandleCrawlFacade handleCrawlFacade){
        this.url=url;
        this.element=element;
        this.handleCrawlFacade = handleCrawlFacade;
    }

    public void run() {
        try {
            MessageContainer messageContainer = handleCrawlFacade.extractMessagesFromDoc(url,element);
            handleCrawlFacade.writeMsgToFile(messageContainer);
        } catch (BusinesssException e) {
            logger.error("Error while saving file for msg URL :"+ url);
        }
    }
}
