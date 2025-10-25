package com.gler.assignment.controllers;

import com.gler.assignment.dto.TextReplaceDto;
import com.gler.assignment.services.TextReplaceService;
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

    private static final String BASE_URL = "/api/v1/texts";

    @Test
    void givenValidTextWhenReplaceTextThenReturnOkWithReplacedDto() throws Exception {
        String input = "elephant";
        TextReplaceDto dto = TextReplaceDto.builder()
                .originalText(input)
                .replacedText("*lephan$")
                .build();
        when(textReplaceService.processTextReplacement(input)).thenReturn(dto);
        mockMvc.perform(get(BASE_URL + "/replace").param("text", input))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.originalText").value("elephant"))
                .andExpect(jsonPath("$.replacedText").value("*lephan$"));

        verify(textReplaceService, times(1)).processTextReplacement(input);
    }

    @Test
    void givenThreeCharacterTextWhenReplaceTextThenReturnOkWithReplacedDto() throws Exception {
        String input = "abc";
        TextReplaceDto dto = TextReplaceDto.builder()
                .originalText(input)
                .replacedText("*b$")
                .build();
        when(textReplaceService.processTextReplacement(input)).thenReturn(dto);
        mockMvc.perform(get(BASE_URL + "/replace").param("text", input))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.originalText").value("abc"))
                .andExpect(jsonPath("$.replacedText").value("*b$"));
    }

    @Test
    void givenOnlyNumbersWhenReplaceTextThenReturnOkWithReplacedDto() throws Exception {
        String input = "123";
        TextReplaceDto dto = TextReplaceDto.builder()
                .originalText(input)
                .replacedText("*2$")
                .build();
        when(textReplaceService.processTextReplacement(input)).thenReturn(dto);
        mockMvc.perform(get(BASE_URL + "/replace").param("text", input))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.originalText").value("123"))
                .andExpect(jsonPath("$.replacedText").value("*2$"));
    }

    @Test
    void givenSpecialCharactersOnlyWhenReplaceTextThenReturnOkWithReplacedDto() throws Exception {
        String input = "!@#";
        TextReplaceDto dto = TextReplaceDto.builder()
                .originalText(input)
                .replacedText("*@$")
                .build();
        when(textReplaceService.processTextReplacement(input)).thenReturn(dto);
        mockMvc.perform(get(BASE_URL + "/replace").param("text", input))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.originalText").value("!@#"))
                .andExpect(jsonPath("$.replacedText").value("*@$"));
    }

    @Test
    void givenTwoCharacterTextWhenReplaceTextThenReturnOkWithEmptyBody() throws Exception {
        String input = "ab";
        when(textReplaceService.processTextReplacement(input)).thenReturn(null);
        mockMvc.perform(get(BASE_URL + "/replace").param("text", input))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
        verify(textReplaceService, times(1)).processTextReplacement(input);
    }

    @Test
    void givenInvalidTextLengthWhenReplaceTextThenReturnBadRequest() throws Exception {
        String input = "a";
        when(textReplaceService.processTextReplacement(input))
                .thenThrow(new IllegalArgumentException("Text length must be at least 2 characters"));

        mockMvc.perform(get(BASE_URL + "/replace").param("text", input))
                .andExpect(status().isBadRequest());

        verify(textReplaceService, times(1)).processTextReplacement(input);
    }

    @Test
    void givenEmptyStringWhenReplaceTextThenReturnBadRequest() throws Exception {
        String input = "";
        when(textReplaceService.processTextReplacement(input))
                .thenThrow(new IllegalArgumentException("Text length must be at least 2 characters"));
        mockMvc.perform(get(BASE_URL + "/replace").param("text", input))
                .andExpect(status().isBadRequest());
        verify(textReplaceService, times(1)).processTextReplacement(input);
    }

    @Test
    void givenMissingTextParameterWhenReplaceTextThenReturnBadRequest() throws Exception {
        mockMvc.perform(get(BASE_URL + "/replace"))
                .andExpect(status().isBadRequest());
        verify(textReplaceService, never()).processTextReplacement(anyString());
    }
}
