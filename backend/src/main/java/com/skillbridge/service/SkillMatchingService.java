package com.skillbridge.service;

import com.skillbridge.model.SkillRequirementGroup;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillMatchingService {

    public boolean matchesGroup(
            SkillRequirementGroup group,
            List<String> candidateSkills) {

        if (group == null || candidateSkills == null) {
            return false;
        }

        return group.getSkills().stream()
                .anyMatch(requiredSkill ->
                        candidateSkills.stream()
                                .anyMatch(candidateSkill ->
                                        candidateSkill.equalsIgnoreCase(requiredSkill)
                                )
                );
    }
}