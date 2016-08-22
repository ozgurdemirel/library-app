package com.library.app.common.json;

import com.google.gson.Gson;

/**
 * Created by ozgur on 1.07.2016.
 */
public final class JsonWriter {

    private JsonWriter() {
    }

    public static String writeToString(Object o){
        if (o == null){
            return "";
        }
        final Gson gson = new Gson();
        return gson.toJson(o);
    }
}
