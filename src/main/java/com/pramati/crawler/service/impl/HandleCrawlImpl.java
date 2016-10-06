package com.pramati.crawler.service.impl;

import com.pramati.crawler.downloader.api.DocumentDownloader;
import com.pramati.crawler.exceptions.BusinesssException;
import com.pramati.crawler.model.DocumentContainer;
import com.pramati.crawler.service.api.HandleCrawl;
import com.pramati.crawler.service.facade.HandleCrawlFacade;
import com.pramati.crawler.service.jobs.DownloadAndSaveMsgJob;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class HandleCrawlImpl implements HandleCrawl {

    private static Logger logger = Logger.getLogger(HandleCrawlImpl.class);

    @Value("${baseURL}")
    private String baseURL;

    @Value("${urlToParse}")
    private String URL;

    @Value("${threadPoolSize}")
    private int threadPoolSize;

    @Autowired
            @Qualifier("webPageDownloadImpl")
    private DocumentDownloader documentDownloader;

    @Autowired
    private HandleCrawlFacade handleCrawlFacade;

    private ExecutorService es;

    @PostConstruct
    private void initExecuters(){
        es = Executors.newFixedThreadPool(threadPoolSize);
    }

    public void parseDocument() throws BusinesssException {
        Date date = handleCrawlFacade.getDateFromUser();
        downloadMessageforMonthAndYear(date);
    }

    public void downloadMessageforMonthAndYear(Date date) throws BusinesssException{
        DocumentContainer doc = documentDownloader.download(baseURL+"/"+URL);
        String msgURL = handleCrawlFacade.parseMessagesLinkForDateFromDoc(date,doc);

        msgURL = baseURL+"/"+URL+msgURL;
        downloadAndSaveMsgsFromPageURL(msgURL);
        es.shutdown();                              //wont shutdown in a live application
        System.out.println("done");
    }

    private void downloadAndSaveMsgsFromPageURL(String msgURL) throws BusinesssException {
        System.out.println("downloading msgs from : "+ msgURL);

        DocumentContainer doc = documentDownloader.download(msgURL);
        List<Element> elements = handleCrawlFacade.extractElementsFromDoc(doc);

        for (Element e:elements){
            es.submit(new DownloadAndSaveMsgJob(msgURL,e,handleCrawlFacade));
        }

        parseIfNextPageExists(doc);
    }

    private void parseIfNextPageExists(DocumentContainer doc) throws BusinesssException {
        Elements nextUrlElement =doc.getDoc().select("a[href]:contains(Next)");
        String nextPageUrl = baseURL;

        if(!nextUrlElement.isEmpty()){
            nextPageUrl += nextUrlElement.first().attr("href");
            downloadAndSaveMsgsFromPageURL(nextPageUrl);
        }
    }
}
