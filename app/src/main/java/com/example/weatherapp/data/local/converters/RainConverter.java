package com.example.weatherapp.data.local.converters;

import androidx.room.TypeConverter;

import com.example.weatherapp.data.model.Rain;
import com.example.weatherapp.data.model.WeatherMain;
import com.example.weatherapp.data.model.WeatherResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class RainConverter {
    @TypeConverter
    public String fromMainToString(Rain main) {
        Gson gson = new Gson();
        Type type = new TypeToken<Rain>() {
        }.getType();
        return gson.toJson(main, type);
    }

    @TypeConverter
    public Rain fromStringToMain(String fromString) {
        Gson gson = new Gson();
        Type type = new TypeToken<Rain>() {
        }.getType();
        return gson.fromJson(fromString, type);
    }
}
