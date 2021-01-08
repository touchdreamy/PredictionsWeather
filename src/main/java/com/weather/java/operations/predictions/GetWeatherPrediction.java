package com.weather.java.operations.predictions;

import com.weather.java.Day;
import com.weather.java.operations.Utilities;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public abstract class GetWeatherPrediction {

    protected int countDays;
    protected Document page = getDocument();

    public GetWeatherPrediction(int countDays) {
        this.countDays = countDays;
    }

    public List<Day> getPrediction() {
        List<Day> prediction = new ArrayList<>();

        for (int i = 0; i < countDays; i++) {
            prediction.add(i, new Day()
                    .setDayOfWeek(Utilities.getDay(getDayOfWeek().get(i)))
                    .setDayOfMonth(Utilities.getNumber(getDayOfMonth().get(i)))
                    .setEventOfDay(getEventOfDay().get(i))
                    .setTemperatureDay(Utilities.setCelsiusSign(getTemperatureDay().get(i)))
                    .setTemperatureNight(Utilities.setCelsiusSign(getTemperatureNight().get(i))));
        }

        return prediction;
    }

    protected abstract List<String> getDayOfWeek();
    protected abstract List<String> getDayOfMonth();
    protected abstract List<String> getEventOfDay();
    protected abstract List<String> getTemperatureDay();
    protected abstract List<String> getTemperatureNight();
    protected abstract Document getDocument();

}
