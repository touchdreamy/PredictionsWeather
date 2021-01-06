package com.weather.java;

import lombok.Data;
import org.jsoup.nodes.Element;

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
        this.temperatureDay = setCelsiusSign(temperatureDay.text());
        this.temperatureNight = setCelsiusSign(temperatureNight.text());
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
        StringBuilder str = new StringBuilder(data);
        StringBuilder strOut = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                strOut.append(str.charAt(i));
            }
        }
        return strOut.toString();
    }

    private String getDay(String data) {
        String day = "";
        switch (data) {
            case "Пн", "понедельник", "пн":
                day = "Понедельник";
                break;
            case "Вт", "вторник", "вт":
                day = "Вторник";
                break;
            case "Ср", "среда", "ср":
                day = "Среда";
                break;
            case "Чт", "четверг", "чт":
                day = "Четверг";
                break;
            case "Пт", "пятница", "пт":
                day = "Пятница";
                break;
            case "сб", "суббота", "Сб":
                day = "Суббота";
                break;
            case "Вс", "воскресенье", "вс":
                day = "Воскресенье";
                break;
            default:
                day = data;
                break;
        }
        return day;
    }

    private String setCelsiusSign(String data)
    {
        StringBuilder str = new StringBuilder(data);
        if (str.charAt(str.length() - 1) != '°')
        {
            return str.append('°').toString();
        }
        return str.toString();
    }

}
