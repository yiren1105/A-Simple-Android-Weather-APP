package com.example.yiren.weather;

/**
 * Created by yiren on 2018-03-08.
 */

public class WeatherData {

    private String city;
    private String weather;
    private String temperature;
    private String description;
    private String max;
    private String min;
    private String humidity;
    private String clouds;

    //default constructor
    public WeatherData() {
    }

    public WeatherData(String city, String weather, String temperature, String description, String max, String min, String humidity, String clouds) {
        this.city = city;
        this.weather = weather;
        this.temperature = temperature;
        this.description = description;
        this.max = max;
        this.min = min;
        this.humidity = humidity;
        this.clouds = clouds;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getClouds() {
        return clouds;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }
}
