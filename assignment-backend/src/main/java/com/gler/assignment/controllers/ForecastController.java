package com.gler.assignment.controllers;

import com.gler.assignment.dto.ForecastRequestDto;
import com.gler.assignment.dto.ForecastResponseDto;
import com.gler.assignment.services.ForecastService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/forecasts")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ForecastController {

    private final ForecastService forecastService;

    @PostMapping
    public ResponseEntity<ForecastResponseDto> getForecast(@Valid @RequestBody ForecastRequestDto request) {
        return ResponseEntity.status(HttpStatus.OK).body(forecastService.processForecast(request));
    }
}