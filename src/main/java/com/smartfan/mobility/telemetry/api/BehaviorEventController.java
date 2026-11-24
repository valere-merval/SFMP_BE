package com.smartfan.mobility.telemetry.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.telemetry.application.BehaviorEventPipelineService;
import com.smartfan.mobility.telemetry.domain.BehaviorEventEntity;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(BehaviorEventController.class);

    private final BehaviorEventPipelineService pipelineService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public BehaviorEventController(BehaviorEventPipelineService pipelineService) {
        this.pipelineService = pipelineService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponse<BehaviorEventRequest> create(@RequestBody BehaviorEventRequest request) {
        pipelineService.record(
                safe(request.userId()),
                safe(request.eventType()),
                safe(request.screen()),
                request.payload() == null ? Map.of() : request.payload(),
                "telemetry_api");
        log.info("Telemetry event recorded: userId={} eventType={} screen={}", safe(request.userId()), safe(request.eventType()), safe(request.screen()));
        return ApiResponse.ok(request, "Behavior event recorded");
    }

    @GetMapping
    public ApiResponse<List<BehaviorEventEntity>> list() {
        log.info("Telemetry events requested");
        return ApiResponse.ok(pipelineService.list(), "Behavior events ready");
    }

    private String safe(String value) {
        return value == null || value.isBlank() ? "unknown" : value;
    }
}
