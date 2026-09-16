package com.skillbridge.dto;
import java.util.List;

public class MatchResult {

    private Long candidateId;
    private String candidateName;
    private double matchScore;
    private double titleScore;
    private double skillScore;
    private double experienceScore;
    private List<String> matchedSkillGroups;
    private List<String> missingSkillGroups;

    public MatchResult(
            Long candidateId,
            String candidateName,
            double matchScore,
            double titleScore,
            double skillScore,
            double experienceScore,
            List<String> matchedSkillGroups,
            List<String> missingSkillGroups) {

        this.candidateId = candidateId;
        this.candidateName = candidateName;
        this.matchScore = matchScore;
        this.titleScore = titleScore;
        this.skillScore = skillScore;
        this.experienceScore = experienceScore;
        this.matchedSkillGroups = matchedSkillGroups;
        this.missingSkillGroups = missingSkillGroups;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public double getMatchScore() {
        return matchScore;
    }

    public double getTitleScore() {
        return titleScore;
    }

    public double getSkillScore() {
        return skillScore;
    }

    public double getExperienceScore() {
        return experienceScore;
    }

    public List<String> getMatchedSkillGroups() {
        return matchedSkillGroups;
    }

    public List<String> getMissingSkillGroups() {
        return missingSkillGroups;
    }
}