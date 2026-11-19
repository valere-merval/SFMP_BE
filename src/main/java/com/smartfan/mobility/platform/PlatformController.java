package com.smartfan.mobility.platform;

import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.api.ModuleStatus;
import com.smartfan.mobility.shared.catalogue.FixtureCatalogue;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/platform")
public class PlatformController {

    @GetMapping
    public ApiResponse<ModuleStatus> platform() {
        return ApiResponse.ok(new ModuleStatus(
                "platform",
                "running",
                "Smart Fan Mobility Platform backend is up and ready for module wiring"), "Platform ready");
    }

    @GetMapping("/modules")
    public ApiResponse<List<ModuleStatus>> modules() {
        return ApiResponse.ok(List.of(
                new ModuleStatus("identity", "ready", "User and role entry point"),
                new ModuleStatus("events", "ready", "Event catalogue and schedules"),
                new ModuleStatus("mobility", "ready", "Journey planning and transport options"),
                new ModuleStatus("bookings", "ready", "Reservation workflow"),
                new ModuleStatus("operations", "ready", "Operational dashboard"),
                new ModuleStatus("admin", "ready", "Platform administration"),
                new ModuleStatus("intelligence", "ready", "Version 3 forecasting and heatmaps")), "Module inventory");
    }

    @GetMapping("/roadmap")
    public ApiResponse<List<FixtureCatalogue.RoadmapView>> roadmap() {
        return ApiResponse.ok(FixtureCatalogue.roadmap(), "Delivery roadmap");
    }
}
