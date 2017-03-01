/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nguyen
 */
public class HashingUtil {
    public static String generateHash(String original, String algorithm) {
        String hash = null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(original.getBytes("UTF-8"));
            byte[] bytes = md.digest();
            hash = String.format("%064x", new java.math.BigInteger(1, bytes));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(HashingUtil.class.getName()).log(Level.SEVERE, null, ex);
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
