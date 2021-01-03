import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Scanner;

public class Day
{
    private String dayOfWeek;
    private String dayOfMonth;
    private String eventOfDay;
    private String temperatureDay;
    private String temperatureNight;

    public Day(Element dayOfWeek,Element dayOfMonth, Element eventOfDay, String attrEvent, Element temperatureDay, Element temperatureNight){
        this.dayOfWeek = getDay(dayOfWeek.text());
        this.dayOfMonth = getNumber(dayOfMonth.text());
        this.eventOfDay = eventOfDay.attr(attrEvent);
        this.temperatureDay = temperatureDay.text();
        this.temperatureNight = temperatureNight.text();
    }



    public void PrintInfoAboutDay()
    {
        System.out.println("\t  "+dayOfMonth + "\t\t    " + dayOfWeek + "\t\t  " + eventOfDay + "  " + temperatureDay + "  " + temperatureNight);
//        System.out.println(dayOfWeek);
//        System.out.println(eventOfDay);
//        System.out.println("Температура днем = " + temperatureDay);
//        System.out.println("Температура ночью = " + temperatureNight);
//        //System.out.println("Среднаяя температура = " + (temperatureMax + temperatureMin) / 2);
    }

    private String getNumber(String data)
    {
        int num = 0;
        try (Scanner s = new Scanner(data)) {
            while (s.hasNextInt()) {
                num = s.nextInt();
            }
        }
        return String.valueOf(num);
    }

    private String getDay(String data)
    {
        String day = "";
        switch (data)
        {
            case "Понедельник":
                day = "Пн";
                break;
            case "Вторник":
                day =  "Вт";
                break;
            case "Среда":
                day = "Ср";
                break;
            case "Четверг":
                day = "Чт";
                break;
            case "Пятница":
                day = "Пт";
                break;
            case "Суббота":
                day = "Сб";
                break;
            case "Воскресенье":
                day = "Вс";
                break;
            default:
                day = data;
                break;
        }
        return  day;
    }

    public String getDayOfWeek()
    {
        return this.dayOfWeek;
    }

    public String getDayOfMonth()
    {
        return this.dayOfMonth;
    }

    public String getEventOfDay()
    {
        return this.eventOfDay;
    }

    public String getTemperatureDay()
    {
        return this.temperatureDay;
    }

    public String getTemperatureNight()
    {
        return this.temperatureNight;
    }
}
