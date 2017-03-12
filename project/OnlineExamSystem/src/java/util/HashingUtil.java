/*
 * Copyright © 2017 Six Idiots Team
 */
package util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author nguyen
 */
public class HashingUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(HashingUtil.class);
    
    public static String generateHash(String original, String algorithm) {
        String hash = null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(original.getBytes("UTF-8"));
            byte[] bytes = md.digest();
            hash = "";
            for (byte b : bytes) {
                hash += String.format("%02x", b);
            }
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            LOGGER.error(null, ex);
        }
        return hash;
    }

    public static String generateSHA1Hash(String original) {
        return generateHash(original, "SHA-1");
    }

    public static String generateSHA256Hash(String original) {
        return generateHash(original, "SHA-256");
    }

    public static String generateSHA512Hash(String original) {
        return generateHash(original, "SHA-512");
    }
}
