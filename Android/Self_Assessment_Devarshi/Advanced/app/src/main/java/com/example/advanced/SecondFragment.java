package com.example.advanced;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.advanced.databinding.FragmentSecondBinding;
import com.example.advanced.models.Article;
import com.example.advanced.models.News;

public class SecondFragment extends Fragment {
    Article n;
    private FragmentSecondBinding binding;
    public void setNews(Article t)
    {
        this.n=t;
    }
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState

    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.itemTitle.setText(n.getTitle());
        binding.itemDescription.setText(n.getDescription());
        binding.itemContent.setText(n.getContent());
        binding.itemAuthor.setText(n.getAuthor());
        binding.itemPublishedAt.setText(n.getPublishedAt());


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}