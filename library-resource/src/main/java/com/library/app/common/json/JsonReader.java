package com.library.app.common.json;

import com.google.gson.*;
import com.library.app.common.exception.InvalidJsonException;

/**
 * Author : ozgur.d
 * Using utily claas s to easy switch of Gson - > Jackson or reverse
 */
public class JsonReader {

    public static JsonObject readAsJsonObject(final String json) throws InvalidJsonException {
        return readJsonAs(json, JsonObject.class);
    }

    public static JsonArray readAsJsonArray(final String json) throws InvalidJsonException {
        return readJsonAs(json, JsonArray.class);
    }

    public static <T> T readJsonAs(String json, Class<T> jsonObjectClass) {
        if (json == null || json.trim().isEmpty()) {
            throw new InvalidJsonException("json string can not be Null");
        }
        try {
            return new Gson().fromJson(json, jsonObjectClass);
        } catch (final JsonSyntaxException e) {
            throw new InvalidJsonException(e.getMessage());
        }
    }

    public static Long getLongOrNull(final JsonObject jsonObject, final String propertyName) {
        final JsonElement property = jsonObject.get(propertyName);
        if (isJsonElementNull(jsonObject)) {
            return null;
        }
        return property.getAsLong();
    }


    public static Integer getIntegerOrNull(final JsonObject jsonObject, final String propertyName) {
        final JsonElement property = jsonObject.get(propertyName);
        if (isJsonElementNull(jsonObject)) {
            return null;
        }
        return property.getAsInt();
    }

    public static String getStringOrNull(final JsonObject jsonObject, final String propertyName) {
        final JsonElement property = jsonObject.get(propertyName);
        if (isJsonElementNull(jsonObject)) {
            return null;
        }
        return property.getAsString();
    }

    public static Double getDoubleOrNull(final JsonObject jsonObject, final String propertyName) {
        final JsonElement property = jsonObject.get(propertyName);
        if (isJsonElementNull(jsonObject)) {
            return null;
        }
        return property.getAsDouble();
    }

    public static boolean isJsonElementNull(final JsonObject element) {
        return element == null || element.isJsonNull();
    }

}
