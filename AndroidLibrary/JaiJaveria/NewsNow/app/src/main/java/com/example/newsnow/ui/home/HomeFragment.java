package com.example.newsnow.ui.home;

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
import com.example.newsnow.NewsAdapter;
import com.example.newsnow.NewsItem;
import com.example.newsnow.R;
import com.example.newsnow.databinding.FragmentHomeBinding;
import com.example.newsnow.ui.gallery.GalleryFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("HFrag","inside onViewCreated");
        View pb =view.findViewById(R.id.animation_viewH);
        pb.setVisibility(View.VISIBLE);
        final RecyclerView recyclerView =  view.findViewById(R.id.recycler_home);
        NewsAdapter newsAdapter=new NewsAdapter(this.getContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(newsAdapter);
        List<NewsItem> l= new ArrayList<>();
        API api = new API();
        api.getClientG().getNews(GalleryFragment.getCallbackFunc(l, newsAdapter, view));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}