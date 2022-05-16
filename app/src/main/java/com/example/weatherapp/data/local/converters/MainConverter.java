package com.example.weatherapp.data.local.converters;

import androidx.room.TypeConverter;

import com.example.weatherapp.data.model.WeatherMain;
import com.example.weatherapp.data.model.WeatherResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class MainConverter {
    @TypeConverter
    public String fromMainToString(WeatherMain main) {
        Gson gson = new Gson();
        Type type = new TypeToken<WeatherMain>() {
        }.getType();
        return gson.toJson(main, type);
    }

    @TypeConverter
    public WeatherMain fromStringToMain(String fromString) {
        Gson gson = new Gson();
        Type type = new TypeToken<WeatherMain>() {
        }.getType();
        return gson.fromJson(fromString, type);
    }
}
