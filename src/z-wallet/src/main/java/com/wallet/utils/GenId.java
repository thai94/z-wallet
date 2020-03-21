package com.wallet.utils;

public class GenId {
    public static String genId() {
        return String.valueOf(System.currentTimeMillis());
    }
}
