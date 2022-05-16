package com.example.weatherapp.data.remote;

import com.example.weatherapp.data.model.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {
    @GET("data/2.5/weather")
    Call<WeatherResponse> getWeatherMain(@Query("lat") String lat, @Query("lon") String lon, @Query("appid") String apiKey, @Query("units") String units);


}
