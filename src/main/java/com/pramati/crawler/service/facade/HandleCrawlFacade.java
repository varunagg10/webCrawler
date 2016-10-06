package com.pramati.crawler.service.facade;

import com.pramati.crawler.exceptions.BusinesssException;
import com.pramati.crawler.model.DocumentContainer;
import com.pramati.crawler.model.MessageContainer;
import org.jsoup.nodes.Element;

import java.util.Date;
import java.util.List;

public interface HandleCrawlFacade {

    public  Date getDateFromUser() throws BusinesssException;

    public Date parseStringToDate(String inputDate) throws BusinesssException;

    public String parseMessagesLinkForDateFromDoc(Date date, DocumentContainer docCon) throws BusinesssException;

    public MessageContainer extractMessagesFromDoc(String msgURL, Element element) throws BusinesssException;

    public List<Element> extractElementsFromDoc( DocumentContainer doc) throws BusinesssException;

    public void writeMsgToFile(MessageContainer messageContainer) throws BusinesssException;

    public boolean validateInput(String input);
}
