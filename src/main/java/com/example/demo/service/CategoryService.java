package com.example.demo.service;

import com.example.demo.modal.Category;

import java.util.List;

public interface CategoryService {
   List<Category> getCategories();
   void createCategory(Category category);
   String deleteCategory(String categoryId);
   String updateCategory(String categoryId, Category categoryItem);
}
