package com.example.myapplication.model;

import com.example.myapplication.R;

public enum WeatherStatus {
    SUNNY(R.drawable.sunny),
    CLOUDLY_WITH_SUN(R.drawable.cloudly),
    CLOUD(R.drawable.cloud),
    CLOUDLY_RAIN(R.drawable.cloudly_rain),
    CLOUD_RAIN(R.drawable.cloud_rain);

    public int getId(){
        return this.id;
    }
    private final int id;

    WeatherStatus(int id) {
        this.id = id;
    }
}
