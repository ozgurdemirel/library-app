package com.library.app.category.resource;

import com.google.gson.JsonObject;
import com.library.app.category.model.Category;
import com.library.app.common.json.JsonReader;

/**
 * Created by ozgur on 22.05.2016.
 */
public class CategoryJsonConverter {
    public Category convertFrom(String json) {
        JsonObject jsonObject = JsonReader.readAsJsonObject(json);
        Category category = new Category();
        category.setName(JsonReader.getStringOrNull(jsonObject, "name"));
        return category;
    }

}
