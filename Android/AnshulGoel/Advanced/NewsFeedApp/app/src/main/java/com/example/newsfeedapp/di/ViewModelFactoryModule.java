package com.example.newsfeedapp.di;


import androidx.lifecycle.ViewModelProvider;

import com.example.newsfeedapp.viewmodel.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {
    // Responsible for doing dependency for ViewModelFactory class

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory modelProviderFactory);
}

