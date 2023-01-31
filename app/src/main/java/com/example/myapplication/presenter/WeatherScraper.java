package com.example.myapplication.presenter;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;

import com.example.myapplication.R;
import com.example.myapplication.model.RequestHelper;
import com.example.myapplication.model.WeatherStatus;
import com.example.myapplication.view.MainActivity;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class WeatherScraper {
    private static Map<String, WeatherStatus> weatherStatusMap;
    static {
        weatherStatusMap = new HashMap<>();
        weatherStatusMap.put("Thunderstorm",WeatherStatus.CLOUD_RAIN);
        weatherStatusMap.put("Clouds",WeatherStatus.CLOUD);
        weatherStatusMap.put("Clear",WeatherStatus.SUNNY);
        weatherStatusMap.put("Snow",WeatherStatus.CLOUD_RAIN);
        weatherStatusMap.put("Rain",WeatherStatus.CLOUD_RAIN);
        weatherStatusMap.put("Drizzle",WeatherStatus.CLOUDLY_RAIN);
        weatherStatusMap.put("another",WeatherStatus.CLOUDLY_WITH_SUN);
    }
    private final MainActivity mainActivity;

    public WeatherScraper(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        updateWeather();
    }

    public boolean isCityStored(){
        SharedPreferences prefs = mainActivity.getSharedPreferences("storedCity", MODE_PRIVATE);
        String name = prefs.getString("name", null);
        if(name != null) return true;
        return false;
    }

    public void updateWeather(){
        if(!isCityStored()){
            SharedPreferences.Editor editor = mainActivity.getSharedPreferences("storedCity", MODE_PRIVATE).edit();
            editor.putString("name", mainActivity.getResources().getString(R.string.default_city));
            editor.putFloat("latitude", Float.parseFloat(mainActivity.getResources().getString(R.string.default_latitude)));
            editor.putFloat("longitude", Float.parseFloat(mainActivity.getResources().getString(R.string.default_longitude)));
            editor.apply();
        }

        SharedPreferences prefs = mainActivity.getSharedPreferences("storedCity", MODE_PRIVATE);
        String name = prefs.getString("name", null);
        float latitude = prefs.getFloat("latitude",0);
        float longitude = prefs.getFloat("longitude",0);

        JsonElement element = RequestHelper.getRequest(String.format("https://api.openweathermap.org/data/2.5/weather?units=metric&lat=%f&lon=%f&appid=%s",
                latitude,
                longitude,
                mainActivity.getResources().getString(R.string.owm_api_key)));

        JsonObject object = element.getAsJsonObject();
        String status = object.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();

        float temp = object.getAsJsonObject("main").get("temp").getAsFloat();
        float pressure = object.getAsJsonObject("main").get("pressure").getAsFloat();
        float wind = object.getAsJsonObject("wind").get("speed").getAsFloat();
        float humidity = object.getAsJsonObject("main").get("humidity").getAsFloat();
        WeatherStatus weatherStatus = weatherStatusMap.get(status);

        if(weatherStatus==null) weatherStatus = weatherStatusMap.get("another");

        mainActivity.updateWeatherData(temp,humidity,pressure,wind,weatherStatus);
    }
}
