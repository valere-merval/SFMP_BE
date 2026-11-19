package com.smartfan.mobility.media.api;

import com.smartfan.mobility.media.application.MediaStorageService;
import com.smartfan.mobility.shared.api.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/uploads")
public class ImageUploadController {

    private final MediaStorageService mediaStorageService;

    public ImageUploadController(MediaStorageService mediaStorageService) {
        this.mediaStorageService = mediaStorageService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<UploadResponse> uploadImage(@RequestPart("file") MultipartFile file) {
        MediaStorageService.UploadedAsset uploadedAsset = mediaStorageService.storeImage(file, "images");
        return ApiResponse.ok(new UploadResponse(uploadedAsset.key(), uploadedAsset.url()), "Image uploaded");
    }

    public record UploadResponse(String key, String url) {
    }
}
