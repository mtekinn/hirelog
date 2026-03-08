package com.example.hirelog.controller;

import com.example.hirelog.dto.ApplicationResponse;
import com.example.hirelog.dto.SummaryResponse;
import com.example.hirelog.entity.Application;
import com.example.hirelog.service.ApplicationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {
    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    public List<ApplicationResponse> getAllApplications(@RequestParam Long userId) {
        return applicationService.getAllApplications(userId);
    }

    @GetMapping("/summary/{userId}")
    public SummaryResponse getSummary(@PathVariable Long userId) {
        return applicationService.getSummary(userId);
    }

    @PostMapping
    public Application createApplication(@RequestBody Application application) {
        return applicationService.createApplication(application);
    }

    @PatchMapping("/{id}/status")
    public ApplicationResponse updateStatus(@PathVariable Long id,
                                            @RequestBody String status) {
        return applicationService.updateStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public void deleteApplication(@PathVariable Long id) {
        applicationService.deleteApplication(id);
    }
}
