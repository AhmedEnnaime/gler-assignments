package com.gler.assignment.controllers;

import com.gler.assignment.dto.TextReplaceDto;
import com.gler.assignment.services.TextReplaceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TextReplaceController.class)
class TextReplaceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TextReplaceService textReplaceService;
    
    private final static String BASE_URL = "/api/v1/texts";

    @Test
    @DisplayName("Should return 200 OK with replaced text for 'elephant'")
    void testReplaceEndpoint_Elephant() throws Exception {
        String input = "elephant";
        TextReplaceDto dto = TextReplaceDto.builder()
                .originalText(input)
                .replacedText("*lephan$")
                .build();

        when(textReplaceService.processTextReplacement(input)).thenReturn(dto);
        mockMvc.perform(get(BASE_URL + "/replace")
                        .param("text", input))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.originalText").value("elephant"))
                .andExpect(jsonPath("$.replacedText").value("*lephan$"));
        verify(textReplaceService, times(1)).processTextReplacement(input);
    }

    @Test
    void testReplaceEndpoint_Home() throws Exception {
        String input = "home";
        TextReplaceDto dto = TextReplaceDto.builder()
                .originalText(input)
                .replacedText("*om$")
                .build();
        when(textReplaceService.processTextReplacement(input)).thenReturn(dto);
        mockMvc.perform(get(BASE_URL + "/replace")
                        .param("text", input))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.originalText").value("home"))
                .andExpect(jsonPath("$.replacedText").value("*om$"));
    }

    @Test
    void testReplaceEndpoint_MixedCharacters() throws Exception {
        String input = "abc#20xyz";
        TextReplaceDto dto = TextReplaceDto.builder()
                .originalText(input)
                .replacedText("*bc#20xy$")
                .build();

        when(textReplaceService.processTextReplacement(input)).thenReturn(dto);
        mockMvc.perform(get(BASE_URL + "/replace")
                        .param("text", input))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.originalText").value("abc#20xyz"))
                .andExpect(jsonPath("$.replacedText").value("*bc#20xy$"));
    }

    @Test
    void testReplaceEndpoint_ThreeCharacters() throws Exception {
        String input = "abc";
        TextReplaceDto dto = TextReplaceDto.builder()
                .originalText(input)
                .replacedText("*b$")
                .build();
        when(textReplaceService.processTextReplacement(input)).thenReturn(dto);
        mockMvc.perform(get(BASE_URL + "/replace")
                        .param("text", input))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.originalText").value("abc"))
                .andExpect(jsonPath("$.replacedText").value("*b$"));
    }

    @Test
    void testReplaceEndpoint_LongString() throws Exception {
        String input = "TetingCodeAssignmentProject";
        TextReplaceDto dto = TextReplaceDto.builder()
                .originalText(input)
                .replacedText("*etingCodeAssignmentProjec$")
                .build();
        when(textReplaceService.processTextReplacement(input)).thenReturn(dto);
        mockMvc.perform(get(BASE_URL + "/replace")
                        .param("text", input))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.originalText").value("TetingCodeAssignmentProject"))
                .andExpect(jsonPath("$.replacedText").value("*etingCodeAssignmentProjec$"));
    }

    @Test
    void testReplaceEndpoint_WithSpaces() throws Exception {
        String input = "My Test Project";
        TextReplaceDto dto = TextReplaceDto.builder()
                .originalText(input)
                .replacedText("*y Test Projec$")
                .build();
        when(textReplaceService.processTextReplacement(input)).thenReturn(dto);
        mockMvc.perform(get(BASE_URL + "/replace")
                        .param("text", input))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.originalText").value("My Test Project"))
                .andExpect(jsonPath("$.replacedText").value("*y Test Projec$"));
    }

    @Test
    void testReplaceEndpoint_TwoCharacters() throws Exception {
        String input = "ab";
        when(textReplaceService.processTextReplacement(input)).thenReturn(null);
        mockMvc.perform(get(BASE_URL + "/replace")
                        .param("text", input))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
        verify(textReplaceService, times(1)).processTextReplacement(input);
    }

    @Test
    void testReplaceEndpoint_TwoCharactersUppercase() throws Exception {
        String input = "XY";
        when(textReplaceService.processTextReplacement(input)).thenReturn(null);
        mockMvc.perform(get(BASE_URL + "/replace")
                        .param("text", input))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void testReplaceEndpoint_EmptyString() throws Exception {
        String input = "";
        when(textReplaceService.processTextReplacement(input))
                .thenThrow(new IllegalArgumentException("Text length must be at least 2 characters"));
        mockMvc.perform(get(BASE_URL + "/replace")
                        .param("text", input))
                .andExpect(status().isBadRequest());
        verify(textReplaceService, times(1)).processTextReplacement(input);
    }

    @Test
    void testReplaceEndpoint_OneCharacter() throws Exception {
        String input = "a";
        when(textReplaceService.processTextReplacement(input))
                .thenThrow(new IllegalArgumentException("Text length must be at least 2 characters"));
        mockMvc.perform(get(BASE_URL + "/replace")
                        .param("text", input))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should handle missing text parameter")
    void testReplaceEndpoint_MissingParameter() throws Exception {
        mockMvc.perform(get(BASE_URL + "/replace"))
                .andExpect(status().isBadRequest());
        verify(textReplaceService, never()).processTextReplacement(anyString());
    }

    @Test
    @DisplayName("Should handle numbers only '123'")
    void testReplaceEndpoint_NumbersOnly() throws Exception {
        String input = "123";
        TextReplaceDto dto = TextReplaceDto.builder()
                .originalText(input)
                .replacedText("*2$")
                .build();
        when(textReplaceService.processTextReplacement(input)).thenReturn(dto);
        mockMvc.perform(get(BASE_URL + "/replace")
                        .param("text", input))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.originalText").value("123"))
                .andExpect(jsonPath("$.replacedText").value("*2$"));
    }

    @Test
    @DisplayName("Should handle special characters only '!@#'")
    void testReplaceEndpoint_SpecialCharactersOnly() throws Exception {
        String input = "!@#";
        TextReplaceDto dto = TextReplaceDto.builder()
                .originalText(input)
                .replacedText("*@$")
                .build();
        when(textReplaceService.processTextReplacement(input)).thenReturn(dto);
        mockMvc.perform(get(BASE_URL + "/replace")
                        .param("text", input))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.originalText").value("!@#"))
                .andExpect(jsonPath("$.replacedText").value("*@$"));
    }
}