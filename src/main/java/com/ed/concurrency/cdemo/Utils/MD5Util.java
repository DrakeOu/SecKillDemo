package com.ed.concurrency.cdemo.Utils;

import org.springframework.util.DigestUtils;

import java.security.MessageDigest;

public class MD5Util {

    public static String md5(String src){
        return DigestUtils.md5DigestAsHex(src.getBytes());
    }

    private static final String salt = "1a2b3c";

    public static String inputPassToFormPass(String inputPass) {
        String str = ""+salt.charAt(0)+salt.charAt(2) + inputPass +salt.charAt(5) + salt.charAt(4);
        System.out.println(str);
        return md5(str);
    }

    public static String formPassToDBPass(String formPass, String salt) {
        String str = ""+salt.charAt(0)+salt.charAt(2) + formPass +salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDbPass(String inputPass, String saltDB) {
        String formPass = inputPassToFormPass(inputPass);
        String dbPass = formPassToDBPass(formPass, saltDB);
        return dbPass;
    }
    public static String Md5ForUrl(String url) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(url.getBytes("UTF-8"));
            byte[] b = md5.digest();

            int i;
            StringBuffer buf = new StringBuffer();
            for(int offset = 0, len = b.length; offset < len; offset++) {
                i = b[offset];
                if(i < 0) {
                    i += 256;
                }
                if(i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            url = buf.toString();
            System.out.println("result = " + url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }


    public static void main(String[] args) {
//        String password = "abcdefg123";
//        System.out.println(formPassToDBPass(password, "1a2b3c"));
        String url = "seckill/01";
        System.out.println(Md5ForUrl(url));
        //34b079807e0a4de60524b211ed893dfc
    }

}
