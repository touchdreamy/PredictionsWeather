package com.weather.java.operations;

import com.weather.java.Day;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class GetWeather
{
    public static Document getPage(String url) throws IOException
    {
        return Jsoup.connect(url).get();
    }

    public static ArrayList<Day> getPrediction(Document page, int countDays, String selectTable, String selectDayOfWeek,
                                               String selectDayOfMonth, String selectEvent, String attrEvent,
                                               String selectTempDay, String selectTempNight) {
        ArrayList<Day> prediction = new ArrayList<>();

        Element tableWeather = page.select(selectTable).first();
        Elements dayOfWeek = tableWeather.select(selectDayOfWeek);
        Elements dayOfMonth = tableWeather.select(selectDayOfMonth);
        Elements eventOfDay = tableWeather.select(selectEvent);
        Elements dayTemperature = tableWeather.select(selectTempDay);
        Elements nightTemperature = tableWeather.select(selectTempNight);

        for (int i = 0; i < countDays; i++) {
            Day day = new Day(dayOfWeek.get(i), dayOfMonth.get(i), eventOfDay.get(i), attrEvent, dayTemperature.get(i),
                    nightTemperature.get(i));
            prediction.add(i, day);

        }

        return prediction;
    }

    public static Day getWeatherNow(String nameCity) throws IOException {
        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + nameCity +
                                    "&units=metric&lang=ru&appid=e4e38e27080c4e8fe159f243e07f80d9");

        Scanner sc = new Scanner((InputStream) url.getContent());
        String result = "";

        while (sc.hasNext()) {
            result += sc.nextLine();
        }

        JSONObject object = new JSONObject(result);

        JSONObject main = object.getJSONObject("main");

        JSONArray weatherArray = object.getJSONArray("weather");
        String event = "";
        for (int i = 0; i < weatherArray.length(); i++) {
            JSONObject obj = weatherArray.getJSONObject(i);
            event = (obj.getString("description"));
        }

        return new Day(object.getString("name"), event, object.getJSONObject("clouds").getDouble("all"),
                main.getDouble("humidity"), main.getDouble("temp"), main.getDouble("feels_like"),
                main.getDouble("temp_max"), main.getDouble("temp_min"));
    }

}
