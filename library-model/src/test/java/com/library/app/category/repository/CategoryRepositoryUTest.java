package com.library.app.category.repository;

import com.library.app.category.model.Category;
import com.library.app.commontests.utils.DBCommandTransactionalExecutor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;

import static com.library.app.commontests.category.CategoryForTestsRepository.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class CategoryRepositoryUTest {

    private EntityManagerFactory emf;
    private EntityManager em;
    private CategoryRepository categoryRepository;
    private DBCommandTransactionalExecutor dbCommandExecutor;

    @Before
    public void initTestCase() {
        emf = Persistence.createEntityManagerFactory("libraryPU");
        em = emf.createEntityManager();
        categoryRepository = new CategoryRepository();
        categoryRepository.em = this.em;
        dbCommandExecutor = new DBCommandTransactionalExecutor(em);
    }

    @After
    public void closeEntityManager() {
        em.close();
        emf.close();
    }

    @Test
    public void addCategoryAndFindIt() {
        Long categoryAddedId = dbCommandExecutor.executeCommand(() -> categoryRepository.add(java()).getId());
        assertThat(categoryAddedId, is(notNullValue()));
        Category category = categoryRepository.findById(categoryAddedId);
        assertThat(category, is(notNullValue()));
        assertThat(category.getName(), is(equalTo(java().getName())));
    }

    @Test
    public void findCategoryByIdNotFound() {
        Category category = categoryRepository.findById(999L);
        assertThat(category, is(nullValue()));
    }

    @Test
    public void findCategoryByIdNullValue() {
        Category category = categoryRepository.findById(null);
        assertThat(category, is(nullValue()));
    }

    @Test
    public void updateCategory() {
        final Long categoryAddedId = dbCommandExecutor.executeCommand(() ->
                categoryRepository.add(java()).getId()
        );
        Category categoryAfterAdded = categoryRepository.findById(categoryAddedId);
        assertThat(categoryAfterAdded.getName(), is(equalTo(java().getName())));

        categoryAfterAdded.setName(cleanCode().getName());
        dbCommandExecutor.executeCommand(() -> {
            categoryRepository.update(categoryAfterAdded);
            return null;
        });
        Category categoryAfterUpdate = categoryRepository.findById(categoryAddedId);
        assertThat(categoryAfterUpdate.getName(), is(equalTo(cleanCode().getName())));
    }

    @Test
    public void findAllCategories() {
        dbCommandExecutor.executeCommand(() -> {
            allCategories().forEach(categoryRepository::add);
            return null;
        });
        List<Category> categories = categoryRepository.findAll("name");
        assertThat(categories.size(), is(equalTo(4)));
        assertThat(categories.get(0).getName(), is(equalTo(architecture().getName())));
        assertThat(categories.get(1).getName(), is(equalTo(cleanCode().getName())));
        assertThat(categories.get(2).getName(), is(equalTo(java().getName())));
        assertThat(categories.get(3).getName(), is(equalTo(networks().getName())));
    }

    @Test
    public void allReadyExistForAdd() {
        dbCommandExecutor.executeCommand(() -> categoryRepository.add(java()));
        assertThat(categoryRepository.allReadyExist(java()), is(equalTo(true)));
        assertThat(categoryRepository.allReadyExist(cleanCode()), is(equalTo(false)));
    }

    @Test
    public void existById() {
        Long categoryAddedId = dbCommandExecutor.executeCommand(() -> categoryRepository.add(java()).getId());
        assertThat(categoryRepository.existById(categoryAddedId),is(equalTo(true)));
        assertThat(categoryRepository.existById(999L),is(equalTo(false)));
    }

    @Test
    public void addValidCategory(){
        Long command = dbCommandExecutor.executeCommand(() -> categoryRepository.add(cleanCode()).getId());
        assertNotNull(command);
    }


}
