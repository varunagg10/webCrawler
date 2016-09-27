package com.pramati.crawler.downloader.impl;

import com.pramati.crawler.downloader.api.DocumentDownloader;
import org.springframework.stereotype.Component;

/**
 * Created by varuna on 27/9/16.
 */
@Component
public class WebPageDownloadImpl implements DocumentDownloader{

    public void Download() {
        System.out.println("crawling web");
    }
}
