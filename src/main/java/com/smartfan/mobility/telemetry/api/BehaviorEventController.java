package com.smartfan.mobility.telemetry.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.telemetry.domain.BehaviorEventEntity;
import com.smartfan.mobility.telemetry.domain.BehaviorEventRepository;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/telemetry/events")
public class BehaviorEventController {

    private final BehaviorEventRepository repository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public BehaviorEventController(BehaviorEventRepository repository) {
        this.repository = repository;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponse<BehaviorEventRequest> create(@RequestBody BehaviorEventRequest request) {
        String payloadJson = toJson(request.payload());
        repository.save(new BehaviorEventEntity(
                UUID.randomUUID().toString(),
                safe(request.userId()),
                safe(request.eventType()),
                safe(request.screen()),
                payloadJson));
        return ApiResponse.ok(request, "Behavior event recorded");
    }

    @GetMapping
    public ApiResponse<List<BehaviorEventEntity>> list() {
        return ApiResponse.ok(repository.findAllByOrderByCreatedAtDesc(), "Behavior events ready");
    }

    private String toJson(Object value) {
        try {
            return objectMapper.writeValueAsString(value == null ? Map.of() : value);
        } catch (JsonProcessingException ex) {
            return "{}";
        }
    }

    private String safe(String value) {
        return value == null || value.isBlank() ? "unknown" : value;
    }
}
