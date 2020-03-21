package com.wallet.utils;

import com.google.gson.Gson;

public final class GsonUtils {

    public final Gson gson = new Gson();

    public String toJsonString(Object obj) {
        return gson.toJson(obj);
    }
}
