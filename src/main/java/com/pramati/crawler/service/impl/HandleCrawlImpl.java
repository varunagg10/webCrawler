package com.pramati.crawler.service.impl;

import com.pramati.crawler.Utils.UserInputHelper;
import com.pramati.crawler.downloader.api.DocumentDownloader;
import com.pramati.crawler.exceptions.BusinesssException;
import com.pramati.crawler.model.DocumentContainer;
import com.pramati.crawler.model.MessageContainer;
import com.pramati.crawler.service.api.HandleCrawl;
import com.pramati.crawler.service.facade.DocumentParsingFacade;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class HandleCrawlImpl implements HandleCrawl {

    static Logger logger = Logger.getLogger(HandleCrawlImpl.class);

    @Value("${maxAttempts}")
    int maxAttempts;

    @Value("${urlToParse}")
    String URL;

    @Autowired
            @Qualifier("webPageDownloadImpl")
    DocumentDownloader documentDownloader;

    @Autowired
    DocumentParsingFacade documentParsingFacade;

    public void parseDocument() throws BusinesssException{
        Date date = getDateFromUser();
        DocumentContainer doc = documentDownloader.download(URL);
        String msgURL = documentParsingFacade.parseMessagesLinkForDateFromDoc(date,doc);

        msgURL = URL+msgURL;
        doc = documentDownloader.download(msgURL);
        List<MessageContainer> messageContainers = documentParsingFacade.extractMessagesFromDoc(doc);

    }

    private Date getDateFromUser() throws BusinesssException {
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
