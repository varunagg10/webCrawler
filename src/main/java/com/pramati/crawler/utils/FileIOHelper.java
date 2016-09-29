package com.pramati.crawler.utils;

import com.pramati.crawler.exceptions.BusinesssException;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileIOHelper {

    private static Logger logger = Logger.getLogger(FileIOHelper.class);

    public static void writeFileToDisk(String fileName,String data) throws BusinesssException {
        FileOutputStream outputStream =null;
        logger.debug("writing file :"+ fileName);
        try {
            outputStream = new FileOutputStream(fileName);
            outputStream.write(data.getBytes());
        } catch (FileNotFoundException e) {
            logger.error("Error while opening output stream to file",e);
            throw new BusinesssException("Error while opening output stream to file",e);
        } catch (IOException e) {
            logger.error("Error while writing to file",e);
            throw new BusinesssException("Error while writing to file",e);
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
            }
        }
        logger.debug("writing file:STAUS:SUCCESS :"+ fileName);
    }
}
