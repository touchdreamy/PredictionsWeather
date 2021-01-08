package com.weather.java.operations;

import com.weather.java.Day;

public class Operations
{
    public static void printWeatherDay(Day currentDay) {
        System.out.println("Погода на сегодня в городе: " + currentDay.getNameCity());
        System.out.println(" - " + currentDay.getEventOfDay());
        System.out.println(" - Облачность  = " + currentDay.getClouds() + "%");
        System.out.println(" - Влажность   = " + currentDay.getHumidity() + "°");
        System.out.println(" - Температура = " + currentDay.getTemperature() + "°");
        System.out.println(" - Ощущается   = " + currentDay.getFeelsLike() + "°");
        System.out.println(" - Максимум    = " + currentDay.getTemperatureMax() + "°");
        System.out.println(" - Минимум     = " + currentDay.getTemperatureMin() + "°");
    }
}
