package com.practice.shopping.domain.service.category;

import com.practice.shopping.domain.model.Category;

import java.util.List;

public interface ICategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Category category, Long id);
    void deleteCategoryById(Long id);

    Category getCategoryById2(Long id);
    Category getCategoryByName2(String name);
    List<Category> getAllCategories2();
    Category addCategory2(Category category);
    Category updateCategory2(Category category, Long id);
    void deleteCategoryById2(Long id);

    Category getCategoryById3(Long id);
    Category getCategoryByName3(String name);
    List<Category> getAllCategories3();
    Category addCategory3(Category category);
    Category updateCategory3(Category category, Long id);
    void deleteCategoryById3(Long id);
}
