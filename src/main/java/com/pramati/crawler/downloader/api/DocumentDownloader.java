package com.pramati.crawler.downloader.api;

import com.pramati.crawler.exceptions.BusinesssException;
import com.pramati.crawler.model.DocumentContainer;

public interface DocumentDownloader {


    public DocumentContainer download(String source) throws BusinesssException;
}