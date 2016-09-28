package com.pramati.crawler.service.facade;

import com.pramati.crawler.model.DocumentContainer;
import com.pramati.crawler.model.MessageContainer;

import java.util.Date;
import java.util.List;

public interface DocumentParsingFacade {

    public String parseMessagesLinkForDateFromDoc(Date date, DocumentContainer docCon);

    public List<MessageContainer> extractMessagesFromDoc(DocumentContainer docCon);
}
