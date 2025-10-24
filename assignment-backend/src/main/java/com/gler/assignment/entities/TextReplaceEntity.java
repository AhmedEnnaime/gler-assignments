package com.gler.assignment.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "text_replacements")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TextReplaceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String originalText;

    @Column(nullable = false, length = 1000)
    private String replacedText;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}