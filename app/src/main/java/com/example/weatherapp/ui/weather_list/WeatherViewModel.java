package com.example.weatherapp.ui.weather_list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherapp.common.Recourse;
import com.example.weatherapp.data.model.WeatherResponse;
import com.example.weatherapp.data.repasitories.MainRepositoryImpl;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class WeatherViewModel extends ViewModel {
    public LiveData<Recourse<WeatherResponse>> liveData;
    private MainRepositoryImpl repository;

    @Inject
    public WeatherViewModel(MainRepositoryImpl repository) {
        this.repository = repository;
    }

    public WeatherViewModel() {

    }

    public void getWeathers(String latitude,String longitude) {
        liveData = repository.getWeathers(latitude,longitude);
    }

}
