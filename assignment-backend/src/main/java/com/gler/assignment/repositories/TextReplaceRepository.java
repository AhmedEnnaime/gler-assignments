package com.gler.assignment.repositories;

import com.gler.assignment.entities.TextReplaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TextReplaceRepository extends JpaRepository<TextReplaceEntity, Long> {
    List<TextReplaceEntity> findByOriginalText(String originalText);
    List<TextReplaceEntity> findAllByOrderByCreatedAtDesc();
}