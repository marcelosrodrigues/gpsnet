package com.pmrodrigues.gnsnet.utilities;

import org.springframework.security.crypto.codec.Hex;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Marceloo on 27/09/2014.
 */
public final class MD5 {

    private MD5() {
    }

    public static String encrypt(final String message)
            throws NoSuchAlgorithmException {
        final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.reset();
        messageDigest.update(message.getBytes(Charset.forName("UTF8")));
        return new String(Hex.encode(messageDigest.digest()));
    }
}
