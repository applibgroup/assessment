package com.example.newsfeedapp.di;


import android.app.Application;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.newsfeedapp.repository.localStorageNewsDeprecated.NewsDatabaseHelper;
import com.example.newsfeedapp.util.Constants;
import com.example.newsfeedapp.util.NetworkUtil;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {


    @Singleton
    @Provides
    static NewsDatabaseHelper provideNewsDatabaseHelper(Application application, Gson gson) {
        return new NewsDatabaseHelper(application, gson);
    }

    @Singleton
    @Provides
    static NetworkUtil provideNetworkUtil(Application application) {
        return new NetworkUtil(application);
    }

    @Singleton
    @Provides
    static WebView provideWebView(Application application) {
        WebView webView = new WebView(application.getApplicationContext());
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAppCachePath(application.getCacheDir().getAbsolutePath());
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        return webView;
    }

    @Singleton
    @Provides
    static Gson provideGson() {
        return new Gson();
    }

    @Singleton
    @Provides
    static Retrofit provideRetrofit() {
        Retrofit.Builder retrofitBuilder =
                new Retrofit.Builder()
                        .baseUrl(Constants.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create());

        return retrofitBuilder.build();
    }
}

