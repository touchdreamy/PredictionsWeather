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

public class GetMailRuWeatherPrediction extends GetWeatherPrediction{

    @Override
    public List<Day> getPrediction(int countDays)
    {
        List<Day> prediction = new ArrayList<>();

        Element tableWeather = getDocument().select(
                "body > div.g-layout.layout.js-module > " +
                        "div.sticky-springs.js-springs__group.js-module").first();

        Elements dayOfWeek   = tableWeather.select(
                "div.heading.heading_minor.heading_line > span.heading__inner_light");

        Elements dayOfMonth  = tableWeather.select("div.heading.heading_minor.heading_line");

        Elements eventOfDay  = tableWeather.select(
                "div > div.cols__column__item.cols__column__item_2-1.cols__column__item_2-1_ie8 > " +
                        "div:nth-child(3) > div.weather-icon[title]");

        Elements dayTemperature = tableWeather.select(
                "div > div.cols__column__item.cols__column__item_2-1.cols__column__item_2-1_ie8 > " +
                        "div:nth-child(3) > div.day__temperature");

        Elements nightTemperature = tableWeather.select(
                "div > div.cols__column__item.cols__column__item_2-1.cols__column__item_2-1_ie8 > " +
                        "div:nth-child(4) > div.day__temperature");

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
        return Jsoup.connect("https://pogoda.mail.ru/prognoz/nizhniy_novgorod/14dney/").get();
    }
}
