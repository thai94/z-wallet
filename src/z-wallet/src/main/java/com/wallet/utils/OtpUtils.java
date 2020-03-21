package com.wallet.utils;

import java.util.Random;

public class OtpUtils {

    public static String generateOTP() {
        int length = 6;
        Random obj = new Random();
        char[] otp = new char[length];
        for (int i=0; i<length; i++)
        {
            otp[i]= (char)(obj.nextInt(10)+48);
        }
        return String.valueOf(otp);
    }
}
