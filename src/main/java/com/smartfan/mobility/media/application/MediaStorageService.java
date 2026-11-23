package com.smartfan.mobility.media.application;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MediaStorageService {

    private final MinioClient minioClient;
    private final String bucketName;
    private final String publicBaseUrl;

    public MediaStorageService(
            MinioClient minioClient,
            @Value("${s3.bucket:sfmp-media}") String bucketName,
            @Value("${s3.public-base-url:${s3.endpoint:http://localhost:9000}}") String publicBaseUrl) {
        this.minioClient = minioClient;
        this.bucketName = bucketName;
        this.publicBaseUrl = publicBaseUrl.endsWith("/") ? publicBaseUrl.substring(0, publicBaseUrl.length() - 1) : publicBaseUrl;
    }

    public UploadedAsset storeImage(MultipartFile file, String folder) {
        try {
            ensureBucket();
            String extension = extensionOf(file.getOriginalFilename(), file.getContentType());
            String objectKey = folder + "/" + UUID.randomUUID() + extension;
            try (InputStream inputStream = file.getInputStream()) {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(bucketName)
                                .object(objectKey)
                                .stream(inputStream, file.getSize(), 5 * 1024 * 1024)
                                .contentType(file.getContentType() == null ? "application/octet-stream" : file.getContentType())
                                .build());
            }
            String url = publicBaseUrl + "/" + bucketName + "/" + objectKey;
            return new UploadedAsset(objectKey, url);
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to upload image", exception);
        }
    }

    public UploadedAsset storeImage(byte[] bytes, String fileName, String contentType, String folder) {
        try {
            ensureBucket();
            String extension = extensionOf(fileName, contentType);
            String objectKey = folder + "/" + UUID.randomUUID() + extension;
            try (InputStream inputStream = new ByteArrayInputStream(bytes)) {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(bucketName)
                                .object(objectKey)
                                .stream(inputStream, bytes.length, 5 * 1024 * 1024)
                                .contentType(contentType == null || contentType.isBlank() ? "application/octet-stream" : contentType)
                                .build());
            }
            String url = publicBaseUrl + "/" + bucketName + "/" + objectKey;
            return new UploadedAsset(objectKey, url);
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to upload image", exception);
        }
    }

    private void ensureBucket() throws Exception {
        boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!exists) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

    private String extensionOf(String originalFilename, String contentType) {
        String suffix = "";
        if (originalFilename != null && !originalFilename.isBlank()) {
            String candidate = Paths.get(originalFilename).getFileName().toString();
            int index = candidate.lastIndexOf('.');
            if (index >= 0 && index < candidate.length() - 1) {
                suffix = candidate.substring(index).toLowerCase(Locale.ROOT);
            }
        }
        if (suffix.isBlank()) {
            suffix = switch (contentType == null ? "" : contentType.toLowerCase(Locale.ROOT)) {
                case "image/jpeg" -> ".jpg";
                case "image/png" -> ".png";
                case "image/webp" -> ".webp";
                case "image/gif" -> ".gif";
                default -> ".bin";
            };
        }
        return suffix;
    }

    public record UploadedAsset(String key, String url) {
    }
}
