package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp.Retrofit.ApiClient;
import com.example.weatherapp.Retrofit.ApplicationInterface;
import com.example.weatherapp.Retrofit.Example;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ImageView search;
    TextView tempText;
    TextView descriptionText;
    TextView humidityText;
    EditText search_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = findViewById(R.id.search);
        tempText = findViewById(R.id.tempText);
        descriptionText = findViewById(R.id.descriptionText);
        humidityText = findViewById(R.id.humidityText);
        search_text = findViewById(R.id.search_text);

        tempText.setText("Search for city's name");
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWeatherData(search_text.getText().toString());
            }
        });
    }

    private void getWeatherData(String name){
        ApplicationInterface applicationInterface = ApiClient.getClient().create(ApplicationInterface.class);

        Call<Example> call = applicationInterface.getWeatherData(name);
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if (response.isSuccessful()){
                    tempText.setText("Temperature "+response.body().getMain().getTemp()+"°C");
                    descriptionText.setText("Feels Like "+response.body().getMain().getFeels_like()+"°C");
                    humidityText.setText("Humidity: "+response.body().getMain().getHumidity()+"%");
                }
                else{
                    tempText.setText("Data Not Found!!");
                    descriptionText.setText("");
                    humidityText.setText("");
                }

            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
