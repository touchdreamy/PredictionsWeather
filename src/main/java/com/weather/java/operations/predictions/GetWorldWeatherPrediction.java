package com.weather.java.operations.predictions;

import com.weather.java.Day;
import com.weather.java.operations.Utilities;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class GetWorldWeatherPrediction extends GetWeatherPrediction{

    @Override
    public List<Day> getPrediction(int countDays)
    {
        List<Day> prediction = new ArrayList<>();

        Element tableWeather = getDocument().select("div#content-left").first();
        Elements dayOfWeek   = tableWeather.select("div.day-week");
        Elements dayOfMonth  = tableWeather.select("div.numbers-month");
        Elements eventOfDay  = tableWeather.select("span.icon-weather");
        Elements dayTemperature = tableWeather.select("div.day-temperature");
        Elements nightTemperature = tableWeather.select("div.night-temperature");

        for (int i = 0; i < countDays; i++) {
            Day day = new Day()
                    .setDayOfWeek(Utilities.getDay(dayOfWeek.get(i).text()))
                    .setDayOfMonth(Utilities.getNumber(dayOfMonth.get(i).text()))
                    .setEventOfDay(eventOfDay.get(i).attr("title"))
                    .setTemperatureDay(Utilities.setCelsiusSign(dayTemperature.get(i).text()))
                    .setTemperatureNight(Utilities.setCelsiusSign(nightTemperature.get(i).text()));
            prediction.add(i, day);
        }

        return prediction;
    }

    @SneakyThrows
    private Document getDocument() {
        return Jsoup.connect("https://world-weather.ru/pogoda/russia/nizhny_novgorod/").get();
    }
}
