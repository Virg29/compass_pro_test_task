package com.example.myapplication.presenter;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.myapplication.R;
import com.example.myapplication.model.RequestHelper;
import com.example.myapplication.view.SettingsActivity;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class SettingsHelper {

    private final SettingsActivity activity;

    public SettingsHelper(SettingsActivity activity) {
        this.activity = activity;
    }

    public void findCityAndStore(String name){
        Log.i("Debug", String.format("http://api.openweathermap.org/geo/1.0/direct?q=%s&limit=1&appid=%s", name,activity.getResources().getString(R.string.owm_api_key)));
        JsonElement element = RequestHelper.getRequest(String.format("http://api.openweathermap.org/geo/1.0/direct?q=%s&limit=1&appid=%s", encodeValue(name),activity.getResources().getString(R.string.owm_api_key)));
        JsonObject main = element.getAsJsonArray().get(0).getAsJsonObject();
        String cityName = main.get("name").getAsString();
        float latitude = main.get("lat").getAsFloat();
        float longitude = main.get("lon").getAsFloat();

        SharedPreferences.Editor editor = activity.getSharedPreferences("storedCity", MODE_PRIVATE).edit();
        editor.putString("name", cityName);
        editor.putFloat("latitude", latitude);
        editor.putFloat("longitude", longitude);
        editor.apply();
    }
    private String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
    public String getCity(){
        SharedPreferences prefs = activity.getSharedPreferences("storedCity", MODE_PRIVATE);
        String name = prefs.getString("name", null);
        return name;
    }
}
