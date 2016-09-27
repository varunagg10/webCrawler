package com.pramati.crawler.service.impl;

import com.pramati.crawler.service.api.HandleCrawl;
import org.springframework.stereotype.Service;

/**
 * Created by varuna on 27/9/16.
 */
@Service
public class HandleCrawlImpl implements HandleCrawl {

//    @Autowired
//            @Qualifier("webPageDownloadImpl")
//    DocumentDownloader documentDownloader;

    public void parseDocument() {
        System.out.println("parsing");
        //documentDownloader.Download();

    }
}
