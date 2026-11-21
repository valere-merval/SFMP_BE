package com.smartfan.mobility.media;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.smartfan.mobility.media.application.MediaStorageService;
import io.minio.MinioClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.mock.web.MockMultipartFile;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class ImageUploadSecurityTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void anonymousUploadsAreAllowedForProfilePhotos() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "profile.jpg",
                "image/jpeg",
                "fake-image-bytes".getBytes());

        mockMvc.perform(multipart("/api/v1/uploads/images").file(file))
                .andExpect(status().isCreated());
    }

    @TestConfiguration
    static class TestUploadConfig {
        @Bean
        @Primary
        MediaStorageService mediaStorageService() {
            return new MediaStorageService(mock(MinioClient.class), "sfmp-media", "http://localhost:9000") {
                @Override
                public UploadedAsset storeImage(org.springframework.web.multipart.MultipartFile file, String folder) {
                    return new UploadedAsset(folder + "/test.jpg", "https://cdn.example.com/" + folder + "/test.jpg");
                }
            };
        }
    }
}
