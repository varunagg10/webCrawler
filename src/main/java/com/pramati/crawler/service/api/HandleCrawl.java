package com.pramati.crawler.service.api;

import com.pramati.crawler.exceptions.BusinesssException;

/**
 * Created by varuna on 27/9/16.
 */
public interface HandleCrawl {

    public void parseDocument() throws BusinesssException;
}
