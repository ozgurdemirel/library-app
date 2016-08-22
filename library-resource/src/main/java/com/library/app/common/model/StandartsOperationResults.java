package com.library.app.common.model;

import com.library.app.common.exceptions.FieldNotValidException;

/**
 * Created by ozgur on 18.06.2016.
 */
public final class StandartsOperationResults {

    private StandartsOperationResults() {
    }

    public static OperationResult getOperationResultEsxistent(final ResourceMessage resourceMessage,final String fieldsNames) {
        return OperationResult.error(resourceMessage.getKeyOfResourceExistent(),resourceMessage.getMessageOfResourceExistent(fieldsNames));
    }

    public static OperationResult getOperationResultInvalidField(final ResourceMessage resourceMessage, final FieldNotValidException ex) {
        return OperationResult.error(resourceMessage.getKeyOfInvalidField(ex.getFieldName()),ex.getMessage());
    }

    public static OperationResult getOperationalResultNotFound(final ResourceMessage resourceMessage){
        return OperationResult.error(resourceMessage.getKeyOfResourceNotFound(),resourceMessage.getMessageOfResourceNotFound());
    }

}
