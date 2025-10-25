package com.gler.assignment.services;

import com.gler.assignment.client.OpenMeteoClient;
import com.gler.assignment.dto.ForecastRequestDto;
import com.gler.assignment.dto.ForecastResponseDto;
import com.gler.assignment.dto.OpenMeteoApiResponse;
import com.gler.assignment.entities.ForecastEntity;
import com.gler.assignment.repositories.ForecastRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ForecastService {

    private final OpenMeteoClient openMeteoClient;
    private final ForecastRepository forecastRepository;

    public ForecastResponseDto processForecast(ForecastRequestDto request) {
        if (request == null)
            throw new IllegalArgumentException("Forecast request must not be null");
        log.info("Processing forecast request: {}", request);
        OpenMeteoApiResponse apiResponse = openMeteoClient.fetchForecastData();
        Double maxTemperature = null;
        Double maxHumidity = null;
        Double maxWindSpeed = null;
        if (request.getAddTemprature() && apiResponse.getHourly() != null)
            maxTemperature = extractMaxTemperature(apiResponse.getHourly().getTemperature2m());

        if (request.getAddHumidity() && apiResponse.getHourly() != null)
            maxHumidity = extractMaxHumidity(apiResponse.getHourly().getRelativeHumidity2m());

        if (request.getAddWindSpeed() && apiResponse.getHourly() != null)
            maxWindSpeed = extractMaxWindSpeed(apiResponse.getHourly().getWindSpeed10m());

        LocalDate forecastDate = LocalDate.now();
        ForecastEntity entity = ForecastEntity.builder()
                .forecastDate(forecastDate)
                .maxTemperature(maxTemperature)
                .maxHumidity(maxHumidity)
                .maxWindSpeed(maxWindSpeed)
                .build();
        forecastRepository.save(entity);
        return ForecastResponseDto.builder()
                .date(forecastDate)
                .maxTemperature(maxTemperature)
                .maxHumidity(maxHumidity)
                .maxWindSpeed(maxWindSpeed)
                .build();
    }

    private Double extractMaxTemperature(List<Double> temperatures) {
        if (temperatures == null || temperatures.isEmpty())
            return null;
        return Collections.max(temperatures.stream()
                .filter(Objects::nonNull)
                .toList());
    }

    private Double extractMaxHumidity(List<Integer> humidities) {
        if (humidities == null || humidities.isEmpty())
            return null;
        return Collections.max(humidities.stream()
                .filter(Objects::nonNull)
                .toList()).doubleValue();
    }

    private Double extractMaxWindSpeed(List<Double> windSpeeds) {
        if (windSpeeds == null || windSpeeds.isEmpty())
            return null;
        return Collections.max(windSpeeds.stream()
                .filter(Objects::nonNull)
                .toList());
    }
}