package com.example.newsapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment extends Fragment {


    ArrayList<ModelItems> items = new ArrayList <>();
    ItemAdapter adapter;

    Context context;
    int pos;
    public fragment() {

    }

    public fragment(int i) {

        this.pos = i;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.xmlfragment, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final RecyclerView recyclerView = Objects.requireNonNull(getView()).findViewById(R.id.recyclerView);


        final APIInterface apiService = APIClient.getClient().create(APIInterface.class);
        Call<ResponseModel> call;
        if (pos == 0) {
            call = apiService.getCategoryNews("general", "en", "aae53caa51b2478db36695f18a36141b");
        }
        else if(pos == 1){
            call = apiService.getCategoryNews("entertainment", "en", "aae53caa51b2478db36695f18a36141b");
        }
        else if(pos == 2){
            call = apiService.getCategoryNews("sports", "en", "aae53caa51b2478db36695f18a36141b");
        }
        else if(pos == 3){
            call = apiService.getCategoryNews("technology", "en", "aae53caa51b2478db36695f18a36141b");
        }
        else if(pos == 4){
            call = apiService.getCategoryNews("business", "en", "aae53caa51b2478db36695f18a36141b");
        }
        else if(pos == 5){
            call = apiService.getCategoryNews("science", "en", "aae53caa51b2478db36695f18a36141b");
        }
        else{
            call = apiService.getCategoryNews("health", "en", "aae53caa51b2478db36695f18a36141b");
        }

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel>call, Response<ResponseModel> response) {
                assert response.body() != null;
                if(response.body().getStatus().equals("ok")) {
                    Log.d("main", "success");
                    Log.d("main", String.valueOf(response.body().getTotalResults()));
                    List<Article> articleList = response.body().getArticles();
                    for(Article article: articleList) {
                        items.add(new ModelItems(article.getTitle(), article.getAuthor(), article.getPublishedAt(), article.getUrlToImage(), article.getUrl()));
                    }

                    adapter = new ItemAdapter(getActivity(), items);
                    recyclerView.setAdapter(adapter);

                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                }
            }
            @Override
            public void onFailure(Call<ResponseModel>call, Throwable t) {
                Log.e("out", t.toString());
            }
        });

    }




}
