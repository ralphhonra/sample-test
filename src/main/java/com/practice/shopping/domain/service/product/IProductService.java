package com.practice.shopping.domain.service.product;

import com.practice.shopping.domain.dto.ProductDto;
import com.practice.shopping.domain.model.Product;
import com.practice.shopping.domain.request.ConfigProductRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(ConfigProductRequest product);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(ConfigProductRequest product, Long productId);
    List<ProductDto> getAllProducts();
    List<ProductDto> getProductsByCategory(String category);
    List<ProductDto> getProductsByBrand(String brand);
    List<ProductDto> getProductsByCategoryAndBrand(String category, String brand);
    List<ProductDto> getProductsByName(String name);
    List<ProductDto> getProductsByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand, String name);
}
