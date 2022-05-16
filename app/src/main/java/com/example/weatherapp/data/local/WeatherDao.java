package com.example.weatherapp.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.weatherapp.data.model.WeatherResponse;

import java.util.List;

@Dao
public interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WeatherResponse weather);

    @Query("SELECT * FROM  weatherresponse")
    List<WeatherResponse> getWeather();
}
