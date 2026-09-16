package com.skillbridge.controller;

import com.skillbridge.dto.MatchResult;
import com.skillbridge.model.Job;
import com.skillbridge.repository.JobRepository;
import com.skillbridge.service.MatchingService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class MatchingController {

    private final MatchingService matchingService;
    private final JobRepository jobRepository;

    public MatchingController(
            MatchingService matchingService,
            JobRepository jobRepository) {
        this.matchingService = matchingService;
        this.jobRepository = jobRepository;
    }

    @GetMapping("/{jobId}/matches")
    public ResponseEntity<List<MatchResult>> getMatches(
            @PathVariable Long jobId) {

        return jobRepository.findById(jobId)
                .map(job -> ResponseEntity.ok(
                        matchingService.rankCandidates(job)
                ))
                .orElse(ResponseEntity.notFound().build());
    }
}