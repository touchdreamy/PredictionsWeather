public class currentDay
{
    private String name;
    private String description; // event of day
    private Double temp;
    private Double temp_min;
    private Double temp_max;
    private Double feels_like; // как ощущается температура
    private Double humidity; // влажность
    private Double clouds;

    public void printInfo()
    {
        System.out.println("Погода на сегодня в городе: " + name);
        System.out.println(" - " + description);
        System.out.println(" - Облачность  =" + clouds + "%");
        System.out.println(" - Влажность   =" + humidity + "°");
        System.out.println(" - Температура =" + temp + "°");
        System.out.println(" - Ощущается   =" + feels_like + "°");
        System.out.println(" - Максимум    =" + temp_max + "°");
        System.out.println(" - Минимум     =" + temp_min + "°");
    }

    public Double getClouds() {
        return clouds;
    }

    public void setClouds(Double clouds) {
        this.clouds = clouds;
    }

    public currentDay(){this.name = "";}

    public Double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(Double temp_min) {
        this.temp_min = temp_min;
    }

    public Double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(Double temp_max) {
        this.temp_max = temp_max;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getFeels_like(double feel_like) {
        return feels_like;
    }

    public void setFeels_like(Double feels_like) {
        this.feels_like = feels_like;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }
}

