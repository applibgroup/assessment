package com.example.advanced;
import com.example.advanced.api.ApiClient;
import com.example.advanced.api.ApiInterface;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.advanced.models.Article;
import com.example.advanced.models.News;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstFragment extends Fragment {
    public static final String API_KEY="588c2325670649f4949bf46584f71f73";
    private RecyclerView mRecyclerView;
    private TripAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TransportViewModel transportViewModel;

    private List<Article> articles=new ArrayList<>();
    private ApiInterface apiInterface;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        transportViewModel =
                ViewModelProviders.of(this).get(TransportViewModel.class);
        View root = inflater.inflate(R.layout.fragment_first, container, false);

        mLayoutManager = new LinearLayoutManager(super.getContext());
        Locale locale = Locale.getDefault();
        String country = String.valueOf(locale.getCountry());
        apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
        String s="https://newsapi.org/v2/top-headlines?country=us&apiKey=588c2325670649f4949bf46584f71f73";
        Call<News> call;
        call=apiInterface.getNews(country.toLowerCase(),API_KEY);
       int i=1;
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if(response.isSuccessful() &&response.body().getStatus().equals("ok") )
                {
                    articles.clear();
                    articles=response.body().getArticle();
                    mRecyclerView = root.findViewById(R.id.recyclerview);
                    mRecyclerView.setHasFixedSize(true);
                    mAdapter = new TripAdapter(articles);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.setOnItemClickListener(new TripAdapter.OnItemClickListener() {
                        @Override
                        public void onDetailsClick(int position) {
                            SecondFragment someFragment = new SecondFragment();
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            someFragment.setNews(articles.get(position));
                            transaction.replace(R.id.nav_host_fragment, someFragment); // give your fragment container id in first parameter
                            transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                            transaction.commit();


                        }

                    });
                }
                else
                {
                    Log.d("Tag","No result");
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Log.d("Tag","Error");
            }
        });
        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }
}
