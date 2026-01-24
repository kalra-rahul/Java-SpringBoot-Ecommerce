package com.example.demo.service;

import com.example.demo.modal.Category;
import com.example.demo.modal.Product;
import com.example.demo.payload.ProductDto;
import com.example.demo.payload.ProductResponse;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImp implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modalMapper;

    @Autowired
    private FileService fileService;


    @Override
    public ProductDto addProduct(Long categoryId, ProductDto productDTO){
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category Id not found"));

        Product product = modalMapper.map(productDTO, Product.class);

        List<Product> products = productRepository.findByProductNameLikeIgnoreCase(productDTO.getProductName());

        if(!products.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product already exist");
        }

        product.setImage("default.img");
        product.setCategory(category);
        double specialPrice = product.getPrice() -
                (product.getDiscount() * 0.01) * product.getPrice();
        product.setSpecialPrice(specialPrice);
        Product savedProduct = productRepository.save(product);

        return modalMapper.map(savedProduct, ProductDto.class);
//        ProductDto productDto = new ProductDto();
//        productDto.setProductId(savedProduct.getProductId());
//        productDto.setProductName(savedProduct.getProductName());
//        productDto.setPrice(savedProduct.getPrice());
//        productDto.setDiscount(savedProduct.getDiscount());
//        productDto.setSpecialPrice(savedProduct.getSpecialPrice());
//        productDto.setImage(savedProduct.getImage());
//        return productDto;
    }

    @Override
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        System.out.println(pageNumber + "," + pageSize + ","  + sortBy + "," +  sortOrder);
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Product> pageProducts= productRepository.findAll(pageDetails);

        List<Product> products = pageProducts.getContent();

        List<ProductDto> productDTOS = products.stream()
                .map( product -> modalMapper.map(product, ProductDto.class))
                .toList();

        if(products.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Products List is empty");
        }

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(pageProducts.getNumber());
        productResponse.setPageSize(pageProducts.getSize());
        productResponse.setTotalPages(pageProducts.getTotalPages());
        productResponse.setTotalElements(pageProducts.getTotalElements());
        productResponse.setLastPage(pageProducts.isLast());
        return productResponse;
    }

    @Override
    public ProductResponse getProductsByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category Id not found"));

        List<Product> products = productRepository.findByCategory(category);
        List<ProductDto> productDTOS = products.stream()
                .map( product -> modalMapper.map(product, ProductDto.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductResponse searchProductsByKeyword(String keyword) {
        System.out.println("keyword : "+keyword);
        List<Product> products = productRepository.findByProductNameLikeIgnoreCase("%"+keyword+"%");
        List<ProductDto> productDTOS = products.stream()
                .map( product -> modalMapper.map(product, ProductDto.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductDto updateProduct(Long productId, Product product) {
        Product productFromDb = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product Id not found"));

        productFromDb.setProductName(product.getProductName());
        productFromDb.setDescription(product.getDescription());
        productFromDb.setQuantity(product.getQuantity());
        productFromDb.setDiscount(product.getDiscount());
        productFromDb.setPrice(product.getPrice());
        productFromDb.setSpecialPrice(product.getSpecialPrice());

        Product savedProduct = productRepository.save(productFromDb);

        return modalMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public ProductDto deleteProduct(Long productId) {
        Product productFromDb = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product Id not found"));
        productRepository.delete(productFromDb);
        return modalMapper.map(productFromDb, ProductDto.class);
    }

    @Override
    public ProductDto uploadProductImage(Long productId, MultipartFile image) throws IOException {
        //Get the product from database
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Id not found"));

        //Upload image to server
        //Get the file name of upload image
        String path = "images/";
        String fileName = fileService.uploadImage(path, image);

        //update the new file name to product
        product.setImage(fileName);

        //Save product
        productRepository.save(product);

        // Return Dto after mapping product to Dto
        return modalMapper.map(product, ProductDto.class);
    }
}
