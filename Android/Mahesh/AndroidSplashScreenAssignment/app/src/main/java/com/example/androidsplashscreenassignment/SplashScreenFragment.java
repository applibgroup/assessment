package com.example.androidsplashscreenassignment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;

public class SplashScreenFragment extends Fragment {

    private final int splashPosition;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View splashPageView =  inflater.inflate(R.layout.activity_launch, container, false);

        setSplashTextContent(splashPageView);

        return splashPageView;

    }

    private void setSplashTextContent(View splashPageView) {
        TextView splashText1 = (TextView) splashPageView.findViewById(R.id.splashText1);
        splashText1.setText(SplashScreenData.GetSplashText(splashPosition));

        TextView splashTitle = (TextView) splashPageView.findViewById(R.id.splashTitle);
        splashTitle.setText(SplashScreenData.GetSplashTitle(splashPosition));
    }

    public SplashScreenFragment(int position)
    {
        splashPosition = position;
    }
}