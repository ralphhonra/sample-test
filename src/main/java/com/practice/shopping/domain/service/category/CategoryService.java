package com.practice.shopping.domain.service.category;

import com.practice.shopping.domain.exceptions.AlreadyExistException;
import com.practice.shopping.domain.exceptions.ResourceNotFoundException;
import com.practice.shopping.domain.model.Category;
import com.practice.shopping.domain.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return Optional.ofNullable(categoryRepository.findByName(name))
                .orElseThrow(() -> new ResourceNotFoundException("Category name not found!"));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category).filter(item -> !categoryRepository.existsByName(item.getName()))
                .map(categoryRepository::save)
                .orElseThrow(() -> new AlreadyExistException(category.getName() + " already exists!"));
    }

    @Override
    public Category updateCategory(Category category, Long id) {
        return categoryRepository.findById(id)
                .map(oldCategory -> {
                    oldCategory.setName(category.getName());
                    return categoryRepository.save(oldCategory);
                }).orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.findById(id)
                .ifPresentOrElse(
                        categoryRepository::delete,
                        () -> { throw new ResourceNotFoundException("Category not found!"); }
                );
    }

    @Override
    public Category getCategoryById2(Long id) {
        return null;
    }

    @Override
    public Category getCategoryByName2(String name) {
        return null;
    }

    @Override
    public List<Category> getAllCategories2() {
        return List.of();
    }

    @Override
    public Category addCategory2(Category category) {
        return null;
    }

    @Override
    public Category updateCategory2(Category category, Long id) {
        return null;
    }

    @Override
    public void deleteCategoryById2(Long id) {

    }

    @Override
    public Category getCategoryById3(Long id) {
        return null;
    }

    @Override
    public Category getCategoryByName3(String name) {
        return null;
    }

    @Override
    public List<Category> getAllCategories3() {
        return List.of();
    }

    @Override
    public Category addCategory3(Category category) {
        return null;
    }

    @Override
    public Category updateCategory3(Category category, Long id) {
        return null;
    }

    @Override
    public void deleteCategoryById3(Long id) {

    }
}
