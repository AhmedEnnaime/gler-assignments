package com.gler.assignment.client;

import com.gler.assignment.dto.OpenMeteoApiResponse;
import com.gler.assignment.exceptions.UpstreamApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class OpenMeteoClient {

    private final RestTemplate restTemplate;
    private final String apiUrl;

    public OpenMeteoClient(
            RestTemplate restTemplate,
            @Value("${openmeteo.api.url:https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&current=temperature_2m,wind_speed_10m&hourly=temperature_2m,relative_humidity_2m,wind_speed_10m}")
            String apiUrl) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
    }

    public OpenMeteoApiResponse fetchForecastData() {
        log.info("Fetching forecast data from Open-Meteo API");
        try {
            OpenMeteoApiResponse response = restTemplate.getForObject(apiUrl, OpenMeteoApiResponse.class);
            if (response == null)
                throw new UpstreamApiException("Received null response from upstream API");
            log.info("Successfully fetched forecast data from Open-Meteo API");
            return response;
        } catch (RestClientException e) {
            log.error("Failed to fetch data from Open-Meteo API: {}", e.getMessage(), e);
            throw new UpstreamApiException("Connection to the upstream is unreachable", e);
        }
    }
}