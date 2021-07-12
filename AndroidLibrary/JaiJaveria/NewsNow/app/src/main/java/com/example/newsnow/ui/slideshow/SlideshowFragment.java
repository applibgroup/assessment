package com.example.newsnow.ui.slideshow;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsnow.API;
import com.example.newsnow.NewsAdapter;
import com.example.newsnow.NewsItem;
import com.example.newsnow.R;
import com.example.newsnow.databinding.FragmentSlideshowBinding;
import com.example.newsnow.ui.gallery.GalleryFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("SFrag","inside onViewCreated");
        View pb =view.findViewById(R.id.animation_viewS);
        pb.setVisibility(View.VISIBLE);
        final RecyclerView recyclerView =  view.findViewById(R.id.recycler_slideshow);
        NewsAdapter newsAdapter=new NewsAdapter(this.getContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(newsAdapter);
        List<NewsItem> l= new ArrayList<>();
        API api = new API();
        api.getClientB().getNews(GalleryFragment.getCallbackFunc(l,newsAdapter, view));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}