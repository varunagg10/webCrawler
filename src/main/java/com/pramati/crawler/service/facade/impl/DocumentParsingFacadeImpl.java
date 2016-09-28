package com.pramati.crawler.service.facade.impl;

import com.pramati.crawler.model.DocumentContainer;
import com.pramati.crawler.model.MessageContainer;
import com.pramati.crawler.service.facade.DocumentParsingFacade;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DocumentParsingFacadeImpl implements DocumentParsingFacade {

    static SimpleDateFormat sdf = new SimpleDateFormat("MMM");

    public String parseMessagesLinkForDateFromDoc(Date date, DocumentContainer docCont) {
        Document doc = docCont.getDoc();
        String dt = sdf.format(date);
        String nextUrl = doc.select(".date").select(":contains("+dt+")").parents().first().select("a[href]").first().attr("href");
        return nextUrl;
    }

    public List<MessageContainer> extractMessagesFromDoc(DocumentContainer docCon) {
        List<MessageContainer> messageContainers = new ArrayList<MessageContainer>();
        Document doc = docCon.getDoc();
        List<Element> elements = doc.select("a[href*=@]");
        return messageContainers;
    }
}
