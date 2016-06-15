package com.library.app.common.exceptions;

/**
 * Created by ozgur.demirel on 28.03.2016.
 */
public class FieldNotValidException extends RuntimeException {

    private String fieldName;

    public FieldNotValidException(String fieldName, String message) {
        super(message);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    @Override
    public String toString() {
        return "FieldNotValidException{" +
                "fieldName='" + fieldName + '\'' +
                '}';
    }
}
