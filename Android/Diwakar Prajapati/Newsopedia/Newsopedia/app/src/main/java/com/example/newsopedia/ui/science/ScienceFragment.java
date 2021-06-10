package com.example.newsopedia.ui.science;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsopedia.Adapter;
import com.example.newsopedia.ApiClient;
import com.example.newsopedia.Model.Articles;
import com.example.newsopedia.Model.Category;
import com.example.newsopedia.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScienceFragment extends Fragment {

    private ScienceViewModel scienceViewModel;

    private AppBarConfiguration mAppBarConfiguration;
    final String API_KEY = "58a952499a7341b3955c30e0798bbb42";
    RecyclerView recyclerView;
    Adapter adapter;
    List<Articles> articles = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        scienceViewModel =
                new ViewModelProvider(this).get(ScienceViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        retrieveJSON("science",API_KEY);

        EditText editText = root.findViewById(R.id.editText);
        root.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editText.getText().toString().trim();
                editText.setText("");
                if(!s.equals("")){
                    retrieveJSON(s,API_KEY);
                }
            }
        });


        return root;
    }

    public void retrieveJSON(String query, String apiKey){
        Call<Category> call = ApiClient.getInstance().getApi().getCategory(query,apiKey);
        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if(response.isSuccessful() && response.body().getArticles()!=null){
                    articles.clear();
                    articles=response.body().getArticles();
                    adapter = new Adapter(getContext(), articles);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {

            }
        });
    }
}