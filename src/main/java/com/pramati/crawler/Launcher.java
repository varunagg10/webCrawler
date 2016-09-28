package com.pramati.crawler;

import com.pramati.crawler.exceptions.BusinesssException;
import com.pramati.crawler.service.api.HandleCrawl;
import com.pramati.crawler.service.impl.HandleCrawlImpl;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;


@Component
public class Launcher {

    static Logger logger = Logger.getLogger(Launcher.class);

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        HandleCrawl handleCrawl = (HandleCrawlImpl)ctx.getBean("handleCrawlImpl");
        try {
            handleCrawl.parseDocument();
        } catch (BusinesssException e) {
            logger.error(e);
        }finally {
            ctx.close();
        }
    }
}