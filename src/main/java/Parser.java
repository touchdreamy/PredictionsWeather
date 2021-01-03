import dnl.utils.text.table.TextTable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

public class Parser
{
    public static Document getPage(String url) throws IOException
    {
        Document page = Jsoup.parse(new URL(url), 3000);
        return page;
    }

    public static void main(String[] args) throws IOException
    {
        Document pageGismeteo     = getPage("https://www.gismeteo.ru/weather-nizhny-novgorod-4355/10-days/");
        Document pageWorldWeather = getPage("https://world-weather.ru/pogoda/russia/nizhny_novgorod/");

        Day[] predictionGismeteo     = new Day[3];
        Day[] predictionWorldWeather = new Day[3];

        getPredictionGismeteo(pageGismeteo, predictionGismeteo);
        getPredictionWorldWeather(pageWorldWeather, predictionWorldWeather);

        String[][] dataGismeteo     = getData(predictionGismeteo, "Gismeteo");
        String[][] dataWorldWeather = getData(predictionWorldWeather, "WorldWeather");

        String[] columnNames = {"Сайт","Число", "День недели", "Явление", "Температура днем", "Температура ночью"};
        TextTable table = new TextTable(columnNames,combiningData(dataGismeteo, dataWorldWeather));
        table.printTable();
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
        String[][] dataPrediction = new String[3][6];
        String[][] data = new String[3][3];

        // добавили все данные из гисметео
        for (int i=0; i<3; i++)
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

    public static String[][] combiningData(String[][] dataA, String[][] dataB)
    {
        String[][] data = new String[6][6];
        int k = 0;
        for (int i=0; i<6; i++)
        {
            data[i++] = dataA[k];
            data[i]   = dataB[k++];
        }
        return data;
    }
}
