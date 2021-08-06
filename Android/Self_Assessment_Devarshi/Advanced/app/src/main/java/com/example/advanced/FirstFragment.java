package com.example.advanced;
import com.example.advanced.api.ApiClient;
import com.example.advanced.api.ApiInterface;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.advanced.models.Article;
import com.example.advanced.models.News;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstFragment extends Fragment {
    public static final String API_KEY="588c2325670649f4949bf46584f71f73";
    private RecyclerView mRecyclerView;
    private NewsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Article> articles=new ArrayList<>();
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_first, container, false);
        ApiInterface apiInterface;
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);


        }
        mLayoutManager = new LinearLayoutManager(super.getContext());
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
        Call<News> call;
        call=apiInterface.getNews(country.toLowerCase(),API_KEY);
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(@NotNull Call<News> call, @NotNull Response<News> response) {
                if(response.isSuccessful() && Objects.requireNonNull(response.body()).getStatus().equals("ok") )
                {
                    articles.clear();
                    articles=response.body().getArticle();
                    mRecyclerView = root.findViewById(R.id.recyclerview);
                    mRecyclerView.setHasFixedSize(true);
                    mAdapter = new NewsAdapter(articles);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.setOnItemClickListener(position -> {
                        if(isAdded())
                        {
                            SecondFragment someFragment = new SecondFragment();
                            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
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
            public void onFailure(@NotNull Call<News> call, @NotNull Throwable t) {
                Log.d("Tag","Error");
            }
        });
        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }
}
