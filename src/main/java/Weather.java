import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Weather {

    // API e4e38e27080c4e8fe159f243e07f80d9

    public static String getWeather(String nameCity) throws IOException {
        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + nameCity + "&units=metric&appid=e4e38e27080c4e8fe159f243e07f80d9");

        Scanner sc = new Scanner((InputStream) url.getContent());
        String result = "";
        // пока мы можем что то считывать с потока
        while (sc.hasNext())
        {
            result += sc.nextLine() ;
        }
        return result;
    }

}
