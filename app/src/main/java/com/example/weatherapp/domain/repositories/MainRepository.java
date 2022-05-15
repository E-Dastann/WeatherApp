package com.example.weatherapp.domain.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.weatherapp.common.Recourse;
import com.example.weatherapp.data.model.WeatherResponse;

public interface MainRepository {
    MutableLiveData<Recourse<WeatherResponse>> getWeathers(String city);


}
