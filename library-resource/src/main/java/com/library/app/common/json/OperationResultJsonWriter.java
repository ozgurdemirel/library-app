package com.library.app.common.json;

import com.google.gson.Gson;
import com.library.app.common.model.OperationResult;

/**
 * Created by ozgur on 22.05.2016.
 */
public final class OperationResultJsonWriter {

    private OperationResultJsonWriter() {
    }

    public static String toJson(OperationResult operationResult) {
        if (operationResult.getEntity() == null) {
            return "";
        }
        Gson gson = new Gson();
        return gson.toJson(operationResult.getEntity());
    }

}
