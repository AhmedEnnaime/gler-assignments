package com.gler.assignment.controllers;

import com.gler.assignment.dto.TextReplaceDto;
import com.gler.assignment.services.TextReplaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/texts")
@RequiredArgsConstructor
@Slf4j
public class TextReplaceController {

    private final TextReplaceService textReplaceService;

    @GetMapping("/replace")
    public ResponseEntity<TextReplaceDto> replaceText(@RequestParam String text) {
        try {
            TextReplaceDto result = textReplaceService.processTextReplacement(text);
            if (result == null)
                return ResponseEntity.ok().build();
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            log.error("Invalid text length: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}