package com.hanyun.scm.api.utils;

import org.springframework.core.serializer.Serializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class XMLSerializer<T> extends BaseXMLSerializer implements Serializer<T> {

    public Element safeCreateContentElement(Document doc, String tagName,
                                            Object value, String defaultValue) {
        if (value == null && defaultValue == null) {
            return null;
        }

        Element node = doc.createElement(tagName);
        if (value != null) {
            node.setTextContent(value.toString());
        } else {
            node.setTextContent(defaultValue);
        }
        return node;
    }

}
