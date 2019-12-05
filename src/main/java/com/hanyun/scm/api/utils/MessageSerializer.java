package com.hanyun.scm.api.utils;

import com.hanyun.scm.api.consts.AliMessageSendConstant;
import com.hanyun.scm.api.domain.alimessage.Message;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.IOException;
import java.io.OutputStream;

public class MessageSerializer extends XMLSerializer<Message> {

    public MessageSerializer() {
        super();
    }

    public String serialize(Message msg, String encoding) throws Exception {
        Document doc = getDocumentBuilder().newDocument();
        Element root = doc.createElementNS(AliMessageSendConstant.DEFAULT_XML_NAMESPACE, AliMessageSendConstant.MESSAGE_TAG);
        doc.appendChild(root);
        Element node = safeCreateContentElement(doc, AliMessageSendConstant.MESSAGE_BODY_TAG, msg.getMessageBodyAsRawString(), "");
        if (node != null) {
            root.appendChild(node);
        }
        node = safeCreateContentElement(doc, AliMessageSendConstant.DELAY_SECONDS_TAG, msg.getDelaySeconds(), null);
        if (node != null) {
            root.appendChild(node);
        }
        node = safeCreateContentElement(doc, AliMessageSendConstant.PRIORITY_TAG, msg.getPriority(), null);
        if (node != null) {
            root.appendChild(node);
        }
        return XmlUtil.xmlNodeToString(doc, encoding);
    }

    @Override
    public void serialize(Message message, OutputStream outputStream) throws IOException {

    }
}
