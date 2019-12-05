package com.hanyun.scm.api.utils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class BaseXMLSerializer {

    protected static DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    private static ThreadLocal<DocumentBuilder> sps = new ThreadLocal<>();

    protected DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilder db = sps.get();
        if (db == null) {
            db = factory.newDocumentBuilder();
            sps.set(db);
        }
        return db;
    }
}
