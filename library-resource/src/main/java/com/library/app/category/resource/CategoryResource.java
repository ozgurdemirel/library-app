package com.library.app.category.resource;

import com.library.app.category.model.Category;
import com.library.app.category.services.CategoryServices;
import com.library.app.common.exceptions.CategoryExistentException;
import com.library.app.common.json.JsonUtils;
import com.library.app.common.json.OperationResultJsonWriter;
import com.library.app.common.model.HttpCode;
import com.library.app.common.model.OperationResult;
import com.library.app.common.model.ResourceMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;

import static com.library.app.common.model.StandartsOperationResults.getOperationResultEsxistent;

/**
 * Created by ozgur on 22.05.2016.
 */
public class CategoryResource {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final ResourceMessage RESOURCE_MESSAGE = new ResourceMessage("category");

    CategoryServices categoryServices;

    CategoryJsonConverter categoryJsonConverter;

    public Response add(String body) {
        logger.debug("Adding new category with body {}", body);
        Category category = categoryJsonConverter.convertFrom(body);
        HttpCode httpCode = HttpCode.CREATED;
        OperationResult result = null;
        try {
            category = categoryServices.add(category);
            result = OperationResult.success(JsonUtils.getJsonElementWithId(category.getId()));
        } catch (CategoryExistentException e) {
            logger.error("There is already a category for the given name");
            httpCode = HttpCode.VALIDATION_ERROR;
            result = getOperationResultEsxistent(RESOURCE_MESSAGE, "name");
        }
        logger.debug("returning the operation result after adding category {}", result);
        return Response
                .status(httpCode.getCode())
                .entity(OperationResultJsonWriter.toJson(result))
                .build();
    }

}
