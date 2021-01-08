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

public class GetGismeteoWeatherPrediction extends GetWeatherPrediction{

    @Override
    public List<Day> getPrediction(int countDays)
    {
        List<Day> prediction = new ArrayList<>();

        Element tableWeather = getDocument().select("div.widget__body").first();
        Elements dayOfWeek   = tableWeather.select("div.w_date__day");
        Elements dayOfMonth  = tableWeather.select("span.w_date__date");
        Elements eventOfDay  = tableWeather.select("span.tooltip");
        Elements dayTemperature = tableWeather.select("div.maxt > span.unit.unit_temperature_c");
        Elements nightTemperature = tableWeather.select("div.mint > span.unit.unit_temperature_c");

        for (int i = 0; i < countDays; i++) {
            Day day = new Day()
                    .setDayOfWeek(Utilities.getDay(dayOfWeek.get(i).text()))
                    .setDayOfMonth(Utilities.getNumber(dayOfMonth.get(i).text()))
                    .setEventOfDay(eventOfDay.get(i).attr("data-text"))
                    .setTemperatureDay(Utilities.setCelsiusSign(dayTemperature.get(i).text()))
                    .setTemperatureNight(Utilities.setCelsiusSign(nightTemperature.get(i).text()));
            prediction.add(i, day);
        }

        return prediction;
    }

    @SneakyThrows
    private Document getDocument() {
        return Jsoup.connect("https://www.gismeteo.ru/weather-nizhny-novgorod-4355/10-days/").get();
    }
}
