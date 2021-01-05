import dnl.utils.text.table.TextTable;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class Parser
{
    public static Document getPage(String url) throws IOException
    {
        Document page = Jsoup.parse(new URL(url), 7000);
        return page;
    }

    public static currentDay getWeather(String nameCity) throws IOException {
        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + nameCity + "&units=metric&lang=ru&appid=e4e38e27080c4e8fe159f243e07f80d9");

        Scanner sc = new Scanner((InputStream) url.getContent());
        String result = "";

        while (sc.hasNext()) {
            result += sc.nextLine();
        }

        currentDay Day = new currentDay();

        JSONObject object = new JSONObject(result);
        Day.setName(object.getString("name"));

        JSONObject main = object.getJSONObject("main");
        Day.setFeels_like(main.getDouble("feels_like"));
        Day.setHumidity(main.getDouble("humidity"));
        Day.setTemp(main.getDouble("temp"));
        Day.setTemp_min(main.getDouble("temp_min"));
        Day.setTemp_max(main.getDouble("temp_max"));

        JSONArray weatherArray = object.getJSONArray("weather");
        for (int i = 0; i < weatherArray.length(); i++) {
            JSONObject obj = weatherArray.getJSONObject(i);
            Day.setDescription(obj.getString("description"));
        }

        Day.setClouds(object.getJSONObject("clouds").getDouble("all"));

        return Day;
    }

    public static int getPoint(int startIndex, int endIndex)
    {
        Scanner sc = new Scanner(System.in);
        int num;

        do {
            System.out.print("Введите число: ");

            while (!sc.hasNextInt())
            {
                System.out.println("Ошибка! Это не число! Повторите попытку.");
                System.out.print("Введите число: ");
                sc.next(); // this is important!
            }

            num = sc.nextInt();

            if (num < startIndex || num > endIndex)
            {
                System.out.println("Ошибка! Выход за границы ввода. Повторите попытку.");
            }

        } while (num < startIndex || num > endIndex);

        return num;
    }

    public static void main(String[] args) throws IOException
    {
        Scanner sc = new Scanner(System.in);

        System.out.println("Узнать погоду на сегодня. [1]");
        System.out.println("Узнать погоду на ближайшие дни. [2]");
        System.out.print("Введите пункт меню: ");
        int point = getPoint(1,2);

        switch (point){
            case 1:{
                //  погода на сегодня
                currentDay day = getWeather("Нижний Новгород");
                System.out.println("");
                day.printInfo();
                break;
            }
            case 2:{
                System.out.print("\nПрогноз на сколько дней хотите увидеть (от 1 - 7 дней) : ");
                int countDays = getPoint(1,7);

                Document pageGismeteo     = getPage("https://www.gismeteo.ru/weather-nizhny-novgorod-4355/10-days/");
                Document pageWorldWeather = getPage("https://world-weather.ru/pogoda/russia/nizhny_novgorod/");

                Day[] predictionGismeteo     = new Day[countDays];
                Day[] predictionWorldWeather = new Day[countDays];

                getPredictionGismeteo(pageGismeteo, predictionGismeteo);
                getPredictionWorldWeather(pageWorldWeather, predictionWorldWeather);

                String[][] dataGismeteo     = getData(predictionGismeteo, "Gismeteo");
                String[][] dataWorldWeather = getData(predictionWorldWeather, "WorldWeather");

                String[] columnNames = {"Сайт","Число", "День недели", "Явление", "Температура днем", "Температура ночью"};
                TextTable table = new TextTable(columnNames,combiningData(dataGismeteo, dataWorldWeather, countDays, 2));
                table.printTable();
                break;
            }
        }
    }

    public static void getPredictionGismeteo(Document pageGismeteo,Day[] predictionGismeteo)
    {
        Element tableWeather      = pageGismeteo.select("div.widget__body").first();
        Elements dayOfWeek        = tableWeather.select("div.w_date__day");
        Elements dayOfMonth       = tableWeather.select("span.w_date__date" );
        Elements eventOfDay       = tableWeather.select("span.tooltip");
        Elements dayTemperature   = tableWeather.select("div.maxt > span.unit.unit_temperature_c");
        Elements nightTemperature = tableWeather.select("div.mint > span.unit.unit_temperature_c");

        for (int i=0; i < predictionGismeteo.length; i++)
        {
            predictionGismeteo[i] = new Day(dayOfWeek.get(i), dayOfMonth.get(i), eventOfDay.get(i), "data-text", dayTemperature.get(i), nightTemperature.get(i));
        }
    }

    public static void getPredictionWorldWeather(Document pageWorldWeather,Day[] predictionWorldWeather)
    {
        Element tableWorldWeather = pageWorldWeather.select("div#content-left").first();
        Elements dayOfWeek = tableWorldWeather.select("div.day-week");
        Elements dayOfMonth = tableWorldWeather.select("div.numbers-month");
        Elements eventOfDay = tableWorldWeather.select("span.icon-weather");
        Elements dayTemperature = tableWorldWeather.select("div.day-temperature");
        Elements nightTemperature = tableWorldWeather.select("div.night-temperature");

        for (int i=0; i < predictionWorldWeather.length; i++)
        {
            predictionWorldWeather[i] = new Day(dayOfWeek.get(i), dayOfMonth.get(i), eventOfDay.get(i), "title", dayTemperature.get(i), nightTemperature.get(i));
        }
    }

    public static String[][] getData(Day[] prediction, String website)
    {
        String[][] dataPrediction = new String[prediction.length][6];  // 6 - кол-во данных
        String[][] data = new String[prediction.length][prediction.length];

        // добавили все данные из гисметео
        for (int i=0; i<prediction.length; i++)
        {
            int k = 0;
            dataPrediction[i][k++] = website;
            dataPrediction[i][k++] = prediction[i].getDayOfMonth();
            dataPrediction[i][k++] = prediction[i].getDayOfWeek();
            dataPrediction[i][k++] = prediction[i].getEventOfDay();
            dataPrediction[i][k++] = prediction[i].getTemperatureDay();
            dataPrediction[i][k++] = prediction[i].getTemperatureNight();
            data[i] = dataPrediction[i];
        }
        return data;
    }

    public static String[][] combiningData(String[][] dataA, String[][] dataB, int countDays, int countWebsites)
    {
        String[][] data = new String[countDays * countWebsites][6];  // *2 так как два сайта
        int k = 0;
        for (int i=0; i<countDays * countWebsites; i++)
        {
            data[i++] = dataA[k];
            data[i]   = dataB[k++];
        }
        return data;
    }
}
