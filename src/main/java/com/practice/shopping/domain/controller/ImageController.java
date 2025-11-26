package com.practice.shopping.domain.controller;

import com.practice.shopping.domain.dto.ImageDto;
import com.practice.shopping.domain.model.Image;
import com.practice.shopping.domain.response.ApiResponse;
import com.practice.shopping.domain.service.image.IImageService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("${api.prefix}/images")
public class ImageController {
    private final IImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImages(
            @RequestParam List<MultipartFile> files,
            @RequestParam Long productId
    ) {
        List<ImageDto> imageDto = imageService.saveImages(productId, files);

        return ResponseEntity.ok(new ApiResponse("Upload success", imageDto));
    }

    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<?> downloadImage(@PathVariable Long imageId) throws SQLException {
        Image image = imageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage()
                .length()));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                .body(resource);
    }

    @PutMapping("/image/{imageId}/update")
    public ResponseEntity<ApiResponse> updateImage(
            @PathVariable Long imageId,
            @RequestBody MultipartFile file
    ) {
        Image image = imageService.getImageById(imageId);
        if (image != null) {
            imageService.updateImage(file, imageId);

            return ResponseEntity.ok(new ApiResponse("Image updated successfully!", null));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("Something went wrong, please try again later", null));
    }

    @DeleteMapping("/image/{imageId}/delete")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId) {
        Image image = imageService.getImageById(imageId);
        if (image != null) {
            imageService.deleteImageById(imageId);

            return ResponseEntity.ok(new ApiResponse("Image deleted successfully!", null));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("Something went wrong, please try again later", null));
    }
}
