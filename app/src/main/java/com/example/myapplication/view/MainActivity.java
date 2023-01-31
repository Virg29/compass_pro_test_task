package com.example.myapplication.view;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.WeatherStatus;
import com.example.myapplication.presenter.WeatherScraper;

public class MainActivity extends Activity {
    private WeatherScraper scraper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scraper = new WeatherScraper(this);

        scraper.updateWeather();

    }

    @Override
    public void onBackPressed() {
        finish();
        System.exit(0);
    }

    public void goToSettings(View view) {
        Intent intent = new Intent(this,SettingsActivity.class);
        startActivity(intent);
//        RequestHelper.getRequest("http://api.openweathermap.org/geo/1.0/direct?q=%D0%92%D0%BE%D0%BB%D0%B3%D0%BE%D0%B3%D1%80%D0%B0%D0%B4&limit=1&appid=0ceae09ef0855b0d4c26511d8e6add8a");
    }

    public void updateWeatherData(float temp, float humidity, float pressure, float wind, WeatherStatus status){
        ((TextView)findViewById(R.id.temperature_data)).setText(String.valueOf(temp));
        ((TextView)findViewById(R.id.humidity_data)).setText(String.valueOf(humidity));
        ((TextView)findViewById(R.id.airPressure_data)).setText(String.valueOf(pressure));
        ((TextView)findViewById(R.id.windSpeed_data)).setText(String.valueOf(wind));

        ImageView image = findViewById(R.id.weatherStatus_data);
        image.setImageResource(status.getId());
    }

}