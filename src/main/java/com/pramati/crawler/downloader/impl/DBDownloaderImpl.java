package com.pramati.crawler.downloader.impl;


import com.pramati.crawler.downloader.api.DocumentDownloader;
import com.pramati.crawler.model.DocumentContainer;

/**
 * Created by varuna on 27/9/16.
 */

public class DBDownloaderImpl  implements DocumentDownloader{


    public DocumentContainer download(String source) {
        return new DocumentContainer();
    }
}
