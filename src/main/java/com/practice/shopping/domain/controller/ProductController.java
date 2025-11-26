package com.practice.shopping.domain.controller;

import com.practice.shopping.domain.dto.ProductDto;
import com.practice.shopping.domain.model.Product;
import com.practice.shopping.domain.request.ConfigProductRequest;
import com.practice.shopping.domain.response.ApiResponse;
import com.practice.shopping.domain.service.product.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/products")
@AllArgsConstructor
public class ProductController {
    private final IProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts();

        return ResponseEntity.ok(new ApiResponse("Success", products));
    }

    @GetMapping("product/{id}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);

        return ResponseEntity.ok(new ApiResponse("Success", product));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody List<ConfigProductRequest> products) {
        List<Product> temp = new ArrayList<>();
        for (ConfigProductRequest product : products) {
            temp.add(productService.addProduct(product));
        }

        return ResponseEntity.ok(new ApiResponse("Add product success!", temp));
    }

    @PutMapping("/product/{id}/update")
    public ResponseEntity<ApiResponse> updateProduct(
            @RequestBody ConfigProductRequest product,
            @PathVariable Long id
    ) {
        Product updatedProduct = productService.updateProduct(product, id);

        return ResponseEntity.ok(new ApiResponse("Update product success!", updatedProduct));
    }

    @DeleteMapping("/product/{id}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);

        return ResponseEntity.ok(new ApiResponse("Delete product success!", null));
    }

    @GetMapping("/products/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(
            @RequestParam String brand,
            @RequestParam String name
    ) {
        List<ProductDto> product = productService.getProductsByBrandAndName(brand, name);

        if (product.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("No products found", null));

        return ResponseEntity.ok(new ApiResponse("success", product));
    }

    @GetMapping("/products/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(
            @RequestParam String category,
            @RequestParam String brand
    ) {
        List<ProductDto> product = productService.getProductsByCategoryAndBrand(category, brand);

        if (product.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("No products found", null));

        return ResponseEntity.ok(new ApiResponse("success", product));
    }

    @GetMapping("/products/{name}/products")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name) {
        List<ProductDto> products = productService.getProductsByName(name);

        if (products.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("No products found", null));

        return ResponseEntity.ok(new ApiResponse("success", products));
    }

    @GetMapping("/product/by-brand")
    public ResponseEntity<ApiResponse> findProductByBrand(@RequestParam String brand) {
        List<ProductDto> product = productService.getProductsByBrand(brand);

        if (product.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("No products found", null));

        return ResponseEntity.ok(new ApiResponse("success", product));
    }

    @GetMapping("/product/{category}/all/products")
    public ResponseEntity<ApiResponse> findProductByCategory(@PathVariable String category) {
        List<ProductDto> products = productService.getProductsByCategory(category);

        if (products.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("No products found", null));

        return ResponseEntity.ok(new ApiResponse("success", products));
    }
}
