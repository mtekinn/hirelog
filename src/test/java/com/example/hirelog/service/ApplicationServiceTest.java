package com.example.hirelog.service;

import com.example.hirelog.dto.ApplicationResponse;
import com.example.hirelog.entity.Application;
import com.example.hirelog.entity.User;
import com.example.hirelog.repository.ApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceTest {

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @InjectMocks
    private ApplicationService applicationService;

    @BeforeEach
    void setUp() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void getAllApplications_whenCacheEmpty_shouldFetchFromDB() {
        // Arrange
        Long userId = 1L;
        when(valueOperations.get("applications:user:1")).thenReturn(null);

        User user = new User();
        user.setId(1L);

        Application app = new Application();
        app.setId(1L);
        app.setJobName("Backend Developer");
        app.setStatus("APPLIED");
        app.setCompanyName("Test Co");
        app.setCompanyWeb("test.com");
        app.setAppliedDate(LocalDate.now());
        app.setNotes("Test");
        app.setDidReply(false);
        app.setUser(user);

        when(applicationRepository.findByUserId(userId)).thenReturn(List.of(app));

        // Act
        List<ApplicationResponse> result = applicationService.getAllApplications(userId);

        // Assert
        assertEquals(1, result.size());
        assertEquals("Backend Developer", result.get(0).getJobName());
        verify(applicationRepository, times(1)).findByUserId(userId);
    }

    @Test
    void getAllApplications_whenCacheHit_shouldNotFetchFromDB() {
        // Arrange
        Long userId = 1L;
        List<ApplicationResponse> cached = List.of(
                new ApplicationResponse(1L, "Cached Job", "APPLIED", "Co", "co.com",
                        LocalDate.now(), "note", false, 1L)
        );
        when(valueOperations.get("applications:user:1")).thenReturn(cached);

        // Act
        List<ApplicationResponse> result = applicationService.getAllApplications(userId);

        // Assert
        assertEquals("Cached Job", result.get(0).getJobName());
        verify(applicationRepository, never()).findByUserId(userId);
    }
}