package com.example.myapplication.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.presenter.SettingsHelper;

public class SettingsActivity extends Activity {
    private SettingsHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        helper = new SettingsHelper(this);
        ((TextView)findViewById(R.id.cityName_data)).setText(helper.getCity());

    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void saveCity(View view){
        try {
            helper.findCityAndStore(((TextView)findViewById(R.id.cityName_data)).getText().toString());
        }catch (Exception e){
            Toast toast = Toast.makeText(this, "Неправильное название города!", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        ((TextView)findViewById(R.id.cityName_data)).setText(helper.getCity());
        Toast toast = Toast.makeText(this, "Город определен!", Toast.LENGTH_SHORT);
        toast.show();
    }
}
