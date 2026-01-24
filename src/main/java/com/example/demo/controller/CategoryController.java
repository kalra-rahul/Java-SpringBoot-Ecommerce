package com.example.demo.controller;

import com.example.demo.modal.Category;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category")
    public ResponseEntity<?> getCategory(){
        try{
            List<Category> categories = this.categoryService.getCategories();
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
        }
    }

    @PostMapping("/category")
    public ResponseEntity<String> setCategory(@RequestBody Category categoryItem){
        try{
            this.categoryService.createCategory(categoryItem);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Category added successfully");

        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
        }
    }

    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable String categoryId){
        try{
            String status = this.categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>(status, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
        }
    }

    @PutMapping("/category/{categoryId}")
    public ResponseEntity<?> updateCategory(
            @PathVariable String categoryId,
            @RequestBody Category categoryItem
    ){
        try{
            String status = this.categoryService.updateCategory(categoryId, categoryItem);
            return new ResponseEntity<>(status, HttpStatus.OK);
        } catch(ResponseStatusException e){
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
        }
    }
}
