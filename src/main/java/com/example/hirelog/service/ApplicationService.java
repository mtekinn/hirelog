package com.example.hirelog.service;

import com.example.hirelog.dto.ApplicationResponse;
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

    public List<ApplicationResponse> getAllApplications() {
        return applicationRepository.findAll()
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

    public void deleteApplication(Long id) {
        applicationRepository.deleteById(id);
    }
}
