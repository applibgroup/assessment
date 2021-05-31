package com.example.newsnow.ui.gallery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsnow.API;
import com.example.newsnow.Article;
import com.example.newsnow.MySingleton;
import com.example.newsnow.NewsAdapter;
import com.example.newsnow.NewsItem;
import com.example.newsnow.Pojo;
import com.example.newsnow.R;
import com.example.newsnow.databinding.FragmentGalleryBinding;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;
    //    JSONArray news=null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.d("GFrag", "inside onCreateView");
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("GFrag", "inside onViewCreated");
        View pb = view.findViewById(R.id.animation_viewG);
        pb.setVisibility(View.VISIBLE);
        final RecyclerView recyclerView = view.findViewById(R.id.recycler_gallery);
        NewsAdapter newsAdapter = new NewsAdapter(this.getContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(newsAdapter);
        List<NewsItem> l = new ArrayList<>();
        API api = new API();
        api.getClient().getNews(getCallbackFunc(l, newsAdapter, view));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static Callback<Pojo> getCallbackFunc(List<NewsItem> l, NewsAdapter newsAdapter, View view) {
        Callback<Pojo> c = new Callback<Pojo>() {
            @Override
            public void success(Pojo pojo, Response response) {
                Log.d("getCallbackFunc", "inside onViewCreated success");
                List<Article> arr = pojo.getArticles();
                for (int i = 0; i < arr.size(); i++) {
                    NewsItem n = new NewsItem();
                    Article j = arr.get(i);
                    n.heading = j.getTitle();
//                    Log.d("GFrag", "inside onViewCreated success: " + n.heading);
                    n.img_url = j.getUrlToImage();
                    n.news_url = j.getUrl();
                    l.add(n);
                }
                newsAdapter.getNewsList(l);
                Log.d("getCallbackFunc", "calling news adapter notify dataset changed.");
                View pb = view.findViewById(R.id.animation_viewG);
                if (pb == null) {
                    pb = view.findViewById(R.id.animation_viewH);
                }
                if (pb == null) {
                    pb = view.findViewById(R.id.animation_viewS);
                }
                pb.setVisibility(View.GONE);
                newsAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("GFrag", "api.getClient failed. Error message: " + error.toString());
            }
        };
        return c;
    }
}
