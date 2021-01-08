package com.weather.java.operations.predictions;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GetWorldWeatherPrediction extends GetWeatherPrediction{

    public GetWorldWeatherPrediction(int countDays) {
        super(countDays);
    }

    @Override
    protected List<String> getDayOfWeek() {
        Elements data = page
                .select("div#content-left").first()
                .select("div.day-week");
        return IntStream.range(0, countDays).mapToObj(i -> data.get(i).text()).collect(Collectors.toList());
    }

    @Override
    protected List<String> getDayOfMonth() {
        Elements data = page
                .select("div#content-left").first()
                .select("div.numbers-month");
        return IntStream.range(0, countDays).mapToObj(i -> data.get(i).text()).collect(Collectors.toList());
    }

    @Override
    protected List<String> getEventOfDay() {
        Elements data = page
                .select("div#content-left").first()
                .select("span.icon-weather");
        return IntStream.range(0, countDays).mapToObj(i -> data.get(i).attr("title")).collect(Collectors.toList());
    }

    @Override
    protected List<String> getTemperatureDay() {
        Elements data = page
                .select("div#content-left").first()
                .select("div.day-temperature");
        return IntStream.range(0, countDays).mapToObj(i -> data.get(i).text()).collect(Collectors.toList());
    }

    @Override
    protected List<String> getTemperatureNight() {
        Elements data = page
                .select("div#content-left").first()
                .select("div.night-temperature");
        return IntStream.range(0, countDays).mapToObj(i -> data.get(i).text()).collect(Collectors.toList());
    }

    @SneakyThrows
    protected Document getDocument() {
        return Jsoup.connect("https://world-weather.ru/pogoda/russia/nizhny_novgorod/").get();
    }
}
