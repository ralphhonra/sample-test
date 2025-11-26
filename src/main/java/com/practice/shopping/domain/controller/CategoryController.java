package com.practice.shopping.domain.controller;

import com.practice.shopping.domain.model.Category;
import com.practice.shopping.domain.response.ApiResponse;
import com.practice.shopping.domain.service.category.ICategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    private final ICategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();

        return ResponseEntity.ok(new ApiResponse("Item found.", categories));
    }

    @GetMapping("/category/{id}/category")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);

        return ResponseEntity.ok(new ApiResponse("Item found.", category));
    }

    @GetMapping("/category/{name}/category")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name) {
        Category category = categoryService.getCategoryByName(name);

        return ResponseEntity.ok(new ApiResponse("Item found.", category));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody List<Category> categories) {
        List<Category> temp = new ArrayList<>();
        for (Category category : categories) {
            temp.add(categoryService.addCategory(category));
        }

        return ResponseEntity.ok(new ApiResponse("Item added.", temp));
    }

    @DeleteMapping("/category/{id}/delete")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);

        return ResponseEntity.ok(new ApiResponse("Item deleted.", null));
    }

    @PutMapping("/category/{id}/update")
    public ResponseEntity<ApiResponse> updateCategory(
            @PathVariable Long id,
            @RequestBody Category category
    ) {
        categoryService.updateCategory(category, id);

        return ResponseEntity.ok(new ApiResponse("Item updated", null));
    }
}
