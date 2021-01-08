package com.weather.java;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class Day
{
    private String dayOfMonth;
    private String eventOfDay;
    private String dayOfWeek;
    private String temperatureDay;
    private String temperatureNight;

    private String nameCity;
    private Double temperature;
    private Double temperatureMin;
    private Double temperatureMax;
    private Double feelsLike;
    private Double humidity;
    private Double clouds;

}
