package com.library.app.category.resource;


import com.library.app.category.services.CategoryServices;
import com.library.app.common.exceptions.CategoryExistentException;
import com.library.app.common.model.HttpCode;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.Response;

import static com.library.app.commontests.category.CategoryForTestsRepository.categoryWithId;
import static com.library.app.commontests.category.CategoryForTestsRepository.java;
import static com.library.commontests.utils.FileTestNameUtils.getPathFileRequest;
import static com.library.commontests.utils.FileTestNameUtils.getPathFileResponse;
import static com.library.commontests.utils.JsonTestUtils.assertJsonMatchesExpectedJson;
import static com.library.commontests.utils.JsonTestUtils.assertJsonMatchesFileContent;
import static com.library.commontests.utils.JsonTestUtils.readJsonFile;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by ozgur on 22.05.2016....
 */
public class CategoryResourceUTest {

    private static final String PATH_RESOURCE = "categories";

    private CategoryResource categoryResource;

    @Mock
    private CategoryServices categoryServices;

    @Before
    public void initTestCase() {
        MockitoAnnotations.initMocks(this);
        categoryResource = new CategoryResource();
        categoryResource.categoryServices = categoryServices;
        categoryResource.categoryJsonConverter = new CategoryJsonConverter();
    }

    @Test
    public void addValidCategory() {
        when(categoryServices.add(java())).thenReturn(categoryWithId(java(), 1L));
        Response response = categoryResource.add(
                readJsonFile(getPathFileRequest(PATH_RESOURCE, "newCategory.json"))
        );
        assertThat(response.getStatus(), is(equalTo(HttpCode.CREATED.getCode())));
        assertJsonMatchesExpectedJson(response.getEntity().toString(), "{\"id\":1}");
    }

    @Test
    public void addExistentCategory() {
        when(categoryServices.add(java())).thenThrow(new CategoryExistentException());
        Response response = categoryResource.add(
                readJsonFile(
                        getPathFileRequest(PATH_RESOURCE, "newCategory.json")
                )
        );
        assertThat(response.getStatus(), is(equalTo(HttpCode.CREATED.getCode())));
        assertJsonResponseWithFile(response,"categoryExistent.json");
    }


    private void assertJsonResponseWithFile(Response response,String fileName){
        assertJsonMatchesFileContent(response.getEntity().toString(),getPathFileResponse(PATH_RESOURCE,fileName));
    }

}
