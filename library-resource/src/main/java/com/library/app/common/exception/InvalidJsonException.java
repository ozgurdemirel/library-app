package com.library.app.common.exception;


/**
 * Created by ozgur on 22.05.2016.
 */
public class InvalidJsonException extends RuntimeException {

    private String msg;

    public InvalidJsonException(String msg) {
        this.msg = msg;
    }


    public String getMsg() {
        return msg;
    }
}
