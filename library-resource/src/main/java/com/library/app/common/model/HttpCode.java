package com.library.app.common.model;

/**
 * Created by ozgur on 22.05.2016.
 */
public enum HttpCode {
    CREATED(201),
    VALIDATION_ERROR(422),
    OK(200),
    NOT_FOUND(404)
    ;

    private int code;

    HttpCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
