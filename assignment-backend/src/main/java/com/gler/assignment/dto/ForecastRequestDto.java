package com.gler.assignment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForecastRequestDto {

    @NotNull(message = "addTemprature field is mandatory")
    private Boolean addTemprature;

    @NotNull(message = "addHumidity field is mandatory")
    private Boolean addHumidity;

    @NotNull(message = "addWindSpeed field is mandatory")
    private Boolean addWindSpeed;
}