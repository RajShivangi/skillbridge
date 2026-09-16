package com.skillbridge.service;

import com.skillbridge.model.TitleRelation;
import com.skillbridge.repository.TitleRelationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TitleRelationService {

    private final TitleRelationRepository titleRelationRepository;

    public TitleRelationService(TitleRelationRepository titleRelationRepository) {
        this.titleRelationRepository = titleRelationRepository;
    }

    public double getSimilarityScore(String jobTitle, String candidateTitle) {

        // Exact title match
        if (jobTitle.equalsIgnoreCase(candidateTitle)) {
            return 1.0;
        }

        // Check stored related titles
        Optional<TitleRelation> relation =
                titleRelationRepository
                        .findByTitleIgnoreCaseAndRelatedTitleIgnoreCase(
                                jobTitle,
                                candidateTitle
                        );

        return relation
                .map(TitleRelation::getSimilarityScore)
                .orElse(0.0);
    }
}