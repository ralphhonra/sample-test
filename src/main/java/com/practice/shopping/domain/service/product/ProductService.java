package com.practice.shopping.domain.service.product;

import com.practice.shopping.domain.config.ShopComponent;
import com.practice.shopping.domain.dto.ImageDto;
import com.practice.shopping.domain.dto.ProductDto;
import com.practice.shopping.domain.exceptions.ResourceNotFoundException;
import com.practice.shopping.domain.model.Category;
import com.practice.shopping.domain.model.Image;
import com.practice.shopping.domain.model.Product;
import com.practice.shopping.domain.repository.CategoryRepository;
import com.practice.shopping.domain.repository.ImageRepository;
import com.practice.shopping.domain.repository.ProductRepository;
import com.practice.shopping.domain.request.ConfigProductRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;
    private final ShopComponent shopComponent;

    @Override
    public Product addProduct(ConfigProductRequest request) {
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = Category.builder()
                            .name(request.getCategory().getName())
                            .build();
                    return categoryRepository.save(newCategory);
                });

        return productRepository.save(createProduct(request, category));
    }

    private Product createProduct(ConfigProductRequest request, Category category) {
        return Product.builder()
                .name(request.getName())
                .brand(request.getBrand())
                .price(request.getPrice())
                .inventory(request.getInventory())
                .description(request.getDescription())
                .category(category)
                .build();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(
                        product -> productRepository.deleteById(product.getId()),
                        () -> { throw new ResourceNotFoundException("Product not found!"); }
                );
    }

    @Override
    public Product updateProduct(ConfigProductRequest product, Long id) {
        return productRepository.findById(id)
                .map(existingProduct -> updateExistingProduct(existingProduct, product))
                .map(productRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
    }

    private Product updateExistingProduct(Product existingProduct, ConfigProductRequest product) {
        return existingProduct.updateProduct(
                product.getName(),
                product.getBrand(),
                product.getPrice(),
                product.getInventory(),
                product.getDescription(),
                categoryRepository.findByName(product.getCategory().getName())
        );
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::convertProductToDto)
                .toList();
    }

    @Override
    public List<ProductDto> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category)
                .stream()
                .map(this::convertProductToDto)
                .toList();
    }

    @Override
    public List<ProductDto> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand)
                .stream()
                .map(this::convertProductToDto)
                .toList();
    }

    @Override
    public List<ProductDto> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand)
                .stream()
                .map(this::convertProductToDto)
                .toList();
    }

    @Override
    public List<ProductDto> getProductsByName(String name) {
        return productRepository.findByName(name)
                .stream()
                .map(this::convertProductToDto)
                .toList();
    }

    @Override
    public List<ProductDto> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name)
                .stream()
                .map(this::convertProductToDto)
                .toList();
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

    private ProductDto convertProductToDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);

        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDto> imageDtos = shopComponent.mapList(images, ImageDto.class);

        productDto.setImages(imageDtos);

        return productDto;
    }
}
