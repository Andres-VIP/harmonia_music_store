package com.harmonia.store.repository;

import com.harmonia.store.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    List<Review> findByInstrumentId(Long instrumentId);
    
    List<Review> findByRating(Integer rating);
    
    List<Review> findByRatingGreaterThanEqual(Integer minRating);
    
    List<Review> findByAuthorNameContainingIgnoreCase(String authorName);
} 