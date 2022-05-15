package com.example.weatherapp.ui.weather_list;

import android.content.Context;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import com.example.weatherapp.base.BaseFragment;
import com.example.weatherapp.common.Recourse;
import com.example.weatherapp.data.model.WeatherResponse;
import com.example.weatherapp.databinding.FragmentWeatherBinding;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WeatherFragments extends BaseFragment<FragmentWeatherBinding> {
    private WeatherViewModel viewModel;
    private WeatherFragmentsArgs args;

        @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);
        args = WeatherFragmentsArgs.fromBundle(requireArguments());
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
        viewModel.getWeathers(args.getCity());
        binding.btnLocation.setText(args.getCity());
    }
    @Override
    protected void setUpListeners() {
        binding.btnLocation.setOnClickListener(view ->
                navController.navigate(WeatherFragmentsDirections.actionWeatherFragmentToSearchFragment()));
    }

    @Override
    protected void setUpObservers() {
        viewModel.liveData.observe(getViewLifecycleOwner(), new Observer<Recourse<WeatherResponse>>() {
            @Override
            public void onChanged(Recourse<WeatherResponse> weatherResponseRecourse) {
                switch (weatherResponseRecourse.status) {
                    case LOADING: {
                        binding.ekran.setVisibility(View.INVISIBLE);
                        break;
                    }
                    case SUCCESS: {
                        binding.ekran.setVisibility(View.VISIBLE);
                        binding.tempC.setText(String.valueOf(weatherResponseRecourse.data.getMain().getTemp()));
                        binding.tex27.setText(String.valueOf(weatherResponseRecourse.data.getMain().getTempMin()));
                        binding.tex35.setText(String.valueOf(weatherResponseRecourse.data.getMain().getTempMax()));
                        binding.sunriseTime.setText(getTime(requireContext(), Long.valueOf(weatherResponseRecourse.data.getSys().getSunrise())));
                        binding.humidity45.setText(String.valueOf(weatherResponseRecourse.data.getMain().getHumidity()));
                        binding.sunsetTime.setText(getTime(requireContext(), Long.valueOf(weatherResponseRecourse.data.getSys().getSunset())));
                        binding.mBar.setText(String.valueOf(weatherResponseRecourse.data.getMain().getPressure()));
                        binding.text23Km.setText(String.valueOf(weatherResponseRecourse.data.getWind().getSpeed()));
                        binding.imageTextCloud.setText(weatherResponseRecourse.data.getWeather().get(0).getMain());
                        Glide.with(requireContext())
                                .load("https://openweathermap.org/img/wn/" + weatherResponseRecourse.data.getWeather().get(0).getIcon() + ".png")
                                .override(100, 100)
                                .into(binding.imageCloud);
                        binding.simpleData.setText(String.valueOf(s));
                        binding.dayTime.setText(String.valueOf(weatherResponseRecourse.data.getWind()));
                        int daytime = weatherResponseRecourse.data.getSys().getSunset() - weatherResponseRecourse.data.getSys().getSunrise();
                        binding.dayTime.setText(getHours(daytime));
                        break;
                    }
                    case ERROR: {
                        Toast.makeText(requireActivity(), "что то пишитье ", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }
            SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyy HH:mm:ss", Locale.ROOT);
            String s = sdf.format(System.currentTimeMillis());
        });

    }

    private String getTime(Context context, Long time) {
        return DateUtils.formatDateTime(context, time * 1000, DateUtils.FORMAT_SHOW_TIME);
    }

    private String getHours(int time) {
        int hours = (int) TimeUnit.SECONDS.toHours(time);
        int minutes = (int) ((int) TimeUnit.SECONDS.toMinutes(time) -
                (TimeUnit.SECONDS.toHours(time) * 60));

        return hours + "h " +  minutes + "m";
    }
}
