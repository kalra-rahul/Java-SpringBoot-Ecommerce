package com.example.demo.controller;


import com.example.demo.config.AppConstants;
import com.example.demo.modal.Product;
import com.example.demo.payload.ProductDto;
import com.example.demo.payload.ProductResponse;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
   @Autowired
   private ProductService productService;

   @PostMapping("/admin/categories/{categoryId}/product")
   public ResponseEntity<ProductDto> saveProduct(@Valid @RequestBody ProductDto productDto,
                                                  @PathVariable Long categoryId){
      ProductDto saveProductDto = productService.addProduct(categoryId, productDto);
      return new ResponseEntity<>(saveProductDto, HttpStatus.CREATED);
   }

   @GetMapping("/public/products")
   public ResponseEntity<ProductResponse> getAllProducts(
           @RequestParam(name="pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
           @RequestParam(name="pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
           @RequestParam(name="sortBy",    defaultValue = AppConstants.SORT_PRODUCTS_BY, required = false) String sortBy,
           @RequestParam(name="sortOrder", defaultValue = AppConstants.SORT_ORDER, required = false) String sortOrder
   ){
      ProductResponse productData = productService.getAllProducts(pageNumber, pageSize, sortBy, sortOrder);
      return new ResponseEntity<>(productData, HttpStatus.OK);
   }

   @GetMapping("/public/products/{categoryId}")
   public ResponseEntity<ProductResponse> getProductByCategoryId(@PathVariable Long categoryId){
      ProductResponse products = productService.getProductsByCategoryId(categoryId);
      return new ResponseEntity<>(products, HttpStatus.OK);
   }

   @GetMapping("/public/search/products/{keyword}")
   public ResponseEntity<ProductResponse> searchProductsByKeyword(@PathVariable String keyword){
      ProductResponse products = productService.searchProductsByKeyword(keyword);
      return new ResponseEntity<>(products, HttpStatus.OK);
   }

   @PutMapping("/admin/products/{productId}")
   private ResponseEntity<ProductDto> updateProduct(@PathVariable Long productId, @RequestBody Product product){
      ProductDto products = productService.updateProduct(productId, product);
      return new ResponseEntity<>(products, HttpStatus.OK);
   }   

   @DeleteMapping("/admin/products/{productId}")
   private ResponseEntity<ProductDto> deleteProduct(@PathVariable Long productId){
      ProductDto products = productService.deleteProduct(productId);
      return new ResponseEntity<>(products, HttpStatus.OK);
   }

   @PutMapping("/admin/upload/product/{productId}/image")
   public ResponseEntity<ProductDto> uploadProductImage(@Valid @PathVariable Long productId
                                        ,MultipartFile image
   ) throws IOException {
      ProductDto product = productService.uploadProductImage(productId, image);
      return new ResponseEntity<>(product, HttpStatus.OK);
   }



}
