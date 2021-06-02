package com.applibgroup.newsfeed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends AppCompatActivity {

    WebView browser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        browser = (WebView) findViewById(R.id.webview);
        browser.setWebViewClient(new WebViewClient());
        String url = getIntent().getStringExtra("URL");
        browser.getSettings().setJavaScriptEnabled(true);
        browser.loadUrl(url);

    }

    @Override
    public void onBackPressed() {
        if(browser.canGoBack()){
            browser.goBack();
        }else
            super.onBackPressed();
    }
}
