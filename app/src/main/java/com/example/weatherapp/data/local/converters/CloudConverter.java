package com.example.weatherapp.data.local.converters;

import androidx.room.TypeConverter;

import com.example.weatherapp.data.model.Clouds;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class CloudConverter {
    @TypeConverter
    public String fromCloudsToString(Clouds image) {
        Gson gson = new Gson();
        Type type = new TypeToken<Clouds>() {
        }.getType();
        return gson.toJson(image, type);
    }

    @TypeConverter
    public Clouds fromStringToClouds(String fromString) {
        Gson gson = new Gson();
        Type type = new TypeToken<Clouds>() {
        }.getType();
        return gson.fromJson(fromString, type);
    }
}
