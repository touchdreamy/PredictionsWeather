package com.weather.java;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.nodes.Element;

import java.util.Scanner;

@Data
public class Day
{
    String dayOfWeek;
    String dayOfMonth;
    String eventOfDay;
    String temperatureDay;
    String temperatureNight;
    String nameCity;
    Double temperature;
    Double temperatureMin;
    Double temperatureMax;
    Double feelsLike;
    Double humidity;
    Double clouds;


    public Day(Element dayOfWeek,Element dayOfMonth, Element eventOfDay, String attrEvent, Element temperatureDay, Element temperatureNight){
        this.dayOfWeek = getDay(dayOfWeek.text());
        this.dayOfMonth = getNumber(dayOfMonth.text());
        this.eventOfDay = eventOfDay.attr(attrEvent);
        this.temperatureDay = temperatureDay.text();
        this.temperatureNight = temperatureNight.text();
    }

    public Day(String name, String eventOfDay, Double clouds, Double humidity, Double temperature, Double feelsLike, Double temperatureMax, Double temperatureMin) {
        this.nameCity = name;
        this.eventOfDay = eventOfDay;
        this.clouds = clouds;
        this.humidity = humidity;
        this.temperature = temperature;
        this.feelsLike = feelsLike;
        this.temperatureMax = temperatureMax;
        this.temperatureMin = temperatureMin;
    }

    public void printInfo() {
        System.out.println("Погода на сегодня в городе: " + nameCity);
        System.out.println(" - " + eventOfDay);
        System.out.println(" - Облачность  = " + clouds + "%");
        System.out.println(" - Влажность   = " + humidity + "°");
        System.out.println(" - Температура = " + temperature + "°");
        System.out.println(" - Ощущается   = " + feelsLike + "°");
        System.out.println(" - Максимум    = " + temperatureMax + "°");
        System.out.println(" - Минимум     = " + temperatureMin + "°");
    }

    private String getNumber(String data) {
        int num = 0;
        try (Scanner s = new Scanner(data)) {
            while (s.hasNextInt()) {
                num = s.nextInt();
            }
        }
        return String.valueOf(num);
    }

    private String getDay(String data) {
        String day = "";
        switch (data) {
            case "Понедельник":
                day = "Пн";
                break;
            case "Вторник":
                day = "Вт";
                break;
            case "Среда":
                day = "Ср";
                break;
            case "Четверг":
                day = "Чт";
                break;
            case "Пятница":
                day = "Пт";
                break;
            case "Суббота":
                day = "Сб";
                break;
            case "Воскресенье":
                day = "Вс";
                break;
            default:
                day = data;
                break;
        }
        return day;
    }

}
