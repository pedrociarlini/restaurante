package br.com.marcao.helpers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptHelper {

    private static MessageDigest md;

    static {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String convertToMd5(String value) {
        md.update(value.getBytes());
        byte[] hashMd5 = md.digest();
         return stringHexa(hashMd5);
        
    }

    private static String stringHexa(byte[] bytes) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int parteAlta = ((bytes[i] >> 4) & 0xf) << 4;
            int parteBaixa = bytes[i] & 0xf;
            if (parteAlta == 0) s.append('0');
            s.append(Integer.toHexString(parteAlta | parteBaixa));
        }
        return s.toString();
     }
}
