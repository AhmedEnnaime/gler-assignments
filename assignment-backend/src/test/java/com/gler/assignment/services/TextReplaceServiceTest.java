package com.gler.assignment.services;

import com.gler.assignment.dto.TextReplaceDto;
import com.gler.assignment.entities.TextReplaceEntity;
import com.gler.assignment.repositories.TextReplaceRepository;
import org.junit.jupiter.api.DisplayName;
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
    void testReplaceText_Elephant() {
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
    void testReplaceText_Home() {
        String input = "home";
        when(repository.save(any(TextReplaceEntity.class))).thenReturn(new TextReplaceEntity());
        TextReplaceDto result = service.processTextReplacement(input);
        assertNotNull(result);
        assertEquals("home", result.getOriginalText());
        assertEquals("*om$", result.getReplacedText());
    }

    @Test
    void testReplaceText_MixedCharacters() {
        String input = "abc#20xyz";
        when(repository.save(any(TextReplaceEntity.class))).thenReturn(new TextReplaceEntity());
        TextReplaceDto result = service.processTextReplacement(input);
        assertNotNull(result);
        assertEquals("abc#20xyz", result.getOriginalText());
        assertEquals("*bc#20xy$", result.getReplacedText());
    }

    @Test
    void testReplaceText_ThreeCharacters() {
        String input = "abc";
        when(repository.save(any(TextReplaceEntity.class))).thenReturn(new TextReplaceEntity());
        TextReplaceDto result = service.processTextReplacement(input);
        assertNotNull(result);
        assertEquals("abc", result.getOriginalText());
        assertEquals("*b$", result.getReplacedText());
    }

    @Test
    void testReplaceText_LongString() {
        String input = "TetingCodeAssignmentProject";
        when(repository.save(any(TextReplaceEntity.class))).thenReturn(new TextReplaceEntity());
        TextReplaceDto result = service.processTextReplacement(input);
        assertNotNull(result);
        assertEquals("TetingCodeAssignmentProject", result.getOriginalText());
        assertEquals("*etingCodeAssignmentProjec$", result.getReplacedText());
    }

    @Test
    void testReplaceText_OnlyNumbers() {
        String input = "12345";
        when(repository.save(any(TextReplaceEntity.class))).thenReturn(new TextReplaceEntity());
        TextReplaceDto result = service.processTextReplacement(input);
        assertNotNull(result);
        assertEquals("12345", result.getOriginalText());
        assertEquals("*234$", result.getReplacedText());
    }

    @Test
    void testReplaceText_SpecialCharacters() {
        String input = "@#$%^&";
        when(repository.save(any(TextReplaceEntity.class))).thenReturn(new TextReplaceEntity());
        TextReplaceDto result = service.processTextReplacement(input);
        assertNotNull(result);
        assertEquals("@#$%^&", result.getOriginalText());
        assertEquals("*#$%^$", result.getReplacedText());
    }

    @Test
    void testReplaceText_TwoCharacters() {
        String input = "ab";
        TextReplaceDto result = service.processTextReplacement(input);
        assertNull(result);
        verify(repository, never()).save(any(TextReplaceEntity.class));
    }

    @Test
    void testReplaceText_EmptyString() {
        String input = "";
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.processTextReplacement(input)
        );
        assertEquals("Text length must be at least 2 characters", exception.getMessage());
        verify(repository, never()).save(any(TextReplaceEntity.class));
    }

    @Test
    void testReplaceText_OneCharacter() {
        String input = "a";
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.processTextReplacement(input)
        );
        assertEquals("Text length must be at least 2 characters", exception.getMessage());
        verify(repository, never()).save(any(TextReplaceEntity.class));
    }

    @Test
    void testReplaceText_NullInput() {
        String input = null;
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.processTextReplacement(input)
        );
        assertEquals("Text cannot be null", exception.getMessage());
        verify(repository, never()).save(any(TextReplaceEntity.class));
    }

}