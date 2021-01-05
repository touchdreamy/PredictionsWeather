package com.weather.java.operations;

import java.util.Scanner;

public class Operations
{
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
}
