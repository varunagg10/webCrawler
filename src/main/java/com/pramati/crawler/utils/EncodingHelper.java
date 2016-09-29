package com.pramati.crawler.utils;

import com.pramati.crawler.exceptions.BusinesssException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class EncodingHelper {

    private static Logger logger = Logger.getLogger(EncodingHelper.class);

    public static String encodeFileName(String fileName,String encoding) throws BusinesssException {
        try {
            fileName = URLEncoder.encode(fileName,encoding);
        } catch (UnsupportedEncodingException e) {
            logger.error("Unsupported format for URL encodeing :"+encoding,e);
            throw new BusinesssException("Unsupported format for URL encodeing :"+encoding,e);
        }
        return  fileName;
    }
}
