package com.example.myapplication.model;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

public class RequestHelper {
    public static JsonElement getRequest(String uri){

        CloseableHttpClient httpClient = HttpClients.createDefault();
        String json;
        try {
//            HttpGet request = new HttpGet("http://api.openweathermap.org/geo/1.0/direct?q=%D0%92%D0%BE%D0%BB%D0%B3%D0%BE%D0%B3%D1%80%D0%B0%D0%B4&limit=1&appid=0ceae09ef0855b0d4c26511d8e6add8a");
            HttpGet request = new HttpGet(uri);
            json = httpClient.execute(request, response -> {
                Log.i("debug",response.getEntity().toString());
                return EntityUtils.toString(response.getEntity());
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if(json==null) return null;
        Log.i("debug",json);
//        if (element instanceof JsonObject) {
//            element.getAsJsonObject();
//        } else if (element instanceof JsonArray) {
//            element.getAsJsonArray();
//        }
        return JsonParser.parseString(json);


    }
}
