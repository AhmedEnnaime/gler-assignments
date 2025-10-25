package com.gler.assignment.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "forecasts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForecastEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate forecastDate;

    @Column(name = "max_temperature")
    private Double maxTemperature;

    @Column(name = "max_humidity")
    private Double maxHumidity;

    @Column(name = "max_wind_speed")
    private Double maxWindSpeed;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}