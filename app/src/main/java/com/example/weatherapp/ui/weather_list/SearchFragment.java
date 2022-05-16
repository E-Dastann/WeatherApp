package com.example.weatherapp.ui.weather_list;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.example.weatherapp.R;
import com.example.weatherapp.base.BaseFragment;
import com.example.weatherapp.databinding.FragmentSearchBinding;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class SearchFragment extends BaseFragment<FragmentSearchBinding> implements OnMapReadyCallback {

    @Override
    protected FragmentSearchBinding bind() {
        return FragmentSearchBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void setUpView() {
        FragmentManager fm = getChildFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.google_map);
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.google_map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

    }

    @Override
    protected void callRequest() {

    }

    @Override
    protected void setUpListeners() {
//        binding.btnGo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                navController.navigate(SearchFragmentDirections.actionSearchFragmentToWeatherFragment(binding.etCity.getText().toString()));
//            }
//        });
    }

    @Override
    protected void setUpObservers() {

    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                googleMap.clear();
                MarkerOptions options = new MarkerOptions();
                options.draggable(true);
                options.title(latLng.toString());
                options.position(latLng);
                googleMap.addMarker(options);
                googleMap.animateCamera(getCameraUpdate(latLng, 4f, 90f, 30f));
                googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(@NonNull Marker marker) {
                        navController.navigate(SearchFragmentDirections.actionSearchFragmentToWeatherFragment(String.valueOf(marker.getPosition().latitude), String.valueOf(marker.getPosition().longitude)));
                    }
                });
            }

            private CameraUpdate getCameraUpdate(LatLng latLng, float v, float v1, float v2) {
                return CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(latLng)
                        .zoom(v)
                        .bearing(v1)
                        .tilt(v2)
                        .build()

                );
            }
        });
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                marker.showInfoWindow();
                return false;
            }
        });
        googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDrag(@NonNull Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(@NonNull Marker marker) {
                marker.setTitle(marker.getPosition().toString());
                marker.showInfoWindow();

            }

            @Override
            public void onMarkerDragStart(@NonNull Marker marker) {
                marker.hideInfoWindow();

            }
        });

    }
}