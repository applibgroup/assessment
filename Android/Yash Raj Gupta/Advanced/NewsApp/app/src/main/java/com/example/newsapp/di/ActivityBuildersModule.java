package com.example.newsapp.di;

import com.example.newsapp.view.NewsActivity;
import com.example.newsapp.view.NewsDetailsActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
            modules = {NewsViewModelsModule.class}
    )
    abstract NewsActivity contributeNewsActivity();

    @ContributesAndroidInjector
    abstract NewsDetailsActivity contributeNewsDetailsActivity();
}
