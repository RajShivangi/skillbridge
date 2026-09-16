package com.skillbridge.repository;

import com.skillbridge.model.TitleRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TitleRelationRepository
        extends JpaRepository<TitleRelation, Long> {

    Optional<TitleRelation> findByTitleIgnoreCaseAndRelatedTitleIgnoreCase(
            String title,
            String relatedTitle
    );
}