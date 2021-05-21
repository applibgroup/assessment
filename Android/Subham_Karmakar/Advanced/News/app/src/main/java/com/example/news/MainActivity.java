package com.example.news;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
{
    RequestQueue queue;
    String url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=8987e832944046e2b543ec9baee0feb9";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response ->
        {
            Log.d("ABC", "Hi");
            //                Log.d("ABC", response.getJSONArray("articles"));
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.d("ABC", error.toString());
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

//        queue.add(jsonObjectRequest);
    }
}