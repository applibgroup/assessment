package com.example.newsapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.newsapp.Adapter;
import com.example.newsapp.ApiClient;
import com.example.newsapp.R;
import com.example.newsapp.data.Article;
import com.example.newsapp.data.Headline;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SportsFragment extends Fragment {

    final String API_KEY = "6084b9529b9040fca56bda74f9d88043";

    RecyclerView.Adapter adapter;
    SwipeRefreshLayout refresh;
    RecyclerView recycler_view;

    List<Article> articles = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home,container,false);

        refresh = root.findViewById(R.id.refresh);
        recycler_view = root.findViewById(R.id.recycler_view);

        recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        final String country = getCountry();

        refresh.setOnRefreshListener(() -> retrieveJson("", country));
        retrieveJson("", country);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void retrieveJson(String query, String country){

        refresh.setRefreshing(true);
        Call<Headline> call = ApiClient.getInstance().getApi().getSpecificData("sport", API_KEY);

        call.enqueue(new Callback<Headline>() {
            @Override
            public void onResponse(@NotNull Call<Headline> call, @NotNull Response<Headline> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getArticles() != null) {
                        refresh.setRefreshing(false);
                        articles.clear();
                        articles = response.body().getArticles();
                        adapter = new Adapter(getContext(), articles);
                        recycler_view.setAdapter(adapter);
                    }
                }
            }
            @Override
            public void onFailure(@NotNull Call<Headline> call, @NotNull Throwable error) {
                refresh.setRefreshing(false);
                Toast.makeText(getContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getCountry(){
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }

}