package com.example.weatherapp.data.local.converters;

import androidx.room.TypeConverter;

import com.example.weatherapp.data.model.Coord;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class CoordConverter {
    @TypeConverter
    public String fromCoordToString(Coord main) {
        Gson gson = new Gson();
        Type type = new TypeToken<Coord>() {
        }.getType();
        return gson.toJson(main, type);
    }

    @TypeConverter
    public Coord fromStringToCoord(String fromString) {
        Gson gson = new Gson();
        Type type = new TypeToken<Coord>() {
        }.getType();
        return gson.fromJson(fromString, type);
    }
}
