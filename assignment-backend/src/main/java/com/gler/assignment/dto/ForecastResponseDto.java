package com.gler.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForecastResponseDto {

    private LocalDate date;
    private Double maxTemperature;
    private Double maxHumidity;
    private Double maxWindSpeed;
}