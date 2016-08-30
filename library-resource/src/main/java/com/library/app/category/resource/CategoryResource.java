package com.library.app.category.resource;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.library.app.category.model.Category;
import com.library.app.category.services.CategoryServices;
import com.library.app.common.exceptions.CategoryExistentException;
import com.library.app.common.exceptions.CategoryNotFoundException;
import com.library.app.common.exceptions.FieldNotValidException;
import com.library.app.common.json.JsonUtils;
import com.library.app.common.json.JsonWriter;
import com.library.app.common.json.OperationResultJsonWriter;
import com.library.app.common.model.HttpCode;
import com.library.app.common.model.OperationResult;
import com.library.app.common.model.ResourceMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static com.library.app.common.model.StandartsOperationResults.getOperationResultEsxistent;

@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryResource {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final ResourceMessage RESOURCE_MESSAGE = new ResourceMessage("category");

    @Inject
    CategoryServices categoryServices;
    @Inject
    CategoryJsonConverter categoryJsonConverter;

    @POST
    public Response add(String body) {
        logger.debug("Adding new category with body {}", body);
        Category category = categoryJsonConverter.convertFrom(body);
        HttpCode httpCode = HttpCode.CREATED;
        OperationResult result = null;
        try {
            category = categoryServices.add(category);
            result = OperationResult.success(JsonUtils.getJsonElementWithId(category.getId()));
        } catch (FieldNotValidException e) {
            logger.error("one of the fields of the category is not valid ", e);
            httpCode = HttpCode.VALIDATION_ERROR;
            result = getOperationResultEsxistent(RESOURCE_MESSAGE, "name");
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

    @GET
    @PathParam("{id}")
    public Response findById(@PathParam("id") final Long id) {
        logger.debug("Find category : {] ", id);
        Response.ResponseBuilder responseBuilder = null;
        try {
            final Category category = categoryServices.findById(id);
            final OperationResult operationResult = OperationResult.success(categoryJsonConverter.convertToJsonElement(category));
            responseBuilder = Response.status(HttpCode.OK.getCode()).entity(OperationResultJsonWriter.toJson(operationResult));
            logger.debug("Category found : {} ", category);
        } catch (final CategoryNotFoundException e) {
            logger.error("No category found for id ", id);
            Response.status(HttpCode.NOT_FOUND.getCode());
        }

        return responseBuilder.build();
    }

    @GET
    public Response findAll() {
        logger.debug("find all categories");
        List<Category> categories = categoryServices.findAll();
        logger.debug("Found {} categories", categories.size());
        JsonElement jsonWithPagingAndEntries = getJsonElementWithPagingAndEntries(categories);
        return Response.status(HttpCode.OK.getCode()).entity(JsonWriter.writeToString(jsonWithPagingAndEntries)).build();
    }

    private JsonElement getJsonElementWithPagingAndEntries(final List<Category> categories) {
        final JsonObject jsonWithEntriesAndPaging = new JsonObject();

        final JsonObject jsonPaging = new JsonObject();
        jsonPaging.addProperty("totalRecords", categories.size());

        jsonWithEntriesAndPaging.add("paging", jsonPaging);
        jsonWithEntriesAndPaging.add("entries", categoryJsonConverter.convertToJsonElement(categories));

        return jsonWithEntriesAndPaging;
    }


}
