package com.library.app.category.repository;

import com.library.app.category.model.Category;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by ozgur.demirel on 24.03.2016.
 */
public class CategoryRepository {
    EntityManager em;

    public Category add(Category category) {
        em.persist(category);
        return category;
    }

    public Category findById(Long categoryAddedId) {
        if (categoryAddedId == null) {
            return null;
        }
        return em.find(Category.class, categoryAddedId);
    }

    public void update(Category categoryAfterAdded) {
        em.merge(categoryAfterAdded);
    }

    public List<Category> findAll(String orderField) {
        return (List<Category>) em.createQuery("Select c from Category c order by c." + orderField).getResultList();
    }

    public boolean allReadyExist(Category category) {
        Query query = em.createQuery("select 1 from Category c where c.name = :name");
        query.setParameter("name", category.getName());
        return query.setMaxResults(1).getResultList().size() > 0;
    }

    public Boolean existById(Long categoryAddedId) {
        boolean existence = em.createQuery("select 1 from Category c where c.id=:id").setParameter("id", categoryAddedId).setMaxResults(1).getResultList().size() > 0;
        return existence;
    }
}
