package com.example.androidsplashscreenassignment;

import java.util.HashMap;

public class SplashScreenData {

    static String splashTitle1 = "Hello.";
    static String splashTitle2 = "Easy to Use.";
    static String splashTitle3 = "Get Started Today.";

    static String splashTextPage1 = "Welcome to LeafScan, an indispensable scanning service to suit your botanical requirements.";
    static String splashTextPage2 = "Quickly identify and locate any and all plant species with a few taps.";
    static String splashTextPage3 = "Take the first step of your green journey today.";

    /*
    In the future, should change implementation of splash text to more sophisticated structure
     */
    public static String GetSplashTitle(int position)
    {
        switch (position){
            case 0: return splashTitle1;
            case 1: return splashTitle2;
            case 2: return splashTitle3;
            default: return "";
        }
    }
    public static String GetSplashText(int position)
    {
        switch (position){
            case 0: return splashTextPage1;
            case 1: return splashTextPage2;
            case 2: return splashTextPage3;
            default: return "";
        }
    }

}
