package com.gler.assignment.client;

import com.gler.assignment.dto.OpenMeteoApiResponse;
import com.gler.assignment.exceptions.UpstreamApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OpenMeteoClientTest {

    @Mock
    private RestTemplate restTemplate;

    private OpenMeteoClient openMeteoClient;

    private static final String API_URL = "https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&current=temperature_2m,wind_speed_10m&hourly=temperature_2m,relative_humidity_2m,wind_speed_10m";

    @BeforeEach
    void setUp() {
        openMeteoClient = new OpenMeteoClient(restTemplate, API_URL);
    }

    @Test
    void givenValidApiResponseWhenFetchForecastDataThenReturnCompleteResponse() {
        OpenMeteoApiResponse givenResponse = createMockApiResponse();
        when(restTemplate.getForObject(eq(API_URL), eq(OpenMeteoApiResponse.class)))
                .thenReturn(givenResponse);

        OpenMeteoApiResponse result = openMeteoClient.fetchForecastData();

        assertNotNull(result);
        assertEquals(givenResponse.getLatitude(), result.getLatitude());
        assertEquals(givenResponse.getLongitude(), result.getLongitude());
        assertNotNull(result.getHourly());
        assertEquals(5, result.getHourly().getTemperature2m().size());
        verify(restTemplate, times(1)).getForObject(eq(API_URL), eq(OpenMeteoApiResponse.class));
    }

    @Test
    void givenResponseWithAllFieldsWhenFetchForecastDataThenReturnAllHourlyData() {
        OpenMeteoApiResponse givenResponse = createMockApiResponse();
        when(restTemplate.getForObject(eq(API_URL), eq(OpenMeteoApiResponse.class)))
                .thenReturn(givenResponse);

        OpenMeteoApiResponse result = openMeteoClient.fetchForecastData();

        assertNotNull(result.getHourly());
        assertNotNull(result.getHourly().getTemperature2m());
        assertNotNull(result.getHourly().getRelativeHumidity2m());
        assertNotNull(result.getHourly().getWindSpeed10m());
        assertFalse(result.getHourly().getTemperature2m().isEmpty());
        assertFalse(result.getHourly().getRelativeHumidity2m().isEmpty());
        assertFalse(result.getHourly().getWindSpeed10m().isEmpty());
    }

    @Test
    void givenResponseWithEmptyListsWhenFetchForecastDataThenReturnEmptyHourlyLists() {
        OpenMeteoApiResponse givenResponse = new OpenMeteoApiResponse();
        givenResponse.setLatitude(52.52);
        givenResponse.setLongitude(13.41);

        OpenMeteoApiResponse.Hourly hourly = new OpenMeteoApiResponse.Hourly();
        hourly.setTemperature2m(Collections.emptyList());
        hourly.setRelativeHumidity2m(Collections.emptyList());
        hourly.setWindSpeed10m(Collections.emptyList());
        givenResponse.setHourly(hourly);

        when(restTemplate.getForObject(eq(API_URL), eq(OpenMeteoApiResponse.class)))
                .thenReturn(givenResponse);

        OpenMeteoApiResponse result = openMeteoClient.fetchForecastData();

        assertNotNull(result);
        assertTrue(result.getHourly().getTemperature2m().isEmpty());
        assertTrue(result.getHourly().getRelativeHumidity2m().isEmpty());
        assertTrue(result.getHourly().getWindSpeed10m().isEmpty());
    }

    @Test
    void givenResponseWithCurrentWeatherWhenFetchForecastDataThenReturnCurrentWeatherData() {
        OpenMeteoApiResponse givenResponse = createMockApiResponse();

        OpenMeteoApiResponse.Current current = new OpenMeteoApiResponse.Current();
        current.setTime("2025-10-24T12:00");
        current.setInterval(900);
        current.setTemperature2m(20.5);
        current.setWindSpeed10m(12.3);
        givenResponse.setCurrent(current);

        when(restTemplate.getForObject(eq(API_URL), eq(OpenMeteoApiResponse.class)))
                .thenReturn(givenResponse);

        OpenMeteoApiResponse result = openMeteoClient.fetchForecastData();

        assertNotNull(result.getCurrent());
        assertEquals(20.5, result.getCurrent().getTemperature2m());
        assertEquals(12.3, result.getCurrent().getWindSpeed10m());
    }

    @Test
    void givenNullResponseWhenFetchForecastDataThenThrowUpstreamApiException() {
        when(restTemplate.getForObject(eq(API_URL), eq(OpenMeteoApiResponse.class)))
                .thenReturn(null);

        UpstreamApiException exception = assertThrows(
                UpstreamApiException.class,
                () -> openMeteoClient.fetchForecastData()
        );

        assertEquals("Received null response from upstream API", exception.getMessage());
        verify(restTemplate, times(1)).getForObject(eq(API_URL), eq(OpenMeteoApiResponse.class));
    }

    @Test
    void givenRestClientExceptionWhenFetchForecastDataThenThrowUpstreamApiException() {
        when(restTemplate.getForObject(eq(API_URL), eq(OpenMeteoApiResponse.class)))
                .thenThrow(new RestClientException("Connection refused"));

        UpstreamApiException exception = assertThrows(
                UpstreamApiException.class,
                () -> openMeteoClient.fetchForecastData()
        );

        assertEquals("Connection to the upstream is unreachable", exception.getMessage());
        assertNotNull(exception.getCause());
        assertTrue(exception.getCause() instanceof RestClientException);
        verify(restTemplate, times(1)).getForObject(eq(API_URL), eq(OpenMeteoApiResponse.class));
    }

    @Test
    void givenConnectionTimeoutWhenFetchForecastDataThenThrowUpstreamApiException() {
        ResourceAccessException timeoutException = new ResourceAccessException(
                "I/O error on GET request",
                new SocketTimeoutException("Read timed out")
        );

        when(restTemplate.getForObject(eq(API_URL), eq(OpenMeteoApiResponse.class)))
                .thenThrow(timeoutException);

        UpstreamApiException exception = assertThrows(
                UpstreamApiException.class,
                () -> openMeteoClient.fetchForecastData()
        );

        assertEquals("Connection to the upstream is unreachable", exception.getMessage());
        assertNotNull(exception.getCause());
        verify(restTemplate, times(1)).getForObject(eq(API_URL), eq(OpenMeteoApiResponse.class));
    }

    @Test
    void givenHttp4xxErrorWhenFetchForecastDataThenThrowUpstreamApiException() {
        HttpClientErrorException clientException = HttpClientErrorException.create(
                org.springframework.http.HttpStatus.BAD_REQUEST,
                "Bad Request",
                null,
                null,
                null
        );

        when(restTemplate.getForObject(eq(API_URL), eq(OpenMeteoApiResponse.class)))
                .thenThrow(clientException);

        UpstreamApiException exception = assertThrows(
                UpstreamApiException.class,
                () -> openMeteoClient.fetchForecastData()
        );

        assertEquals("Connection to the upstream is unreachable", exception.getMessage());
        verify(restTemplate, times(1)).getForObject(eq(API_URL), eq(OpenMeteoApiResponse.class));
    }

    @Test
    void givenHttp5xxErrorWhenFetchForecastDataThenThrowUpstreamApiException() {
        HttpServerErrorException serverException = HttpServerErrorException.create(
                org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error",
                null,
                null,
                null
        );

        when(restTemplate.getForObject(eq(API_URL), eq(OpenMeteoApiResponse.class)))
                .thenThrow(serverException);

        UpstreamApiException exception = assertThrows(
                UpstreamApiException.class,
                () -> openMeteoClient.fetchForecastData()
        );

        assertEquals("Connection to the upstream is unreachable", exception.getMessage());
        verify(restTemplate, times(1)).getForObject(eq(API_URL), eq(OpenMeteoApiResponse.class));
    }

    @Test
    void givenNetworkUnreachableWhenFetchForecastDataThenThrowUpstreamApiException() {
        ResourceAccessException networkException = new ResourceAccessException(
                "Network is unreachable"
        );

        when(restTemplate.getForObject(eq(API_URL), eq(OpenMeteoApiResponse.class)))
                .thenThrow(networkException);

        UpstreamApiException exception = assertThrows(
                UpstreamApiException.class,
                () -> openMeteoClient.fetchForecastData()
        );

        assertEquals("Connection to the upstream is unreachable", exception.getMessage());
        assertNotNull(exception.getCause());
    }

    @Test
    void givenResponseWithNullHourlyDataWhenFetchForecastDataThenReturnResponseWithNullHourly() {
        OpenMeteoApiResponse givenResponse = new OpenMeteoApiResponse();
        givenResponse.setLatitude(52.52);
        givenResponse.setLongitude(13.41);
        givenResponse.setHourly(null);

        when(restTemplate.getForObject(eq(API_URL), eq(OpenMeteoApiResponse.class)))
                .thenReturn(givenResponse);

        OpenMeteoApiResponse result = openMeteoClient.fetchForecastData();

        assertNotNull(result);
        assertNull(result.getHourly());
    }

    @Test
    void givenResponseWithPartialHourlyDataWhenFetchForecastDataThenReturnPartialData() {
        OpenMeteoApiResponse givenResponse = new OpenMeteoApiResponse();
        givenResponse.setLatitude(52.52);
        givenResponse.setLongitude(13.41);

        OpenMeteoApiResponse.Hourly hourly = new OpenMeteoApiResponse.Hourly();
        hourly.setTemperature2m(Arrays.asList(15.0, 18.0, 22.0));
        hourly.setRelativeHumidity2m(null);
        hourly.setWindSpeed10m(Arrays.asList(10.5, 12.0));
        givenResponse.setHourly(hourly);

        when(restTemplate.getForObject(eq(API_URL), eq(OpenMeteoApiResponse.class)))
                .thenReturn(givenResponse);

        OpenMeteoApiResponse result = openMeteoClient.fetchForecastData();

        assertNotNull(result);
        assertNotNull(result.getHourly().getTemperature2m());
        assertNull(result.getHourly().getRelativeHumidity2m());
        assertNotNull(result.getHourly().getWindSpeed10m());
    }

    @Test
    void givenResponseWithNegativeTemperaturesWhenFetchForecastDataThenReturnNegativeTemperatures() {
        OpenMeteoApiResponse givenResponse = new OpenMeteoApiResponse();

        OpenMeteoApiResponse.Hourly hourly = new OpenMeteoApiResponse.Hourly();
        hourly.setTemperature2m(Arrays.asList(-10.0, -5.0, -15.0, -8.0));
        hourly.setRelativeHumidity2m(Arrays.asList(60, 65, 70, 68));
        hourly.setWindSpeed10m(Arrays.asList(10.5, 12.0, 15.5, 14.0));
        givenResponse.setHourly(hourly);

        when(restTemplate.getForObject(eq(API_URL), eq(OpenMeteoApiResponse.class)))
                .thenReturn(givenResponse);

        OpenMeteoApiResponse result = openMeteoClient.fetchForecastData();

        assertNotNull(result);
        assertTrue(result.getHourly().getTemperature2m().stream()
                .allMatch(temp -> temp < 0));
    }

    private OpenMeteoApiResponse createMockApiResponse() {
        OpenMeteoApiResponse response = new OpenMeteoApiResponse();
        response.setLatitude(52.52);
        response.setLongitude(13.41);
        response.setGenerationtimeMs(0.5);
        response.setUtcOffsetSeconds(0);
        response.setTimezone("GMT");
        response.setTimezoneAbbreviation("GMT");
        response.setElevation(38.0);

        OpenMeteoApiResponse.Hourly hourly = new OpenMeteoApiResponse.Hourly();
        hourly.setTemperature2m(Arrays.asList(15.0, 18.0, 22.0, 20.0, 16.0));
        hourly.setRelativeHumidity2m(Arrays.asList(60, 65, 70, 68, 62));
        hourly.setWindSpeed10m(Arrays.asList(10.5, 12.0, 15.5, 14.0, 11.0));

        response.setHourly(hourly);

        return response;
    }
}