package com.skillbridge.service;

import com.skillbridge.model.Candidate;
import com.skillbridge.model.Job;
import com.skillbridge.model.SkillRequirementGroup;
import org.springframework.stereotype.Service;
import com.skillbridge.dto.MatchResult;
import com.skillbridge.repository.CandidateRepository;
import java.util.ArrayList;

import java.util.Comparator;
import java.util.List;

@Service
public class MatchingService {

    private final TitleRelationService titleRelationService;
    private final SkillMatchingService skillMatchingService;
    private final CandidateRepository candidateRepository;

    public MatchingService(
            TitleRelationService titleRelationService,
            SkillMatchingService skillMatchingService,
            CandidateRepository candidateRepository) {

        this.titleRelationService = titleRelationService;
        this.skillMatchingService = skillMatchingService;
        this.candidateRepository = candidateRepository;
    }

    public double calculateMatchScore(Candidate candidate, Job job) {

        double titleScore = titleRelationService.getSimilarityScore(
                job.getTitle(),
                candidate.getCurrentTitle()
        );

        double skillScore = calculateSkillScore(candidate, job);

        double experienceScore =
                candidate.getYearsExperience() >= job.getMinimumExperience()
                        ? 1.0
                        : 0.0;

        // Weighted final score
        double finalScore =
                (titleScore * 0.30)
                + (skillScore * 0.50)
                + (experienceScore * 0.20);

        return finalScore * 100;
    }

    public  double calculateSkillScore(Candidate candidate, Job job) {

        if (job.getSkillRequirementGroups().isEmpty()) {
            return 1.0;
        }

        long matchedGroups = job.getSkillRequirementGroups()
                .stream()
                .filter(group ->
                        skillMatchingService.matchesGroup(
                                group,
                                candidate.getSkills()
                        )
                )
                .count();

        return (double) matchedGroups
                / job.getSkillRequirementGroups().size();
    }

    public List<MatchResult> rankCandidates(Job job) {

        return candidateRepository.findAll()
                .stream()
                .map(candidate -> {

                    double titleScore =
                            titleRelationService.getSimilarityScore(
                                    job.getTitle(),
                                    candidate.getCurrentTitle()
                            );

                    double skillScore =
                            calculateSkillScore(candidate, job);

                    double experienceScore = calculateExperienceScore(candidate, job);

                    List<String> matchedSkillGroups = new ArrayList<>();
                    List<String> missingSkillGroups = new ArrayList<>();

                    for (SkillRequirementGroup group :
                            job.getSkillRequirementGroups()) {

                        boolean matched =
                                skillMatchingService.matchesGroup(
                                        group,
                                        candidate.getSkills()
                                );

                        if (matched) {
                            matchedSkillGroups.add(group.getName());
                        } else {
                            missingSkillGroups.add(group.getName());
                        }
                    }

                    double matchScore =
                            (titleScore * 0.30
                            + skillScore * 0.50
                            + experienceScore * 0.20)
                            * 100;

                    return new MatchResult(
                            candidate.getId(),
                            candidate.getName(),
                            matchScore,
                            titleScore * 100,
                            skillScore * 100,
                            experienceScore * 100,
                            matchedSkillGroups,
                            missingSkillGroups
                    );
                })
                .sorted(
                        Comparator.comparingDouble(
                                MatchResult::getMatchScore
                        ).reversed()
                )
                .toList();
    }


    private double calculateExperienceScore(Candidate candidate, Job job) {

        if (job.getMinimumExperience() == null) {
            return 1.0;
        }

        if (candidate.getYearsExperience() == null) {
            return 0.0;
        }

        return candidate.getYearsExperience() >= job.getMinimumExperience()
                ? 1.0
                : 0.0;
    }
}