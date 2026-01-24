package com.example.demo.service;

import com.example.demo.modal.Category;
import com.example.demo.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    final List<Category> categories = new ArrayList<>();

    @Override
    public List<Category> getCategories() {
        return categories;
    }

    @Override
    public void createCategory(Category category) {
        //this.categories.add(category);
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(String categoryId) {
        Category category = categories.stream()
                .filter(category1 -> category1.getCategoryId().equals(categoryId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));

        categories.remove(category);
        return "Category ID " + categoryId + "delete successfully";
    }

    @Override
    public String updateCategory(String categoryId, Category categoryItem) {
        Category category = categories.stream()
                .filter( c ->  c.getCategoryId().equals(categoryId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));

        category.setCategoryName(categoryItem.getCategoryName());
        return "Resource update successfully";
    }
}
