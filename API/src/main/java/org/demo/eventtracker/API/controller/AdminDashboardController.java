package org.demo.eventtracker.API.controller;

import lombok.RequiredArgsConstructor;
import org.demo.eventtracker.API.dto.DashboardResponse;
import org.demo.eventtracker.API.service.AdminDashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminDashboardController {

    private final AdminDashboardService adminDashboardService;

    @GetMapping("/admin/dashboard")
    public DashboardResponse getDashboardSummary() {
        return adminDashboardService.getDashboardSummary();
    }
}