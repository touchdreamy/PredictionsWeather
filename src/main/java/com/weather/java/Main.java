package com.weather.java;

import com.weather.java.operations.*;
import com.weather.java.operations.predictions.*;
import dnl.utils.text.table.TextTable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("Узнать погоду на сегодня. [1]");
        System.out.println("Узнать погоду на ближайшие дни. [2]");
        System.out.print("Введите пункт меню: ");
        int point = Utilities.getPoint(1,2);

        switch (point) {
            case 1 -> {
                List<Day> prediction;
                prediction = new GetOpenWeatherPrediction().getPrediction("Нижний Новгород");
                System.out.println();
                Operations.printWeatherDay(prediction.get(0));
            }
            case 2 -> {
                System.out.print("\nПрогноз на сколько дней хотите увидеть (от 1 - 7 дней) : ");

                int countDays = Utilities.getPoint(1, 7);

                List<Object> dataWebsites = new ArrayList<>();

                dataWebsites.add(
                        Utilities.getData(
                        new GetMailRuWeatherPrediction().getPrediction(countDays), "MailRu"));

                dataWebsites.add(
                        Utilities.getData(
                        new GetGismeteoWeatherPrediction().getPrediction(countDays), "Gismeteo"));

                dataWebsites.add(
                        Utilities.getData(
                        new GetWorldWeatherPrediction().getPrediction(countDays), "WorldWeather"));

                dataWebsites.add(
                        Utilities.getData(
                        new GetRuMeteoPrediction().getPrediction(countDays), "RuMeteo"));

                String[] columnNames = {
                        "Сайт",
                        "Число",
                        "День недели",
                        "Явление",
                        "Температура днем",
                        "Температура ночью"};

                TextTable table = new TextTable(columnNames, Utilities.combiningData(dataWebsites, countDays));
                table.printTable();
            }
        }
    }
}
