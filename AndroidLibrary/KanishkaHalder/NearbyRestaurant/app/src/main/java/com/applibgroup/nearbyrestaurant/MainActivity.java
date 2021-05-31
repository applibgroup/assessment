package com.applibgroup.nearbyrestaurant;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.applibgroup.nearbyrestaurant.databinding.ActivityMainBinding;
import com.facebook.stetho.Stetho;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ViewAdapter.OnViewClickListener {

    private ActivityMainBinding binding;
    private FusedLocationProviderClient fusedLocationClient;
    private String[] permissions = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    static List<Place> placesList = new ArrayList<>();

    private ViewAdapter adapter;
    protected String API_KEY = BuildConfig.CONSUMER_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Paper.init(this);
        Stetho.initializeWithDefaults(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocationAndPlaces();
        } else {
            requestPermissionLauncher.launch(permissions);
        }

        RecyclerView recyclerView = binding.rvList;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this){
            @Override
            public boolean isAutoMeasureEnabled() {
                return false;
            }
        };
        linearLayoutManager.isAutoMeasureEnabled();
        adapter = new ViewAdapter(placesList,this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);


    }

    private void getLocationAndPlaces() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            Paper.book().write("LAT",location.getLatitude());
                            Paper.book().write("LON",location.getLongitude());
                            fetchNearbyPlaces("restaurant", location, API_KEY);
                        }
                    }
                });
    }

    private void fetchNearbyPlaces(String type, Location location, String apiKey){
        final ApiInterface apiService = Api.getClient().create(ApiInterface.class);
        String strLocation = String.valueOf(location.getLatitude())+","+String.valueOf(location.getLongitude());
        Call<ResponseModel> call = apiService.getNearbyPlaces(strLocation,"1500",type,apiKey);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                Log.d("NEARBY PLACES", "onResponse: "+response.isSuccessful() + response.body().getStatus());
                if (response.isSuccessful() && response.body().getStatus().equals("OK")){

                        placesList = response.body().getPlaces();
                        for (int i=0;i<placesList.size();i++){
                            Log.d("NEARBY PLACES", "onResponse: "+placesList.get(i).getName());
                        }
                        adapter.updateList(placesList);

                }
            }
            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.d("NEARBY PLACES", "onResponse: FAILED");
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private ActivityResultLauncher<String[]> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), isGranted -> {
                if (isGranted.containsValue(false)) {
                    Toast.makeText(this, "Please provide Permission", Toast.LENGTH_SHORT).show();
                } else {
                    getLocationAndPlaces();
                }
            });

    @Override
    public void onListItemClick(int position) {
        Intent intent = new Intent(this,DetailPage.class);
        intent.putExtra("PLACE", placesList.get(position));
        startActivity(intent);
    }
}