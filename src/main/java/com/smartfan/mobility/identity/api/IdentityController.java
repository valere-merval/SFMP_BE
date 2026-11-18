package com.smartfan.mobility.identity.api;

import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.demo.DemoCatalogue;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/identity")
public class IdentityController {

    @GetMapping("/users")
    public ApiResponse<List<DemoCatalogue.UserView>> users() {
        return ApiResponse.ok(DemoCatalogue.users(), "Identity users loaded");
    }
}
