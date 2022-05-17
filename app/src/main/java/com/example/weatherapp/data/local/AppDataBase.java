package com.example.weatherapp.data.local;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.weatherapp.data.local.converters.CloudConverter;
import com.example.weatherapp.data.local.converters.CoordConverter;
import com.example.weatherapp.data.local.converters.InfoTypeConverter;
import com.example.weatherapp.data.local.converters.ListConverter;
import com.example.weatherapp.data.local.converters.MainConverter;
import com.example.weatherapp.data.local.converters.RainConverter;
import com.example.weatherapp.data.local.converters.SystemConverter;
import com.example.weatherapp.data.local.converters.WindConverter;
import com.example.weatherapp.data.model.WeatherResponse;

@Database(entities = {WeatherResponse.class}, version = 1)
@TypeConverters({InfoTypeConverter.class, CloudConverter.class, CoordConverter.class,
        MainConverter.class, SystemConverter.class, WindConverter.class, ListConverter.class,RainConverter.class})
public abstract class AppDataBase extends RoomDatabase {
    public abstract WeatherDao weatherDao();
}
