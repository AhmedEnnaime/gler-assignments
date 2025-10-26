package com.gler.assignment.services;

import com.gler.assignment.client.OpenMeteoClient;
import com.gler.assignment.dto.ForecastRequestDto;
import com.gler.assignment.dto.ForecastResponseDto;
import com.gler.assignment.dto.OpenMeteoApiResponse;
import com.gler.assignment.entities.ForecastEntity;
import com.gler.assignment.exceptions.UpstreamApiException;
import com.gler.assignment.repositories.ForecastRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ForecastServiceTest {

    @Mock
    private OpenMeteoClient openMeteoClient;

    @Mock
    private ForecastRepository forecastRepository;

    @InjectMocks
    private ForecastService forecastService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private OpenMeteoApiResponse buildApiResponse(
            List<Double> temperatures,
            List<Integer> humidities,
            List<Double> windSpeeds) {
        OpenMeteoApiResponse apiResponse = new OpenMeteoApiResponse();
        OpenMeteoApiResponse.Hourly hourly = new OpenMeteoApiResponse.Hourly();
        hourly.setTemperature2m(temperatures);
        hourly.setRelativeHumidity2m(humidities);
        hourly.setWindSpeed10m(windSpeeds);
        apiResponse.setHourly(hourly);
        return apiResponse;
    }

    @Test
    void givenValidRequestWhenProcessForecastThenReturnMaxValues() {
        ForecastRequestDto request = new ForecastRequestDto(true, true, true);
        OpenMeteoApiResponse apiResponse = buildApiResponse(
                Arrays.asList(10.5, 15.2, 20.3),
                Arrays.asList(50, 65, 70),
                Arrays.asList(5.0, 8.0, 12.0)
        );
        when(openMeteoClient.fetchForecastData()).thenReturn(apiResponse);
        when(forecastRepository.save(any(ForecastEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        ForecastResponseDto result = forecastService.processForecast(request);
        assertNotNull(result);
        assertEquals(20.3, result.getMaxTemperature());
        assertEquals(70.0, result.getMaxHumidity());
        assertEquals(12.0, result.getMaxWindSpeed());
    }

    @Test
    void givenExternalApiFailureWhenProcessForecastThenThrowUpstreamApiException() {
        ForecastRequestDto request = new ForecastRequestDto(true, true, true);
        when(openMeteoClient.fetchForecastData()).thenThrow(new UpstreamApiException("Connection failed"));
        assertThrows(UpstreamApiException.class, () -> forecastService.processForecast(request));
    }

    @Test
    void givenAllParametersFalseWhenProcessForecastThenReturnEmptyResponse() {
        ForecastRequestDto request = new ForecastRequestDto(false, false, false);
        when(forecastRepository.save(any(ForecastEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        ForecastResponseDto result = forecastService.processForecast(request);
        assertNull(result.getMaxTemperature());
        assertNull(result.getMaxHumidity());
        assertNull(result.getMaxWindSpeed());
    }

    @Test
    void givenNullRequestWhenProcessForecastThenThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> forecastService.processForecast(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"empty", "negative", "nulls"})
    void testTemperatureEdgeCases(String caseType) {
        ForecastRequestDto request = new ForecastRequestDto(true, false, false);
        OpenMeteoApiResponse apiResponse;

        switch (caseType) {
            case "empty" -> apiResponse = buildApiResponse(Collections.emptyList(), null, null);
            case "negative" -> apiResponse = buildApiResponse(Arrays.asList(-10.5, -5.3, -7.8), null, null);
            case "nulls" -> apiResponse = buildApiResponse(Arrays.asList(null, 10.0, 15.0, null), null, null);
            default -> throw new IllegalArgumentException("Invalid test case");
        }

        when(openMeteoClient.fetchForecastData()).thenReturn(apiResponse);
        when(forecastRepository.save(any(ForecastEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        ForecastResponseDto result = forecastService.processForecast(request);
        switch (caseType) {
            case "empty" -> assertNull(result.getMaxTemperature());
            case "negative" -> assertEquals(-5.3, result.getMaxTemperature());
            case "nulls" -> assertEquals(15.0, result.getMaxTemperature());
        }
    }

    @ParameterizedTest
    @NullAndEmptySource
    void givenNullOrEmptyTemperatureListThenReturnNullTemperature(List<Double> temperatures) {
        ForecastRequestDto request = new ForecastRequestDto(true, false, false);
        OpenMeteoApiResponse apiResponse = buildApiResponse(temperatures, null, null);
        when(openMeteoClient.fetchForecastData()).thenReturn(apiResponse);
        when(forecastRepository.save(any(ForecastEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        ForecastResponseDto result = forecastService.processForecast(request);
        assertNull(result.getMaxTemperature());
    }
}
