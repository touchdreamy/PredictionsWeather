package com.weather.java.operations;

import com.weather.java.Day;

import java.util.List;
import java.util.Scanner;

public final class Utilities
{
    private Utilities() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
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

    public static String setCelsiusSign(String data)
    {
        StringBuilder str = new StringBuilder(data);
        if (str.charAt(str.length() - 1) != '°')
        {
            return str.append('°').toString();
        }
        return str.toString();
    }

    public static String getDay(String data) {
        return switch (data) {
            case "Пн", "понедельник", "пн" -> "Понедельник";
            case "Вт", "вторник", "вт" -> "Вторник";
            case "Ср", "среда", "ср" -> "Среда";
            case "Чт", "четверг", "чт" -> "Четверг";
            case "Пт", "пятница", "пт" -> "Пятница";
            case "сб", "суббота", "Сб" -> "Суббота";
            case "Вс", "воскресенье", "вс" -> "Воскресенье";
            default -> data;
        };
    }

    public static String getNumber(String data) {
        StringBuilder str = new StringBuilder(data);
        StringBuilder strOut = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                strOut.append(str.charAt(i));
            }
        }
        return strOut.toString();
    }

    // создает массив значений для таблицы
    public static String[][] getData(List<Day> prediction, String website)
    {
        String[][] dataPrediction = new String[prediction.size()][6];  // 6 - кол-во данных
        String[][] data = new String[prediction.size()][prediction.size()];

        for (int i = 0; i < prediction.size(); i++) {
            int k = 0;
            dataPrediction[i][k++] = website;
            dataPrediction[i][k++] = prediction.get(i).getDayOfMonth();
            dataPrediction[i][k++] = prediction.get(i).getDayOfWeek();
            dataPrediction[i][k++] = prediction.get(i).getEventOfDay();
            dataPrediction[i][k++] = prediction.get(i).getTemperatureDay();
            dataPrediction[i][k++] = prediction.get(i).getTemperatureNight();
            data[i] = dataPrediction[i];
        }
        return data;
    }

    // комбинирует все значения данных в один для таблицы
    public static String[][] combiningData(List<Object> dataWebsites, int countDays) {
        // +1 для создание пробелов между днями; -1 в самом конце не вставлять пустую строку
        String[][] data = new String[countDays * (dataWebsites.size() + 1) - 1][6];
        String[] tmp = {"", "", "", "", "", ""};

        int k = 0;
        for (int i = 0; i < countDays * (dataWebsites.size() + 1) - 1; i++) {
            for (Object dataWebsite : dataWebsites) {
                String[][] dataWeb = (String[][]) dataWebsite;
                data[i++] = dataWeb[k];
            }
            k++;

            if (i == countDays * (dataWebsites.size() + 1) - 1) break;

            data[i] = tmp;
        }
        return data;
    }
}
