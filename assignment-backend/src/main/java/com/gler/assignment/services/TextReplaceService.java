package com.gler.assignment.services;

import com.gler.assignment.dto.TextReplaceDto;
import com.gler.assignment.entities.TextReplaceEntity;
import com.gler.assignment.repositories.TextReplaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TextReplaceService {

    private final TextReplaceRepository repository;

    public TextReplaceDto processTextReplacement(String text) {
        log.info("Processing text replacement for: {}", text);
        if (text == null)
            throw new IllegalArgumentException("Text cannot be null");
        int length = text.length();
        if (length < 2)
            throw new IllegalArgumentException("Text length must be at least 2 characters");
        if (length == 2)
            return null;
        String replacedText = replaceText(text);
        TextReplaceEntity entity = TextReplaceEntity.builder()
                .originalText(text)
                .replacedText(replacedText)
                .build();
        repository.save(entity);
        log.info("Saved text replacement with id: {}", entity.getId());
        return TextReplaceDto.builder()
                .originalText(text)
                .replacedText(replacedText)
                .build();
    }

    private String replaceText(String text) {
        if (text.length() <= 2)
            return text;
        StringBuilder sb = new StringBuilder(text);
        sb.setCharAt(0, '*');
        sb.setCharAt(text.length() - 1, '$');
        return sb.toString();
    }
}