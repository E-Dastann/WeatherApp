package com.example.weatherapp.data.local.converters;

import androidx.room.TypeConverter;

import com.example.weatherapp.data.model.Sys;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class SystemConverter {
    @TypeConverter
    public String fromSysToString(Sys main) {
        Gson gson = new Gson();
        Type type = new TypeToken<Sys>() {
        }.getType();
        return gson.toJson(main, type);
    }

    @TypeConverter
    public Sys fromStringToSys(String fromString) {
        Gson gson = new Gson();
        Type type = new TypeToken<Sys>() {
        }.getType();
        return gson.fromJson(fromString, type);
    }
}
