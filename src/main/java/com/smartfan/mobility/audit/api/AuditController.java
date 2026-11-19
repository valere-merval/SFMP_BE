package com.smartfan.mobility.audit.api;

import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.catalogue.FixtureCatalogue;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/audit")
public class AuditController {

    @GetMapping("/entries")
    public ApiResponse<List<FixtureCatalogue.AuditView>> entries() {
        return ApiResponse.ok(FixtureCatalogue.auditEntries(), "Audit trail ready");
    }
}
