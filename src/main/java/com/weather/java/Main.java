package com.weather.java;

import com.weather.java.operations.Operations;
import com.weather.java.operations.GetWeather;
import dnl.utils.text.table.TextTable;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
                Document pageRuMeteo = GetWeather.getPage("https://ru-meteo.ru/nizhniy-novgorod");
                Document pageMailRu  = GetWeather.getPage("https://pogoda.mail.ru/prognoz/nizhniy_novgorod/14dney/");

                ArrayList<Day> predictionGismeteo = GetWeather.getPrediction(pageGismeteo, countDays,
                        "div.widget__body", "div.w_date__day", "span.w_date__date",
                        "span.tooltip", "data-text", "div.maxt > span.unit.unit_temperature_c",
                        "div.mint > span.unit.unit_temperature_c");
                ArrayList<Day> predictionWorldWeather = GetWeather.getPrediction(pageWorldWeather, countDays,
                        "div#content-left", "div.day-week", "div.numbers-month",
                        "span.icon-weather", "title", "div.day-temperature",
                        "div.night-temperature");
                ArrayList<Day> predictionRuMeteo = GetWeather.getPrediction(pageRuMeteo, countDays, "table#forecast", "td > div.dt > big",
                        "div.dt:matchText", "div.wtr > img", "title", "div.wtr > big", "div.nprt > span");
                ArrayList<Day> predictionMailRu = GetWeather.getPrediction(pageMailRu, countDays,
                        "body > div.g-layout.layout.js-module > div.sticky-springs.js-springs__group.js-module",
                        "div.heading.heading_minor.heading_line > span.heading__inner_light", "div.heading.heading_minor.heading_line",
                        "div > div.cols__column__item.cols__column__item_2-1.cols__column__item_2-1_ie8 > div:nth-child(3) > div.weather-icon[title]",
                        "title", "div > div.cols__column__item.cols__column__item_2-1.cols__column__item_2-1_ie8 > div:nth-child(3) > div.day__temperature",
                        "div > div.cols__column__item.cols__column__item_2-1.cols__column__item_2-1_ie8 > div:nth-child(4) > div.day__temperature"
                        );



                ArrayList<Object> dataWebsites = new ArrayList<>();

                //ArrayList<Day> predictionMailRu = new ArrayList<>();
                //get(pageMailRu, predictionMailRu);

                String[][] dataMailRu = getData(predictionMailRu, "MailRu");
                String[][] dataGismeteo = getData(predictionGismeteo, "Gismeteo");
                String[][] dataWorldWeather = getData(predictionWorldWeather, "WorldWeather");
                String[][] dataRuMeteo = getData(predictionRuMeteo, "RuMeteo");

                dataWebsites.add(dataMailRu);
                dataWebsites.add(dataGismeteo);
                dataWebsites.add(dataWorldWeather);
                dataWebsites.add(dataRuMeteo);

                String[] columnNames = {"Сайт", "Число", "День недели", "Явление", "Температура днем", "Температура ночью"};
                TextTable table = new TextTable(columnNames, combiningData(dataWebsites, countDays));
                table.printTable();
            }
        }
    }

    public static void get(Document page,ArrayList<Day> prediction)
    {
        Element tableWeather =  page.select("body > div.g-layout.layout.js-module > div.sticky-springs.js-springs__group.js-module").first();
        Elements dayOfWeek = tableWeather.select("div.heading.heading_minor.heading_line > span.heading__inner_light");
        Elements dayOfMonth       = tableWeather.select("div.heading.heading_minor.heading_line" );
        Elements eventOfDay       = tableWeather.select("div > div.cols__column__item.cols__column__item_2-1.cols__column__item_2-1_ie8 > div:nth-child(3) > div.weather-icon[title]");
        Elements dayTemperature   = tableWeather.select("div > div.cols__column__item.cols__column__item_2-1.cols__column__item_2-1_ie8 > div:nth-child(3) > div.day__temperature");
        Elements nightTemperature = tableWeather.select("div > div.cols__column__item > div:nth-child(4) > div.day__temperature");

        for (int i = 0; i < 5; i++)
        {
            System.out.println(dayOfMonth.get(i).text());
        }

        System.out.println(dayOfWeek.text());
        System.out.println(dayOfMonth.text());
        System.out.println(eventOfDay.attr("title"));
        System.out.println(dayTemperature.text());
        System.out.println(nightTemperature.text());

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
