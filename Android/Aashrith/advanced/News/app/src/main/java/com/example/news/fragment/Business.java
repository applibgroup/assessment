package com.example.news.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.news.Adapter;
import com.example.news.ApiClient;
import com.example.news.data.Article;
import com.example.news.data.Headline;
import com.example.news.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Business extends Fragment {

    final String API_KEY = "98a295c4c04749a1aa784bf6c4256ed4";

    Adapter adapter;
    SwipeRefreshLayout refresh;
    RecyclerView recycler_view;
    EditText search;
    Button search_btn;

    List<Article> articles = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment,container,false);

        refresh = root.findViewById(R.id.refresh);
        recycler_view = root.findViewById(R.id.recycler_view);
        search = root.findViewById(R.id.search_query);
        search_btn = root.findViewById(R.id.btn_search);

        recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));

        refresh.setOnRefreshListener(() -> retrieveJson(""));
        retrieveJson("");

        search_btn.setOnClickListener(view -> {
            if (search.getText().toString().equals("")){
                refresh.setOnRefreshListener(() -> retrieveJson(""));
                retrieveJson(search.getText().toString());
            } else {
                refresh.setOnRefreshListener(() -> retrieveJson(search.getText().toString()));
                retrieveJson("");
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void retrieveJson(String query){

        refresh.setRefreshing(true);
        Call<Headline> call;

        if (!search.getText().toString().equals("")){
            call= ApiClient.getInstance().getApi().getSpecificData(query, API_KEY);
        } else {
            call= ApiClient.getInstance().getApi().getSpecificData("business", API_KEY);
        }

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

}