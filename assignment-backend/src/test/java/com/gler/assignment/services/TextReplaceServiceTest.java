package com.gler.assignment.services;

import com.gler.assignment.dto.TextReplaceDto;
import com.gler.assignment.entities.TextReplaceEntity;
import com.gler.assignment.repositories.TextReplaceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TextReplaceServiceTest {

    @Mock
    private TextReplaceRepository repository;

    @InjectMocks
    private TextReplaceService service;

    @Test
    void givenValidWordWhenProcessTextReplacementThenReturnReplacedDto() {
        String input = "elephant";
        TextReplaceEntity savedEntity = TextReplaceEntity.builder()
                .id(1L)
                .originalText(input)
                .replacedText("*lephan$")
                .build();
        when(repository.save(any(TextReplaceEntity.class))).thenReturn(savedEntity);
        TextReplaceDto result = service.processTextReplacement(input);
        assertNotNull(result);
        assertEquals("elephant", result.getOriginalText());
        assertEquals("*lephan$", result.getReplacedText());
        verify(repository, times(1)).save(any(TextReplaceEntity.class));
    }

    @Test
    void givenShortTextOfTwoCharactersWhenProcessTextReplacementThenReturnNullAndNotSave() {
        String input = "ab";
        TextReplaceDto result = service.processTextReplacement(input);
        assertNull(result);
        verify(repository, never()).save(any(TextReplaceEntity.class));
    }

    @Test
    void givenThreeCharacterTextWhenProcessTextReplacementThenReplaceFirstAndLastCharacter() {
        String input = "abc";
        when(repository.save(any(TextReplaceEntity.class))).thenReturn(new TextReplaceEntity());
        TextReplaceDto result = service.processTextReplacement(input);
        assertNotNull(result);
        assertEquals("abc", result.getOriginalText());
        assertEquals("*b$", result.getReplacedText());
    }

    @Test
    void givenTextWithSpecialCharactersWhenProcessTextReplacementThenReplaceFirstAndLastCharacter() {
        String input = "@#$%^&";
        when(repository.save(any(TextReplaceEntity.class))).thenReturn(new TextReplaceEntity());
        TextReplaceDto result = service.processTextReplacement(input);
        assertNotNull(result);
        assertEquals("@#$%^&", result.getOriginalText());
        assertEquals("*#$%^$", result.getReplacedText());
    }

    @Test
    void givenOnlyNumbersWhenProcessTextReplacementThenReplaceFirstAndLastCharacter() {
        String input = "12345";
        when(repository.save(any(TextReplaceEntity.class))).thenReturn(new TextReplaceEntity());
        TextReplaceDto result = service.processTextReplacement(input);
        assertNotNull(result);
        assertEquals("12345", result.getOriginalText());
        assertEquals("*234$", result.getReplacedText());
    }

    @Test
    void givenEmptyStringWhenProcessTextReplacementThenThrowIllegalArgumentException() {
        String input = "";
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.processTextReplacement(input)
        );
        assertEquals("Text length must be at least 2 characters", exception.getMessage());
        verify(repository, never()).save(any(TextReplaceEntity.class));
    }

    @Test
    void givenSingleCharacterWhenProcessTextReplacementThenThrowIllegalArgumentException() {
        String input = "a";
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.processTextReplacement(input)
        );
        assertEquals("Text length must be at least 2 characters", exception.getMessage());
        verify(repository, never()).save(any(TextReplaceEntity.class));
    }

    @Test
    void givenNullInputWhenProcessTextReplacementThenThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.processTextReplacement(null)
        );
        assertEquals("Text cannot be null", exception.getMessage());
        verify(repository, never()).save(any(TextReplaceEntity.class));
    }
}
