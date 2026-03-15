package com.example.hirelog.service;

import com.example.hirelog.dto.ApplicationResponse;
import com.example.hirelog.dto.SummaryResponse;
import com.example.hirelog.entity.Application;
import com.example.hirelog.repository.ApplicationRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public ApplicationService(ApplicationRepository applicationRepository,
                              RedisTemplate<String, Object> redisTemplate) {
        this.applicationRepository = applicationRepository;
        this.redisTemplate = redisTemplate;
    }

    private String cacheKey(Long userId) {
        return "applications:user:" + userId;
    }

    public List<ApplicationResponse> getAllApplications(Long userId) {
        String key = cacheKey(userId);

        List<ApplicationResponse> cached = (List<ApplicationResponse>) redisTemplate.opsForValue().get(key);
        if (cached != null) {
            System.out.println(">>> Redis'ten geldi");
            return cached;
        }

        System.out.println(">>> DB'den geldi");
        List<ApplicationResponse> result = applicationRepository.findByUserId(userId)
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

        redisTemplate.opsForValue().set(key, result, 10, TimeUnit.MINUTES);
        return result;
    }

    public Application createApplication(Application application) {
        Application saved = applicationRepository.save(application);
        redisTemplate.delete(cacheKey(application.getUser().getId()));
        return saved;
    }

    public ApplicationResponse updateStatus(Long id, String status) {
        Application current = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Başvuru bulunamadı"));
        current.setStatus(status);
        applicationRepository.save(current);
        redisTemplate.delete(cacheKey(current.getUser().getId()));

        return new ApplicationResponse(id,
                current.getJobName(),
                status,
                current.getCompanyName(),
                current.getCompanyWeb(),
                current.getAppliedDate(),
                current.getNotes(),
                current.isDidReply(),
                current.getUser().getId());
    }

    public void deleteApplication(Long id) {
        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Başvuru bulunamadı"));
        redisTemplate.delete(cacheKey(app.getUser().getId()));
        applicationRepository.deleteById(id);
    }

    public SummaryResponse getSummary(Long userId) {
        List<Application> applications = applicationRepository.findByUserId(userId);
        long total = applications.size();
        long appliedCount = applications.stream().filter(a -> a.getStatus().equals("APPLIED")).count();
        long interviewCount = applications.stream().filter(a -> a.getStatus().equals("INTERVIEW")).count();
        long offerCount = applications.stream().filter(a -> a.getStatus().equals("OFFER")).count();
        long rejectedCount = applications.stream().filter(a -> a.getStatus().equals("REJECTED")).count();
        long repliedCount = applications.stream().filter(a -> a.isDidReply()).count();
        double replyRate = total > 0 ? (double) repliedCount / total * 100 : 0;
        return new SummaryResponse(total, appliedCount, interviewCount, offerCount, rejectedCount, replyRate);
    }
}