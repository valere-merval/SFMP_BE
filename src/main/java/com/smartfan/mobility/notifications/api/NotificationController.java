package com.smartfan.mobility.notifications.api;

import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.catalogue.FixtureCatalogue;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    @GetMapping
    public ApiResponse<List<FixtureCatalogue.NotificationView>> list() {
        return ApiResponse.ok(FixtureCatalogue.notifications(), "Notifications ready");
    }

    @GetMapping("/contextual")
    public ApiResponse<List<FixtureCatalogue.ContextualMessageView>> contextual() {
        return ApiResponse.ok(FixtureCatalogue.contextualMessages(), "Contextual messaging ready");
    }
}
