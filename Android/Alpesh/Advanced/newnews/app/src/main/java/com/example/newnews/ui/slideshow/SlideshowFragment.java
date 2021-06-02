package com.example.newnews.ui.slideshow;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newnews.Article;
import com.example.newnews.ItemAdapter;
import com.example.newnews.R;
import com.example.newnews.databinding.FragmentHomeBinding;
import com.example.newnews.databinding.FragmentSlideshowBinding;
import com.example.newnews.ui.home.HomeViewModel;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.request.EverythingRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SlideshowFragment extends Fragment {

    ArrayList<Article> items = new ArrayList<>();
    ItemAdapter adapter;
    Context context;
    //////////////

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.act, container, false); }
    //setContentView(R.layout.act);
///////////////////////////////


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        final RecyclerView recyclerView =  getView().findViewById(R.id.recy_view);
        Log.d("MyActivity", "*****************222: " + items.size());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ItemAdapter(getActivity(), items);

        recyclerView.setAdapter(adapter);
        /////////////////////////////////////
        NewsApiClient newsApiClient = new NewsApiClient("1186050610e64c2a941a20f0186639e8");
        newsApiClient.getEverything(
                new EverythingRequest.Builder()
                        .q("US")
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                        for (int i = 0; i < response.getArticles().size(); i++)
                        {
                            Article at =new Article();
                            //at.setAuthor( response.getArticles().get(1).getAuthor());
                            at.setTitle( response.getArticles().get(i).getTitle());
                            at.setUrlToImage( response.getArticles().get(i).getUrlToImage());
                            at.setDescription( response.getArticles().get(i).getDescription() );
                            at.setUrl( response.getArticles().get(i).getUrl() );
                            at.setPublishedAt( response.getArticles().get(i).getPublishedAt() );
                            at.setDate(response.getArticles().get(i).getPublishedAt());
                            //at.setSource( response.getArticles().get(i).getSource());
                            items.add(at);
                            adapter.notifyDataSetChanged();
                        }
                        //System.out.println(at);
                        Log.d("MyActivity", "*****************: " + items);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        System.out.println(throwable.getMessage());
                    }
                }
        );
///////////////////////////////



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}