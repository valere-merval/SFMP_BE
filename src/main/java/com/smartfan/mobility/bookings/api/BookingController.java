package com.smartfan.mobility.bookings.api;

import com.smartfan.mobility.shared.api.ApiResponse;
import com.smartfan.mobility.shared.catalogue.FixtureCatalogue;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    @GetMapping
    public ApiResponse<List<FixtureCatalogue.BookingView>> list() {
        return ApiResponse.ok(FixtureCatalogue.bookings(), "Bookings ready");
    }
}
