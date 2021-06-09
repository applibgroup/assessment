package com.example.advanced;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private PlaceholderAPI apiInterface;
    private List<PlaceholderPost> placeholderlist = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private TripAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TransportViewModel transportViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .build();

        transportViewModel =
                ViewModelProviders.of(this).get(TransportViewModel.class);
        View root = inflater.inflate(R.layout.fragment_first, container, false);
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        apiInterface = retrofit.create(PlaceholderAPI.class);
        String apikey = "f6dfdcab496b4d04bb070ed1238c1475";
        Call<News> call = apiInterface.getPosts(country.toLowerCase(), apikey);

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {

                if(response.isSuccessful() &&response.body().getStatus().equals("ok")) {
                    placeholderlist.clear();
                    placeholderlist = Collections.unmodifiableList((List<PlaceholderPost>) response.body().getArticle());
                    mRecyclerView = root.findViewById(R.id.recyclerview);
                    mRecyclerView.setHasFixedSize(true);
                    mAdapter = new TripAdapter(placeholderlist);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    Log.d("Yo", "Boo!");
                    return;
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

            }



        });


        Log.d("Yo","Hello!");
    }
}
