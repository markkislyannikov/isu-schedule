package isu.kislyannikov.isuschedule.Metods;

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
}
