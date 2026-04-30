package com.avenhon.healthmaxxing.controller;

import com.avenhon.healthmaxxing.dto.InsightsResponse;
import com.avenhon.healthmaxxing.security.CustomUserDetails;
import com.avenhon.healthmaxxing.service.InsightsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/insights")
public class InsightsController {
    private final InsightsService insightsService;

    public InsightsController(InsightsService insightsService) {
        this.insightsService = insightsService;
    }

    @GetMapping
    public InsightsResponse getTodayInsights(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return insightsService.prepareTodayInsight(userDetails.getId());
    }
}
