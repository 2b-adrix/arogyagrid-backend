package com.arogyagrid.repository;

import com.arogyagrid.entity.RedistributionRecommendation;
import com.arogyagrid.enums.RecommendationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RedistributionRepository 
        extends JpaRepository<RedistributionRecommendation, String> {

    List<RedistributionRecommendation> findByStatusOrderByPriorityScoreDesc(
            RecommendationStatus status);

    List<RedistributionRecommendation> findAllByOrderByPriorityScoreDesc();

    long countByStatus(RecommendationStatus status);
}