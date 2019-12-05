package com.hanyun.scm.api.utils;

import org.apache.commons.codec.binary.Base64;

public class BinaryUtil {
    public static String toBase64String(byte[] binaryData) {
        return new String(Base64.encodeBase64(binaryData));
    }
}
