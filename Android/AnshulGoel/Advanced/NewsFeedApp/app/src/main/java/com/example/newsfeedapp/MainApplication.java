package com.example.newsfeedapp;

import com.example.newsfeedapp.di.*;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class MainApplication extends DaggerApplication {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {

        return DaggerAppComponent.builder().application(this).build(); // Binding an application instance to application component
    }
}
