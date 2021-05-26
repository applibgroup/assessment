package com.example.newsfeedapp.di;

import android.app.Application;

import com.example.newsfeedapp.MainApplication;
import com.example.newsfeedapp.viewmodel.ViewModelProviderFactory;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(
        modules = {
                AndroidSupportInjectionModule.class,
                ActivityBuildersModule.class,
                AppModule.class,
                ViewModelProviderFactory.class,
        }
)
public interface AppComponent extends AndroidInjector<MainApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}

