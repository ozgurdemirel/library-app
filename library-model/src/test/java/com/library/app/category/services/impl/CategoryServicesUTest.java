package com.library.app.category.services.impl;

import com.library.app.category.model.Category;
import com.library.app.category.repository.CategoryRepository;
import com.library.app.category.services.CategoryServices;
import com.library.app.common.exceptions.CategoryExistentException;
import com.library.app.common.exceptions.CategoryNotFoundException;
import com.library.app.common.exceptions.FieldNotValidException;
import org.junit.Before;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;
import static com.library.app.commontests.category.CategoryForTestsRepository.*;

/**
 * Created by ozgur.demirel on 28.03.2016.
 */
public class CategoryServicesUTest {

    private CategoryServices categoryServices;
    private Validator validator;
    private CategoryRepository categoryRepository;

    @Before
    public void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        categoryServices = new CategoryServicesImpl();
        categoryRepository = mock(CategoryRepository.class);
        ((CategoryServicesImpl) categoryServices).validator = validator;
        ((CategoryServicesImpl) categoryServices).categoryRepository = categoryRepository;
    }

    @Test
    public void addCategoryWithNullName() {
        try {
            categoryServices.add(new Category());
            fail("An error should be thrown");
        } catch (FieldNotValidException e) {
            assertThat(e.getFieldName(), is(equalTo("name")));
        }
    }

    @Test
    public void addCategoryWithInvalidShortName() {
        try {
            categoryServices.add(new Category("a"));
            fail("An error should be thrown");
        } catch (FieldNotValidException exception) {
            assertThat(exception.getFieldName(), is(equalTo("name")));
        }
    }


    @Test
    public void addCategoryWithInvalidLongName() {
        try {
            categoryServices.add(new Category("this is an invalid long name ............"));
            fail("An error should be thrown");
        } catch (FieldNotValidException exception) {
            assertThat(exception.getFieldName(), is(equalTo("name")));
        }
    }

    @Test(expected = CategoryExistentException.class)
    public void addCategoryWithExistentName() {
        when(categoryRepository.allReadyExist(java())).thenReturn(true);
        categoryServices.add(java());
    }

    @Test
    public void addValidCategory() {
        when(categoryRepository.allReadyExist(java())).thenReturn(false);
        when(categoryRepository.add(java())).thenReturn(categoryWithId(java(), 999L));
        Category categoryAdded = categoryServices.add(java());
        assertThat(categoryAdded.getId(), is(equalTo(999L)));
    }

    private void addCategoryWithInvalidName(final String name) {
        try {
            categoryServices.add(new Category(name));
            fail("An error should have been thrown");
        } catch (final FieldNotValidException e) {
            assertThat(e.getFieldName(), is(equalTo("name")));
        }
    }

    private void updateCategoryWithInvalidName(String name) {
        try {
            categoryServices.update(new Category(name));
            fail("An error should been thrown ");
        } catch (FieldNotValidException e) {
            assertThat(e.getFieldName(), is(equalTo("name")));
        }
    }

    @Test
    public void updateWithNullName() {
        updateCategoryWithInvalidName(null);
    }

    @Test
    public void updateCategoryWithShortName() {
        updateCategoryWithInvalidName("A");
    }

    @Test(expected = CategoryNotFoundException.class)
    public void updateCategoryNotFound() {
        when(categoryRepository.allReadyExist(categoryWithId(java(), 1L))).thenReturn(false);
        when(categoryRepository.existById(1L)).thenReturn(false);
        categoryServices.update(categoryWithId(java(), 1L));
    }

    @Test
    public void updateValidCategory() {
        when(categoryRepository.allReadyExist(categoryWithId(java(), 1L))).thenReturn(false);
        when(categoryRepository.existById(1L)).thenReturn(true);
        categoryServices.update(categoryWithId(java(), 1L));
        verify(categoryRepository).update(categoryWithId(java(), 1L));
    }

    @Test
    public void findVCAtegoryById() {
        when(categoryRepository.findById(1L)).thenReturn(categoryWithId(java(), 1L));
        Category category = categoryServices.findById(1L);
        assertThat(category, is(notNullValue()));
        assertThat(category.getId(), is(equalTo(1L)));
        assertThat(category.getName(), is(equalTo(java().getName())));
    }

    @Test(expected = CategoryNotFoundException.class)
    public void findCategoryByIdNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(null);
        categoryServices.findById(1L);
    }

    @Test
    public void findAllNoCategories() {
        when(categoryRepository.findAll("name")).thenReturn(new ArrayList<>());
        List<Category> categories = categoryServices.findAll();
        assertThat(categories.isEmpty(), is(equalTo(true)));
    }

    @Test
    public void findAllCategories() {
        when(categoryRepository.findAll("name"))
                .thenReturn(
                        Arrays.asList(
                                categoryWithId(java(), 1L),
                                categoryWithId(networks(), 2L)
                        )
                );
        List<Category> categories = categoryServices.findAll();
        assertThat(categories.size(), is(equalTo(2)));
        assertThat(categories.get(0).getName(), is(equalTo(java().getName())));
        assertThat(categories.get(1).getName(), is(equalTo(networks().getName())));
    }

}
