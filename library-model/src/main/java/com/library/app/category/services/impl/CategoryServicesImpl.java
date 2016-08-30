package com.library.app.category.services.impl;

import com.library.app.category.model.Category;
import com.library.app.category.repository.CategoryRepository;
import com.library.app.category.services.CategoryServices;
import com.library.app.common.exceptions.CategoryExistentException;
import com.library.app.common.exceptions.CategoryNotFoundException;
import com.library.app.common.exceptions.FieldNotValidException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by ozgur.demirel on 28.03.2016.
 */
@Stateless
public class CategoryServicesImpl implements CategoryServices {
    @Inject
    Validator validator;
    @Inject
    CategoryRepository categoryRepository;

    @Override
    public Category add(Category category) {
        validateCategory(category);
        return categoryRepository.add(category);
    }

    @Override
    public void update(Category category) {
        validateCategory(category);
        if (!categoryRepository.existById(category.getId())) {
            throw new CategoryNotFoundException();
        }
        categoryRepository.update(category);
    }

    private void validateCategory(Category category) {
        validateCategoryFields(category);
        if (categoryRepository.allReadyExist(category)) {
            throw new CategoryExistentException();
        }
    }

    private void validateCategoryFields(Category category) {
        Set<ConstraintViolation<Category>> errors = validator.validate(category);
        Iterator<ConstraintViolation<Category>> itErrors = errors.iterator();
        if (itErrors.hasNext()) {
            ConstraintViolation<Category> violation = itErrors.next();
            throw new FieldNotValidException(violation.getPropertyPath().toString(), violation.getMessage());
        }
    }

    @Override
    public Category findById(Long id) throws CategoryNotFoundException {
        Category category = categoryRepository.findById(id);
        if (category == null) {
            throw new CategoryNotFoundException();
        }
        return category;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll("name");
    }
}
