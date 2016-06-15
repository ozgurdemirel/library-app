package com.library.app.common.model;

/**
 * Created by ozgur on 22.05.2016.
 */
public class OperationResult {
    private boolean success;
    private String errorIdentification;
    private String errorDescription;
    private Object entity;

    private OperationResult(Object entity) {
        this.entity = entity;
        this.success = true;
    }

    private OperationResult(String errorIdentification, String errorDescription) {
        this.success = false;
        this.errorIdentification = errorIdentification;
        this.errorDescription = errorDescription;
    }

    public static OperationResult success(Object entity) {
        return new OperationResult(entity);
    }

    public static OperationResult error(final String errorIdentification, final String errorDescription) {
        return new OperationResult(errorIdentification, errorDescription);
    }

    public boolean isSuccess() {
        return success;
    }

    public Object getEntity() {
        return entity;
    }

    public String getErrorIdentification() {
        return errorIdentification;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    @Override
    public String toString() {
        return "OperationResult{" +
                "success=" + success +
                ", errorIdentification='" + errorIdentification + '\'' +
                ", errorDescription='" + errorDescription + '\'' +
                ", entity=" + entity +
                '}';
    }
}
