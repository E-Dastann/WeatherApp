package com.example.weatherapp.ui.weather_list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.weatherapp.R;
import com.example.weatherapp.base.BaseFragment;
import com.example.weatherapp.common.Recourse;
import com.example.weatherapp.data.local.WeatherDao;
import com.example.weatherapp.data.model.Sys;
import com.example.weatherapp.data.model.Weather;
import com.example.weatherapp.data.model.WeatherMain;
import com.example.weatherapp.data.model.WeatherResponse;
import com.example.weatherapp.data.model.Wind;
import com.example.weatherapp.databinding.FragmentWeatherBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WeatherFragments extends BaseFragment<FragmentWeatherBinding> {
    private WeatherViewModel viewModel;
    private WeatherFragmentsArgs args;
    private Sys sys;
    private WeatherMain main;
    private WeatherResponse weatherMain;
    private Wind wind;

    @Inject
    WeatherDao dao;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);

        try {
            args = WeatherFragmentsArgs.fromBundle(getArguments());
        } catch (Exception e) {
            Log.e("Tag", e.getLocalizedMessage());

        }

    }

    @Override
    protected FragmentWeatherBinding bind() {
        return FragmentWeatherBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void setUpView() {
    }

    @Override
    protected void callRequest() {
        viewModel.getWeathers(args.getLatitude(), args.getLongitude());

    }

    @Override
    protected void setUpListeners() {
        binding.btnLocation.setOnClickListener(view -> navController.navigate(R.id.action_weatherFragment_to_searchFragment));
    }

    @Override
    protected void setUpObservers() {
        SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyy HH:mm:ss", Locale.ROOT);
        String s = sdf.format(System.currentTimeMillis());
        viewModel.liveData.observe(getViewLifecycleOwner(), new Observer<Recourse<WeatherResponse>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(Recourse<WeatherResponse> weatherResponseRecourse) {
                switch (weatherResponseRecourse.status) {
                    case LOADING: {
                        binding.ekran.setVisibility(View.INVISIBLE);
                        break;
                    }
                    case SUCCESS: {
                        binding.ekran.setVisibility(View.VISIBLE);
                        binding.btnLocation.setText(weatherResponseRecourse.data.getName());
                        binding.tempC.setText(String.valueOf(weatherResponseRecourse.data.getMain().getTemp()));
                        binding.tex27.setText(String.valueOf(weatherResponseRecourse.data.getMain().getTempMin()));
                        binding.tex35.setText(String.valueOf(weatherResponseRecourse.data.getMain().getTempMax()));
                        binding.sunriseTime.setText(getTime(requireContext(), Long.valueOf(weatherResponseRecourse.data.getSys().getSunrise())));
                        binding.humidity45.setText(String.valueOf(weatherResponseRecourse.data.getMain().getHumidity()));
                        binding.sunsetTime.setText(getTime(requireContext(), Long.valueOf(weatherResponseRecourse.data.getSys().getSunset())));
                        binding.mBar.setText(String.valueOf(weatherResponseRecourse.data.getMain().getPressure()));
                        binding.text23Km.setText(String.valueOf(weatherResponseRecourse.data.getWind().getSpeed()));
                        binding.imageTextCloud.setText(weatherResponseRecourse.data.getWeather().get(0).getMain());
                          binding.simpleData.setText(String.valueOf(s));
                        binding.dayTime.setText(String.valueOf(weatherResponseRecourse.data.getWind()));
                        int daytime = weatherResponseRecourse.data.getSys().getSunset() - weatherResponseRecourse.data.getSys().getSunrise();
                        binding.dayTime.setText(getHours(daytime));
                        break;
                    }
                    case ERROR: {
                        Toast.makeText(requireActivity(), "что то пишитье ", Toast.LENGTH_SHORT).show();
                        wind = dao.getWeather().get(dao.getWeather().size() - 1).getWind();
                        binding.sunsetTime.setText(wind.getSpeed().toString());
                        weatherMain = dao.getWeather().get(dao.getWeather().size() - 1);
                        main = dao.getWeather().get(dao.getWeather().size() - 1).getMain();
                        sys = dao.getWeather().get(dao.getWeather().size() - 1).getSys();
                        setCurrentWeatherRoom(dao.getWeather());
                        binding.imageTextCloud.setText(dao.getWeather().get(dao.getWeather().size() - 1).getMain().toString());
                        binding.image.setVisibility(View.VISIBLE);
                        binding.contentPanel.setVisibility(View.VISIBLE);
                        binding.ekran.setVisibility(View.VISIBLE);
                        break;
                    }
                }
            }


            @SuppressLint("SetTextI18n")
            private void setCurrentWeatherRoom(List<WeatherResponse> weather) {
                binding.simpleData.setText(sdf.format(java.lang.System.currentTimeMillis()));
                binding.btnLocation.setText(weather.get(weather.size() -1).getName());
                //Setting weather status
//        Glide.with(requireContext())
//                .load("https://openweathermap.org/img/wn/" + weatherList.get(0).getIcon() + ".png")
//                .override(100, 100)
//                .into(binding.weatherStatusImg);

                //Setting temperature
                binding.tempC.setText((weather.get(weather.size() - 1).getMain().getTemp()) + "");
                binding.tex27.setText(Math.round(weather.get(weather.size() - 1).getMain().getTempMin() - 273.15) + "°C");
                binding.tex35.setText(Math.round(weather.get(weather.size() - 1).getMain().getTempMax() - 273.15) + "°C");

                //Setting extras
                binding.humidity45.setText(weather.get(weather.size() - 1).getMain().getHumidity() + "%");
                binding.mBar.setText(weather.get(weather.size() - 1).getMain().getPressure() + "mBar");
                binding.text23Km.setText(weather.get(weather.size() - 1).getWind().getSpeed() + "km/h");

                //Setting sunset and sunrise
                binding.sunriseTime.setText(getTime(requireContext(), Long.valueOf(sys.getSunrise())));
                binding.sunsetTime.setText(getTime(requireContext(), Long.valueOf(sys.getSunset())));

                //Setting daytime
                int daytime = sys.getSunset() - sys.getSunrise();
                binding.dayTime.setText(getHours(daytime));


            }
        });

    }

    private String getTime(Context context, Long time) {
        return DateUtils.formatDateTime(context, time * 1000, DateUtils.FORMAT_SHOW_TIME);
    }

    private String getHours(int time) {
        int hours = (int) TimeUnit.SECONDS.toHours(time);
        int minutes = (int) ((int) TimeUnit.SECONDS.toMinutes(time) -
                (TimeUnit.SECONDS.toHours(time) * 60));

        return hours + "h " + minutes + "m";
    }
}
