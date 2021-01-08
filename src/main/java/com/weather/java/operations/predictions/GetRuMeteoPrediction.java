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

public class GetRuMeteoPrediction extends GetWeatherPrediction{

    @Override
    public List<Day> getPrediction(int countDays)
    {
        List<Day> prediction = new ArrayList<>();

        Element tableWeather = getDocument().select("table#forecast").first();
        Elements dayOfWeek   = tableWeather.select("td > div.dt > big");
        Elements dayOfMonth  = tableWeather.select("div.dt:matchText");
        Elements eventOfDay  = tableWeather.select("div.wtr > img");
        Elements dayTemperature = tableWeather.select("div.wtr > big");
        Elements nightTemperature = tableWeather.select("div.nprt > span");

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
        return Jsoup.connect("https://ru-meteo.ru/nizhniy-novgorod").get();
    }
}
