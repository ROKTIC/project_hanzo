package com.ezen.mall.web.common.encription;

public class EzenUtil {


    /*
    encription decription -->
    encryption decryption 으로 수정
     */
    // 암호화
    public static String encryption(String text){
        char[] array = text.toCharArray();
        for (int i = 0; i < array.length; i++) {
            array[i] = (char)(array[i] + 1004);
        }
        return new String(array);
    }

    // 복호화
    public static String decryption(String text){
        char[] array = text.toCharArray();
        for (int i = 0; i < array.length; i++) {
            array[i] = (char)(array[i] - 1004);
        }
        return new String(array);
    }
}
