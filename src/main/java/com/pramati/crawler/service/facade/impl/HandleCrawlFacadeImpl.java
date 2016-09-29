package com.pramati.crawler.service.facade.impl;

import com.pramati.crawler.downloader.api.DocumentDownloader;
import com.pramati.crawler.exceptions.BusinesssException;
import com.pramati.crawler.model.DocumentContainer;
import com.pramati.crawler.model.MessageContainer;
import com.pramati.crawler.service.facade.HandleCrawlFacade;
import com.pramati.crawler.utils.EncodingHelper;
import com.pramati.crawler.utils.FileIOHelper;
import com.pramati.crawler.utils.UserInputHelper;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class HandleCrawlFacadeImpl implements HandleCrawlFacade {

    private static Logger logger = Logger.getLogger(HandleCrawlFacadeImpl.class);

    private static SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");

    @Value("${maxAttempts}")
    private int maxAttempts;

    @Autowired
    @Qualifier("webPageDownloadImpl")
    private DocumentDownloader documentDownloader;

    public String parseMessagesLinkForDateFromDoc(Date date, DocumentContainer docCont) {
        Document doc = docCont.getDoc();
        String dt = sdf.format(date);
        String nextUrl = doc.select(".date").select(":contains("+dt+")").parents().first().select("a[href]").first().attr("href");
        return nextUrl;
    }

    public MessageContainer extractMessagesFromDoc(String msgsURL, Element element) throws BusinesssException {
        MessageContainer messageContainer= new MessageContainer();
        String msgURL;
        msgURL = msgsURL.split("thread")[0];

        String URL = msgURL+element.attr("href");
        DocumentContainer container= documentDownloader.download(URL);
        Document document = container.getDoc();

        messageContainer.setDate(container.getDoc().select(".date").select(".right").text());
        messageContainer.setSubject(document.select(".subject").select(".right").text());
        messageContainer.setBody(document.select("pre").text());

        return messageContainer;
    }

    public List<Element> extractElementsFromDoc(String msgURL, DocumentContainer docCon) throws BusinesssException {
        List<MessageContainer> messageContainers = null;
        Document doc = docCon.getDoc();
        List<Element> elements = doc.select("a[href*=@]");
        return elements;
    }

    public Date getDateFromUser() throws BusinesssException {
        System.out.println("Please enter the month and year in mm/yyyy format between 1900 to 2199");
        String input = UserInputHelper.inputFromConsole();
        int attempt = 0;

        while(attempt<maxAttempts){
            if(validateInput(input))
                break;
            System.out.println("Please enter the month and year in mm/yyyy format between 1900 to 2199");
            input = UserInputHelper.inputFromConsole();
            attempt++;
        }
        if (attempt==maxAttempts){
            logger.error("User attempts exceeded max attempts,exiting");
            throw new BusinesssException("User attempts exceeded max attempts,exiting");
        }
        return parseStringToDate(input);
    }

    private Date parseStringToDate(String inputDate) throws BusinesssException {
        DateFormat sourceFormat = new SimpleDateFormat("MM/yyyy");
        Date date = null;
        try {
            date = sourceFormat.parse(inputDate);
        } catch (ParseException e) {
            logger.error(e);
            throw new BusinesssException("Exception occured while parsing date "+ inputDate,e);
        }
        return date;
    }

    private boolean validateInput(String input) throws BusinesssException {
        String pattern= "^(0[1-9]|1[0-2])/(19|2[0-1])\\d{2}$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(input);
        return m.matches();
    }
}
