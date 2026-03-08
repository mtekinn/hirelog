package com.example.hirelog.service;

import com.example.hirelog.dto.ApplicationResponse;
import com.example.hirelog.dto.SummaryResponse;
import com.example.hirelog.entity.Application;
import com.example.hirelog.repository.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public List<ApplicationResponse> getAllApplications(Long userId) {
        return applicationRepository.findByUserId(userId)
                .stream()
                .map(app -> new ApplicationResponse(
                        app.getId(),
                        app.getJobName(),
                        app.getStatus(),
                        app.getCompanyName(),
                        app.getCompanyWeb(),
                        app.getAppliedDate(),
                        app.getNotes(),
                        app.isDidReply(),
                        app.getUser().getId()
                ))
                .collect(Collectors.toList());
    }

    public Application createApplication(Application application) {
        return applicationRepository.save(application);
    }

    public ApplicationResponse updateStatus(Long id, String status) {
        Application current = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Başvuru bulunamadı"));
        current.setStatus(status);
        Application saved = applicationRepository.save(current);

        ApplicationResponse ar = new ApplicationResponse(id,
                current.getJobName(),
                status,
                current.getCompanyName(),
                current.getCompanyWeb(),
                current.getAppliedDate(),
                current.getNotes(),
                current.isDidReply(),
                current.getUser().getId());
        return ar;
    }

    public void deleteApplication(Long id) {
        applicationRepository.deleteById(id);
    }

    public SummaryResponse getSummary(Long userId) {
        List<Application> applications = applicationRepository.findByUserId(userId);
        long total = applications.size();

        long appliedCount = applications.stream()
                .filter(app -> app.getStatus().equals("APPLIED"))
                .count();

        long interviewCount = applications.stream()
                .filter(app -> app.getStatus().equals("INTERVIEW"))
                .count();

        long offerCount = applications.stream()
                .filter(app -> app.getStatus().equals("OFFER"))
                .count();

        long rejectedCount = applications.stream()
                .filter(app -> app.getStatus().equals("REJECTED"))
                .count();

        long repliedCount = applications.stream()
                .filter(app -> app.isDidReply() == true)
                .count();

        double replyRate = (double) repliedCount / total * 100;
        SummaryResponse sr = new SummaryResponse(total, appliedCount, interviewCount, offerCount, rejectedCount, replyRate);
        return sr;
    }

}
