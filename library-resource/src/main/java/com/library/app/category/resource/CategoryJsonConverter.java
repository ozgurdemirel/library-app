package com.library.app.category.resource;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.library.app.category.model.Category;
import com.library.app.common.json.JsonReader;

import java.util.List;

/**
 * Created by ozgur on 22.05.2016.
 */
public class CategoryJsonConverter {
    public Category convertFrom(final String json) {
        JsonObject jsonObject = JsonReader.readAsJsonObject(json);
        Category category = new Category();
        category.setName(JsonReader.getStringOrNull(jsonObject, "name"));
        return category;
    }

    public JsonElement convertToJsonElement(final Category category) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", category.getId());
        jsonObject.addProperty("name", category.getName());
        return jsonObject;
    }

    public JsonElement convertToJsonElement(final List<Category> categories) {
        JsonArray jsonArray = new JsonArray();
        for (Category category : categories) {
            jsonArray.add(convertToJsonElement(category));
        }
        return jsonArray;
    }
}
