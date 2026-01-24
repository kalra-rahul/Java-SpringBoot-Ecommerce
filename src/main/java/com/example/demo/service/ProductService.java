package com.example.demo.service;

import com.example.demo.modal.Product;
import com.example.demo.payload.ProductDto;
import com.example.demo.payload.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {

    ProductDto addProduct(Long categoryId, ProductDto product);

    ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductResponse getProductsByCategoryId(Long categoryId);

    ProductDto updateProduct(Long productId, Product product);

    ProductResponse searchProductsByKeyword(String keyword);

    ProductDto deleteProduct(Long productId);

    ProductDto uploadProductImage(Long productId, MultipartFile file) throws IOException;
}
