package com.library.app.category.resource;

import com.library.app.category.model.Category;
import com.library.app.category.services.CategoryServices;
import com.library.app.common.json.JsonUtils;
import com.library.app.common.json.OperationResultJsonWriter;
import com.library.app.common.model.HttpCode;
import com.library.app.common.model.OperationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;

/**
 * Created by ozgur on 22.05.2016.
 */
public class CategoryResource {

    private Logger logger = LoggerFactory.getLogger(getClass());

    CategoryServices categoryServices;

    CategoryJsonConverter categoryJsonConverter;

    public Response add(String body) {
        logger.debug("Adding new category with body {}", body);
        Category category = categoryJsonConverter.convertFrom(body);
        category = categoryServices.add(category);
        OperationResult result = OperationResult.success(
                JsonUtils.getJsonElementWithId(category.getId())
        );
        logger.debug("retuning the operation result after adding category {}", result);
        return Response
                .status(HttpCode.CREATED.getCode())
                .entity(OperationResultJsonWriter.toJson(result))
                .build();
    }

}
