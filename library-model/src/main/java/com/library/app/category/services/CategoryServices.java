package com.library.app.category.services;

import com.library.app.category.model.Category;
import com.library.app.common.exceptions.CategoryExistentException;
import com.library.app.common.exceptions.CategoryNotFoundException;
import com.library.app.common.exceptions.FieldNotValidException;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by ozgur.demirel on 28.03.2016.
 */
@Local
public interface CategoryServices {
    Category add(Category category) throws FieldNotValidException,CategoryExistentException;

    void update(Category category) throws FieldNotValidException , CategoryNotFoundException;

    Category findById(Long l) throws CategoryNotFoundException;

    List<Category> findAll();
}
