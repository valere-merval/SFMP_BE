package com.smartfan.mobility.media.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    @Bean
    MinioClient minioClient(
            @Value("${s3.endpoint:http://localhost:9000}") String endpoint,
            @Value("${s3.access-key:sfmp}") String accessKey,
            @Value("${s3.secret-key:sfmp12345}") String secretKey) {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }
}
