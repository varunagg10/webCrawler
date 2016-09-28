package com.pramati.crawler.model;

import org.jsoup.nodes.Document;

public class DocumentContainer {

    String docString;
    Document doc;

    public String getDocString() {
        return docString;
    }

    public void setDocString(String docString) {
        this.docString = docString;
    }

    public Document getDoc() {
        return doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }
}
