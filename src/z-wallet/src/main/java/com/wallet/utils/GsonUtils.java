package com.wallet.utils;

import com.google.gson.Gson;

public final class GsonUtils {

    public static final Gson gson = new Gson();

    public static String toJsonString(Object obj) {
        return gson.toJson(obj);
    }
}
