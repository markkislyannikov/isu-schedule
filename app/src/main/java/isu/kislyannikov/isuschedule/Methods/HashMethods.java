package isu.kislyannikov.isuschedule.Methods;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class HashMethods {
    public static String generateHash(String data) throws NoSuchAlgorithmException {
        String hashCode;

        byte[] byteArray = data.getBytes();
        byte[] byteHash  = MessageDigest.getInstance("SHA-256").digest(byteArray);

        hashCode = HashMethods.bytesToHexString(byteHash);
        return hashCode;
    }

    public static String bytesToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder(bytes.length * 2);
        Formatter formatter = new Formatter(stringBuilder);
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        return stringBuilder.toString();
    }
}
