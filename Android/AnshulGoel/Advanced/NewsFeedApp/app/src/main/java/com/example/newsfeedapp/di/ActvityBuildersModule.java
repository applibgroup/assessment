package com.example.newsfeedapp.di;


import com.example.newsfeedapp.view.NewsActivity;
import com.example.newsfeedapp.view.NewsDetailsActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
            modules = {NewsViewModelsModule.class}
    )
    abstract NewsActivity contributeNewsActivity();

    @ContributesAndroidInjector
    abstract NewsDetailsActivity contributeNewsDetailsActivity();
}
