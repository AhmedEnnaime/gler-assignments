package com.gler.assignment.controllers;

import com.gler.assignment.exceptions.UpstreamApiException;
import com.gler.assignment.services.ForecastService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ForecastController.class)
class ForecastControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ForecastService forecastService;

    private static final String BASE_URL = "/api/v1/forecasts";

    @Test
    void GivenValidRequestWhenProcessForecastThenReturn200() throws Exception {
        String json = """
            {
              "addTemprature": true,
              "addHumidity": true,
              "addWindSpeed": true
            }
            """;

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "{invalid json}"
    })
    void GivenInvalidRequestBodyWhenProcessForecastThenReturn400(String invalidBody) throws Exception {
        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void GivenExternalApiFailureWhenProcessForecastThenReturn502() throws Exception {
        when(forecastService.processForecast(Mockito.any()))
                .thenThrow(new UpstreamApiException("Connection to the upstream is unreachable"));

        String json = """
        {
          "addTemprature": true,
          "addHumidity": true,
          "addWindSpeed": true
        }
        """;

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadGateway())
                .andExpect(jsonPath("$.error").value("Upstream API Unreachable"));
    }
}
