package com.weather.java;

import com.weather.java.operations.Operations;
import com.weather.java.operations.GetWeather;
import dnl.utils.text.table.TextTable;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        System.out.println("Узнать погоду на сегодня. [1]");
        System.out.println("Узнать погоду на ближайшие дни. [2]");
        System.out.print("Введите пункт меню: ");
        int point = Operations.getPoint(1,2);

        switch (point) {
            case 1 -> {
                Day day = GetWeather.getWeatherNow("Нижний Новгород");
                System.out.println();
                day.printInfo();
            }
            case 2 -> {
                System.out.print("\nПрогноз на сколько дней хотите увидеть (от 1 - 7 дней) : ");
                int countDays = Operations.getPoint(1, 7);

                Document pageGismeteo = GetWeather.getPage("https://www.gismeteo.ru/weather-nizhny-novgorod-4355/10-days/");
                Document pageWorldWeather = GetWeather.getPage("https://world-weather.ru/pogoda/russia/nizhny_novgorod/");

                ArrayList<Day> predictionGismeteo = GetWeather.getPrediction(pageGismeteo, countDays,
                        "div.widget__body", "div.w_date__day", "span.w_date__date",
                        "span.tooltip", "data-text", "div.maxt > span.unit.unit_temperature_c",
                        "div.mint > span.unit.unit_temperature_c");
                ArrayList<Day> predictionWorldWeather = GetWeather.getPrediction(pageWorldWeather, countDays,
                        "div#content-left", "div.day-week", "div.numbers-month",
                        "span.icon-weather", "title", "div.day-temperature",
                        "div.night-temperature");


                ArrayList<Object> dataWebsites = new ArrayList<>();

                String[][] dataGismeteo = getData(predictionGismeteo, "Gismeteo");
                String[][] dataWorldWeather = getData(predictionWorldWeather, "WorldWeather");

                dataWebsites.add(dataGismeteo);
                dataWebsites.add(dataWorldWeather);

                String[] columnNames = {"Сайт", "Число", "День недели", "Явление", "Температура днем", "Температура ночью"};
                TextTable table = new TextTable(columnNames, combiningData(dataWebsites, countDays));
                table.printTable();
            }
        }
    }

    // создает массив значений для таблицы
    public static String[][] getData(ArrayList<Day> prediction, String website)
    {
        String[][] dataPrediction = new String[prediction.size()][6];  // 6 - кол-во данных
        String[][] data = new String[prediction.size()][prediction.size()];

        for (int i=0; i<prediction.size(); i++)
        {
            int k = 0;
            dataPrediction[i][k++] = website;
            dataPrediction[i][k++] = prediction.get(i).dayOfMonth;
            dataPrediction[i][k++] = prediction.get(i).dayOfWeek;
            dataPrediction[i][k++] = prediction.get(i).eventOfDay;
            dataPrediction[i][k++] = prediction.get(i).temperatureDay;
            dataPrediction[i][k++] = prediction.get(i).temperatureNight;
            data[i] = dataPrediction[i];
        }
        return data;
    }

    // комбинирует все значения данных в один для таблицы
    public static String[][] combiningData(ArrayList<Object> dataWebsites, int countDays) {
        // +1 для создание пробелов между днями; -1 в самом конце не вставлять пустую строку
        String[][] data = new String[countDays * (dataWebsites.size() + 1) - 1][6];
        String[] tmp = {"", "", "", "", "", ""};

        int k = 0;
        for (int i = 0; i < countDays * (dataWebsites.size() + 1) - 1; i++) {
            for (int j = 0; j < dataWebsites.size(); j++) {
                String[][] dataWeb = (String[][]) dataWebsites.get(j);
                data[i++] = dataWeb[k];
            }
            k++;

            if (i == countDays * (dataWebsites.size() + 1) - 1) break;

            data[i] = tmp;
        }
        return data;
    }
}
