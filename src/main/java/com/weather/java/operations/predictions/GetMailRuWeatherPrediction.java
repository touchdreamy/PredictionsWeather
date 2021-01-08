package com.weather.java.operations.predictions;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GetMailRuWeatherPrediction extends GetWeatherPrediction{

    public GetMailRuWeatherPrediction(int countDays) {
        super(countDays);
    }

    @Override
    protected List<String> getDayOfWeek() {
        Elements data = page.
                select("body > div.g-layout.layout.js-module > " +
                        "div.sticky-springs.js-springs__group.js-module").first()
                .select("div.heading.heading_minor.heading_line > span.heading__inner_light");
        return IntStream.range(0, countDays).mapToObj(i -> data.get(i).text()).collect(Collectors.toList());
    }

    @Override
    protected List<String> getDayOfMonth() {
        Elements data = page.
                select("body > div.g-layout.layout.js-module > " +
                        "div.sticky-springs.js-springs__group.js-module").first()
                .select("div.heading.heading_minor.heading_line");
        return IntStream.range(0, countDays).mapToObj(i -> data.get(i).text()).collect(Collectors.toList());
    }

    @Override
    protected List<String> getEventOfDay() {
        Elements data = page
                .select("body > div.g-layout.layout.js-module > " +
                        "div.sticky-springs.js-springs__group.js-module").first()
                .select("div > div.cols__column__item.cols__column__item_2-1.cols__column__item_2-1_ie8 > " +
                        "div:nth-child(3) > div.weather-icon[title]");
        return IntStream.range(0, countDays).mapToObj(i -> data.get(i).attr("title")).collect(Collectors.toList());
    }

    @Override
    protected List<String> getTemperatureDay() {
        Elements data = page
                .select("body > div.g-layout.layout.js-module > " +
                        "div.sticky-springs.js-springs__group.js-module").first()
                .select("div > div.cols__column__item.cols__column__item_2-1.cols__column__item_2-1_ie8 > " +
                        "div:nth-child(3) > div.day__temperature");
        return IntStream.range(0, countDays).mapToObj(i -> data.get(i).text()).collect(Collectors.toList());
    }

    @Override
    protected List<String> getTemperatureNight() {
        Elements data = page
                .select("body > div.g-layout.layout.js-module > " +
                        "div.sticky-springs.js-springs__group.js-module").first()
                .select("div > div.cols__column__item.cols__column__item_2-1.cols__column__item_2-1_ie8 > " +
                        "div:nth-child(4) > div.day__temperature");
        return IntStream.range(0, countDays).mapToObj(i -> data.get(i).text()).collect(Collectors.toList());
    }

    @SneakyThrows
    protected Document getDocument() {
        return Jsoup.connect("https://pogoda.mail.ru/prognoz/nizhniy_novgorod/14dney/").get();
    }
}
