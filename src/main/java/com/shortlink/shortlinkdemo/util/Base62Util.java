package com.shortlink.shortlinkdemo.util;

public class Base62Util {
    private static final String CHARS="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int SCALE=62;

    public static String encode(long num){
        if(num<0){
            throw new IllegalArgumentException("数字不能为负数");

        }
        StringBuilder sb=new StringBuilder();
        do{
            sb.append(CHARS.charAt((int)(num%SCALE)));
            num=num/SCALE;

        }while(num>0);
        return sb.reverse().toString();
    }

    public static long decode(String str){
        long result=0;
        for(int i=0;i<str.length();i++){
            result=result * SCALE +CHARS.indexOf(str.charAt(i));
        }
        return result;
    }


}
