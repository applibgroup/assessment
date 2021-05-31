package com.example.newsfeedassignment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsfeedassignment.Model.ModelData;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.HashMap;

public class NewsFragment extends Fragment {

    private static final String myAPIKey = "9f123e448c9bc0cda31d72b63e2b389f";
    private static final String apiBaseUrl = "http://api.mediastack.com/v1/news";
    private static final HashMap<Integer, String> newsSiteNames;
    private static final int NEWS_SITE_COUNT;
    private static final String TAG = "NEWSAPICALLS";

    static {
        newsSiteNames = new HashMap<Integer, String>();
        newsSiteNames.put(0,"bbc");
        newsSiteNames.put(1,"cnn");
        NEWS_SITE_COUNT = newsSiteNames.size();
    }

    ArrayList<ModelData> newsItems = new ArrayList <>();
    ItemAdapter adapter;

    Context context;
    int tabPosition;
    public NewsFragment() {
        this.tabPosition = 0;
    }

    public NewsFragment(int i) {
        this.tabPosition = i;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.layout_news_recyclerview, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final RecyclerView recyclerView = getView().findViewById(R.id.recyclerView);

        String newsSiteName="";
        if (tabPosition >= 0 && tabPosition < NEWS_SITE_COUNT - 1)
        {
            newsSiteName = newsSiteNames.get(tabPosition);
        }
        else
        {
            newsSiteName = newsSiteNames.get(NEWS_SITE_COUNT - 1);
        }

        String newsRequestUrl = apiBaseUrl + "?access_key=" + myAPIKey + "&sources=" + newsSiteName;

        String finalNewsSiteName = newsSiteName;
        Ion.with(this).load("GET", newsRequestUrl).asJsonObject().setCallback(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                if (result != null)
                    Log.d(TAG, "API Request Loaded Object with keys: " + result.keySet());
                else
                    Log.e(TAG, "API Request resulted in null JSON file. Exception: " + e.getMessage());

                if (result.keySet().contains("data")) {

                    JsonArray dataArray = result.get("data").getAsJsonArray();

                    for (int i = 0; i < dataArray.size(); i++) {
                        JsonObject dataObject = dataArray.get(i).getAsJsonObject();

                        String author = dataObject.get("author").toString();

                        String title = dataObject.get("title").toString();
                        title = title.substring(1, title.length() - 1);

                        String url = dataObject.get("url").toString();
                        url = retrievedUrlFixer(url, finalNewsSiteName);

                        String urlToImage = dataObject.get("image").toString();
                        urlToImage = urlToImage.substring(1, urlToImage.length() - 1);

                        String date = dataObject.get("published_at").toString();
                        String desc = dataObject.get("description").toString();
                        desc = desc.substring(1, desc.length() - 1);
                        desc = shortenDescription(desc);

                        newsItems.add(new ModelData(title, author, date, urlToImage, url, desc));


                    }

                    adapter = new ItemAdapter(getActivity(), newsItems);
                    recyclerView.setAdapter(adapter);

                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);


                    Log.d(TAG, "Successfully retrieved news item.");
                } else {
                    Log.e(TAG, "Error in retrieving news item from API call " + newsRequestUrl);
                }
            }
        });

    }

    private String retrievedUrlFixer(String url, String newsSite)
    {
        String outUrl;
        outUrl = url.substring(1, url.length() - 1);
        return outUrl;
    }

    private String shortenDescription(String description)
    {
        String outDesc = "";
        if (description == null)
        {
            return outDesc;
        }
        else{
            if (description.length() > 100)
            {
                outDesc = description.substring(0, 100);
                outDesc += "...";
            }
        }
        return outDesc;
    }

}
