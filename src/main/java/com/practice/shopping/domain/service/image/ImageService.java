package com.practice.shopping.domain.service.image;

import com.practice.shopping.domain.dto.ImageDto;
import com.practice.shopping.domain.exceptions.ResourceNotFoundException;
import com.practice.shopping.domain.model.Image;
import com.practice.shopping.domain.model.Product;
import com.practice.shopping.domain.repository.ImageRepository;
import com.practice.shopping.domain.service.product.ProductService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ImageService implements IImageService {
    private final ImageRepository imageRepository;
    private final ProductService productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No image found with id: " + id));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id)
                .ifPresentOrElse(
                        imageRepository::delete,
                        () -> { throw new ResourceNotFoundException("No image found with id: " + id); }
                );
    }

    @Override
    public List<ImageDto> saveImages(Long productId, List<MultipartFile> files) {
        Product product = productService.getProductById(productId);
        ArrayList<ImageDto> imagesDto = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                Image imageConfig = Image.builder()
                        .fileName(file.getOriginalFilename())
                        .fileType(file.getContentType())
                        .image(new SerialBlob(file.getBytes()))
                        .product(product)
                        .build();

                Image savedImage = imageRepository.save(imageConfig);
                String buildDownloadUrl = "/api/v1/images/image/download/";
                savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());

                Image finalFormattedImage = imageRepository.save(savedImage);

                imagesDto.add(
                        ImageDto.builder()
                                .id(finalFormattedImage.getId())
                                .fileName(finalFormattedImage.getFileName())
                                .downloadUrl(finalFormattedImage.getDownloadUrl())
                                .build()
                );
            } catch (IOException | SQLException err) {
                throw new RuntimeException(err.getMessage());
            }
        }

        return imagesDto;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        imageRepository.findById(imageId).ifPresentOrElse(
                image -> {
                    try {
                        image.setImage(new SerialBlob(file.getBytes()));
                        image.setFileName(file.getOriginalFilename());
                        image.setFileType(file.getContentType());
                        imageRepository.save(image);
                    } catch (IOException | SQLException e) {
                        throw new RuntimeException(e);
                    }
                },
                () -> { throw new ResourceNotFoundException("No image found with id: " + imageId); }
        );
    }
}
