package com.prmati.crawler.test;

import com.pramati.crawler.exceptions.BusinesssException;
import com.pramati.crawler.model.DocumentContainer;
import com.pramati.crawler.service.facade.HandleCrawlFacade;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class BasicCrawlerTest extends AbstractBaseTest {

    @Autowired
    HandleCrawlFacade crawlFacade;

    @Test
    public void testDate(){
        boolean b = crawlFacade.validateInput("13/2014");
        assertEquals(false,b);
    }

    @Test
    public void testDate1(){
        boolean b = crawlFacade.validateInput("10/2014");
        assertEquals(true,b);
    }

    @Test
    public void testDate2(){
        boolean b = crawlFacade.validateInput("1/2014");
        assertEquals(true,b);
    }

    @Test
    public void testDate3(){
        boolean b = crawlFacade.validateInput("0/2014");
        assertEquals(false,b);
    }

    @Test
    public void testDate4(){
        boolean b = crawlFacade.validateInput("10/2200");
        assertEquals(false,b);
    }

    @Test
    public void testDate5(){
        boolean b = crawlFacade.validateInput("10/1899");
        assertEquals(false,b);
    }

    @Test
    public void linkBasedOnDateTest() throws IOException, BusinesssException, URISyntaxException {
        URI path = this.getClass().getClassLoader().getResource("mail_boxes.html").toURI();
        File input = new File(path);
        Document doc = Jsoup.parse(input,"UTF-8");
        Date dt = crawlFacade.parseStringToDate("12/2015");
        DocumentContainer dc = new DocumentContainer();
        dc.setDoc(doc);
        String partUrl = crawlFacade.parseMessagesLinkForDateFromDoc(dt,dc);
        assertEquals("http://mail-archives.apache.org/mod_mbox/maven-users/201512.mbox/browser",partUrl);
    }


    /*@Test
    public void mailsNoTest() throws IOException, BusinesssException, URISyntaxException {
        URI path = this.getClass().getClassLoader().getResource("mails.html").toURI();
        File input = new File(path);
        Document doc = Jsoup.parse(input,"UTF-8");
        DocumentContainer dc = new DocumentContainer();
        dc.setDoc(doc);
        int no = crawlFacade.extractElementsFromDoc(dc).size();

        assertEquals(10,no);
    }*/

}
