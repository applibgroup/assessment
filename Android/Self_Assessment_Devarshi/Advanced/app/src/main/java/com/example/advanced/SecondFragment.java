package com.example.advanced;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.advanced.databinding.FragmentSecondBinding;
import com.example.advanced.models.Article;

import org.jetbrains.annotations.NotNull;

public class SecondFragment extends Fragment {
    Article article;
    private FragmentSecondBinding binding;
    public void setNews(Article t)
    {
        this.article=t;
    }
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState

    ) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.itemTitle.setText(article.getTitle());
        binding.itemDescription.setText(article.getDescription());
        binding.itemContent.setText(article.getContent());
        binding.itemAuthor.setText(article.getAuthor());
        binding.itemPublishedAt.setText(article.getPublishedAt());
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}