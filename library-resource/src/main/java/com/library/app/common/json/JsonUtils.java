package com.library.app.common.json;

import com.google.gson.JsonObject;

/**
 * Created by ozgur on 22.05.2016.
 */
public final class JsonUtils {

    private JsonUtils() {
    }

    public static JsonObject getJsonElementWithId(Long id) {
        JsonObject idJson = new JsonObject();
        idJson.addProperty("id", id);
        return idJson;
    }
}
