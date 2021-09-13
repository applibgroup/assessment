package com.example.newsfeedapp.di;

import androidx.lifecycle.ViewModel;

import com.example.newsfeedapp.viewmodel.NewsViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class NewsViewModelsModule {
    // Responsible for dependency for AuthViewModelsModule itself

    @Binds
    @IntoMap
    @ViewModelKey(NewsViewModel.class)
    public abstract ViewModel bindAuthViewModel(NewsViewModel viewModel);
}
