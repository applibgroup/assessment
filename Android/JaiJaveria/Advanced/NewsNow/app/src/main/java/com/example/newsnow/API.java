package com.example.newsnow;

import retrofit.RestAdapter;

public class API {
    public static APIInterface getClient( ) {
        String url="https://newsapi.org";
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(url) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        APIInterface api = adapter.create(APIInterface.class);

        return api;
    }

    public static APIInterfaceB getClientB() {
        String url="https://newsapi.org";
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(url) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        APIInterfaceB api = adapter.create(APIInterfaceB.class);

        return api;
    }
    public static APIInterfaceG getClientG() {
        String url="https://newsapi.org";
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(url) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        APIInterfaceG api = adapter.create(APIInterfaceG.class);

        return api;
    }
}
