package com.smartfan.mobility.telemetry.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartfan.mobility.telemetry.application.BehaviorEventPipelineService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@ConditionalOnProperty(name = "app.telemetry.enabled", havingValue = "true")
public class RequestAuditFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(RequestAuditFilter.class);

    private final BehaviorEventPipelineService pipelineService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RequestAuditFilter(BehaviorEventPipelineService pipelineService) {
        this.pipelineService = pipelineService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path != null && (path.startsWith("/actuator") || path.startsWith("/swagger") || path.contains("/v3/api-docs"));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Instant startedAt = Instant.now();
        Exception failure = null;
        try {
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            failure = ex;
            throw ex;
        } finally {
            persistAudit(request, response, startedAt, failure);
        }
    }

    private void persistAudit(HttpServletRequest request, HttpServletResponse response, Instant startedAt, Exception failure) {
        try {
            String userId = request.getRemoteUser();
            if (userId == null && request.getUserPrincipal() != null) {
                userId = request.getUserPrincipal().getName();
            }
            if (userId == null) {
                userId = "anonymous";
            }

            Map<String, Object> payload = new LinkedHashMap<>();
            payload.put("timestamp", Instant.now().toString());
            payload.put("method", request.getMethod());
            payload.put("path", request.getRequestURI());
            payload.put("query", request.getQueryString());
            payload.put("status", response.getStatus());
            payload.put("durationMs", Instant.now().toEpochMilli() - startedAt.toEpochMilli());
            payload.put("contentType", request.getContentType());
            payload.put("remoteAddr", request.getRemoteAddr());
            payload.put("userAgent", request.getHeader("User-Agent"));
            if (failure != null) {
                payload.put("error", failure.getClass().getSimpleName());
                payload.put("errorMessage", failure.getMessage());
            }

            pipelineService.record(
                    userId,
                    "http_request",
                    request.getMethod() + " " + request.getRequestURI(),
                    payload,
                    "http_audit");

            log.info("AUDIT {} {} status={} user={} durationMs={}", request.getMethod(), request.getRequestURI(), response.getStatus(), userId, payload.get("durationMs"));
        } catch (Exception e) {
            try {
                log.warn("Failed to persist audit event for {} {}", request.getMethod(), request.getRequestURI(), e);
            } catch (Exception ignored) {
                // no-op
            }
        }
    }
}
