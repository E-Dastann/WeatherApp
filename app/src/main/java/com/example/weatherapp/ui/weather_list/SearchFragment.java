package com.example.weatherapp.ui.weather_list;

import android.view.View;

import com.example.weatherapp.base.BaseFragment;
import com.example.weatherapp.data.model.Sys;
import com.example.weatherapp.databinding.FragmentSearchBinding;


public class SearchFragment extends BaseFragment<FragmentSearchBinding> {

    @Override
    protected FragmentSearchBinding bind() {
        return FragmentSearchBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void setUpView() {

    }

    @Override
    protected void callRequest() {

    }

    @Override
    protected void setUpListeners() {
        binding.btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(SearchFragmentDirections.actionSearchFragmentToWeatherFragment(binding.etCity.getText().toString()));
            }
        });
    }

    @Override
    protected void setUpObservers() {

    }


}