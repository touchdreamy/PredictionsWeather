package com.weather.java.operations.predictions;

import com.weather.java.Day;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GetOpenWeatherPrediction{

    @SneakyThrows
    public List<Day> getPrediction(String nameCity)
    {
        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + nameCity +
                "&units=metric&lang=ru&appid=e4e38e27080c4e8fe159f243e07f80d9");

        Scanner sc = new Scanner((InputStream) url.getContent());
        StringBuilder result = new StringBuilder();

        while (sc.hasNext()) {
            result.append(sc.nextLine());
        }

        JSONObject object = new JSONObject(result.toString());
        JSONObject main = object.getJSONObject("main");
        JSONArray weatherArray = object.getJSONArray("weather");
        String event = "";

        for (int i = 0; i < weatherArray.length(); i++) {
            JSONObject obj = weatherArray.getJSONObject(i);
            event = (obj.getString("description"));
        }

        List<Day> prediction = new ArrayList<>();

        prediction.add(new Day()
                .setNameCity(object.getString("name"))
                .setEventOfDay(event)
                .setClouds(object.getJSONObject("clouds").getDouble("all"))
                .setHumidity(main.getDouble("humidity"))
                .setTemperature(main.getDouble("temp"))
                .setFeelsLike(main.getDouble("feels_like"))
                .setTemperatureMin(main.getDouble("temp_min"))
                .setTemperatureMax(main.getDouble("temp_max")));

        return prediction;
    }

}
