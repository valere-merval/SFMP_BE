package com.smartfan.mobility.notifications.api;

import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.demo.DemoCatalogue;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    @GetMapping
    public ApiResponse<List<DemoCatalogue.NotificationView>> list() {
        return ApiResponse.ok(DemoCatalogue.notifications(), "Notifications ready");
    }

    @GetMapping("/contextual")
    public ApiResponse<List<DemoCatalogue.ContextualMessageView>> contextual() {
        return ApiResponse.ok(DemoCatalogue.contextualMessages(), "Contextual messaging ready");
    }
}
