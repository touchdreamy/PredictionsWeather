package com.weather.java.operations.predictions;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GetGismeteoWeatherPrediction extends GetWeatherPrediction{

    public GetGismeteoWeatherPrediction(int countDays) {
        super(countDays);
    }

    @Override
    protected List<String> getDayOfWeek() {
        Elements data = page
                .select("div.widget__body").first()
                .select("div.w_date__day");
        return IntStream.range(0, countDays).mapToObj(i -> data.get(i).text()).collect(Collectors.toList());
    }

    @Override
    protected List<String> getDayOfMonth() {
        Elements data = page
                .select("div.widget__body").first()
                .select("span.w_date__date");
        return IntStream.range(0, countDays).mapToObj(i -> data.get(i).text()).collect(Collectors.toList());
    }

    @Override
    protected List<String> getEventOfDay() {
        Elements data = page
                .select("div.widget__body").first()
                .select("span.tooltip");
        return IntStream.range(0, countDays).mapToObj(i -> data.get(i).attr("data-text")).collect(Collectors.toList());
    }

    @Override
    protected List<String> getTemperatureDay() {
        Elements data = page
                .select("div.widget__body").first()
                .select("div.maxt > span.unit.unit_temperature_c");
        return IntStream.range(0, countDays).mapToObj(i -> data.get(i).text()).collect(Collectors.toList());
    }

    @Override
    protected List<String> getTemperatureNight() {
        Elements data = page
                .select("div.widget__body").first()
                .select("div.mint > span.unit.unit_temperature_c");
        return IntStream.range(0, countDays).mapToObj(i -> data.get(i).text()).collect(Collectors.toList());
    }

    @SneakyThrows
    protected Document getDocument() {
        return Jsoup.connect("https://www.gismeteo.ru/weather-nizhny-novgorod-4355/10-days/").get();
    }
}
