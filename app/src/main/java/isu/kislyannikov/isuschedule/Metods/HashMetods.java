package isu.kislyannikov.isuschedule.Metods;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class HashMetods {
    public static String bytesToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder(bytes.length * 2);
        Formatter formatter = new Formatter(stringBuilder);
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        return stringBuilder.toString();
    }

    public static String generateHash(String _data) throws NoSuchAlgorithmException {
        String hashCode;

        byte [] byteArray = _data.getBytes();
        byte[] byteHash = MessageDigest.getInstance("SHA-256").digest(byteArray);

        hashCode = HashMetods.bytesToHexString(byteHash);

        return hashCode;
    }

}
