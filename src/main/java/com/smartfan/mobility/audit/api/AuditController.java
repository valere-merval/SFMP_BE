package com.smartfan.mobility.audit.api;

import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.demo.DemoCatalogue;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/audit")
public class AuditController {

    @GetMapping("/entries")
    public ApiResponse<List<DemoCatalogue.AuditView>> entries() {
        return ApiResponse.ok(DemoCatalogue.auditEntries(), "Audit trail ready");
    }
}
