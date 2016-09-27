package com.pramati.crawler;

import com.pramati.crawler.service.api.HandleCrawl;
import com.pramati.crawler.service.impl.HandleCrawlImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;

/**
 * Created by varuna on 27/9/16.
 */
@Component
public class Launcher {

    public static void main(String[] args) throws FileNotFoundException {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        ctx.getBeanDefinitionNames();
        HandleCrawl handleCrawl=(HandleCrawlImpl)ctx.getBean("handleCrawlImpl");
        handleCrawl.parseDocument();
    }
}