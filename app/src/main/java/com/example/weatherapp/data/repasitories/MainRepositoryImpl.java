package com.example.weatherapp.data.repasitories;

import androidx.lifecycle.MutableLiveData;

import com.example.weatherapp.common.Recourse;
import com.example.weatherapp.data.local.WeatherDao;
import com.example.weatherapp.data.model.WeatherResponse;
import com.example.weatherapp.data.remote.WeatherApi;
import com.example.weatherapp.domain.repositories.MainRepository;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepositoryImpl implements MainRepository {

    private WeatherApi api;
    private WeatherDao dao;

    @Inject
    public MainRepositoryImpl(WeatherApi api, WeatherDao dao) {
        this.api = api;
        this.dao = dao;
    }



    public MutableLiveData<Recourse<WeatherResponse>> getWeathers(String lat, String lot) {
        MutableLiveData<Recourse<WeatherResponse>> liveData = new MutableLiveData<>();
        liveData.setValue(Recourse.loading());
        api.getWeatherMain(lat,lot, "8acc3e500d215d7e0795345bef53cef1", "metric").enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.setValue(Recourse.success(response.body()));
                    dao.insert(response.body());
                } else {
                    liveData.setValue(Recourse.error(response.message(), null));
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                liveData.setValue(Recourse.error(t.getLocalizedMessage(), null));

            }
        });
        return liveData;
    }

    public List<WeatherResponse> getWeather() {
        return dao.getWeather();
    }

    @Override
    public MutableLiveData<Recourse<WeatherResponse>> getWeathers(String city) {
        return null;
    }
}

