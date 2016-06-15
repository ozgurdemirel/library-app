package com.library.app.commontests.category;

import com.library.app.category.model.Category;
import org.junit.Ignore;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ozgur.demirel on 24.03.2016.
 */
@Ignore
public class CategoryForTestsRepository {

    public static Category java() {
        return new Category("java");
    }

    public static Category cleanCode() {
        return new Category("cleanCode");
    }

    public static Category architecture() {
        return new Category("architecture");
    }

    public static Category networks() {
        return new Category("networks");
    }

    public static Category categoryWithId(Category category,Long id){
        category.setId(id);
        return category;
    }

    public static List<Category> allCategories() {
        return Arrays.asList(java(), cleanCode(), architecture(), networks());
    }

}
