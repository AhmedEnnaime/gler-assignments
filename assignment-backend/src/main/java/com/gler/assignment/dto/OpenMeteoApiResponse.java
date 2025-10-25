package com.gler.assignment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenMeteoApiResponse {

    private Double latitude;
    private Double longitude;

    @JsonProperty("generationtime_ms")
    private Double generationtimeMs;

    @JsonProperty("utc_offset_seconds")
    private Integer utcOffsetSeconds;

    private String timezone;

    @JsonProperty("timezone_abbreviation")
    private String timezoneAbbreviation;

    private Double elevation;

    @JsonProperty("current_units")
    private CurrentUnits currentUnits;

    private Current current;

    @JsonProperty("hourly_units")
    private HourlyUnits hourlyUnits;

    private Hourly hourly;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CurrentUnits {
        private String time;
        private String interval;

        @JsonProperty("temperature_2m")
        private String temperature2m;

        @JsonProperty("wind_speed_10m")
        private String windSpeed10m;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Current {
        private String time;
        private Integer interval;

        @JsonProperty("temperature_2m")
        private Double temperature2m;

        @JsonProperty("wind_speed_10m")
        private Double windSpeed10m;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class HourlyUnits {
        private String time;

        @JsonProperty("temperature_2m")
        private String temperature2m;

        @JsonProperty("relative_humidity_2m")
        private String relativeHumidity2m;

        @JsonProperty("wind_speed_10m")
        private String windSpeed10m;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Hourly {
        private List<String> time;

        @JsonProperty("temperature_2m")
        private List<Double> temperature2m;

        @JsonProperty("relative_humidity_2m")
        private List<Integer> relativeHumidity2m;

        @JsonProperty("wind_speed_10m")
        private List<Double> windSpeed10m;
    }
}