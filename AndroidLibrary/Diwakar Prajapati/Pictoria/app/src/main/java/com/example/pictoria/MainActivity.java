package com.example.pictoria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.pictoria.Model.Item;
import com.example.pictoria.Model.ResponseFromAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    final String API_KEY = "21860795-7ffe5a87268f7873883bc1608";
    RecyclerView recyclerView;
    Adapter adapter;
    List<Item> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.editText);
                if(editText.getText().toString().trim().length()>0)
                    retrieveJSON(editText.getText().toString(),API_KEY);
            }
        });
    }

    public void retrieveJSON(String query, String apiKey){
        Log.d("DEBUG",query+" |-| "+apiKey);
        Call<ResponseFromAPI> call = ApiClient.getInstance().getApi().getResponse(apiKey,query);

        call.enqueue(new Callback<ResponseFromAPI>() {
            @Override
            public void onResponse(Call<ResponseFromAPI> call, Response<ResponseFromAPI> response) {
                if(response.isSuccessful() && response.body().getHits()!=null){
                    items.clear();
                    items=response.body().getHits();
                    if(items.size()==0){
                        Item result_not_found = new Item();
                        result_not_found.setUrl("https://thumbs.dreamstime.com/b/page-not-found-design-template-error-flat-line-concept-link-to-non-existent-document-no-results-magnifying-glass-156396935.jpg");
                        items.add(result_not_found);
                    }
                    adapter = new Adapter(MainActivity.this, items);
                    recyclerView.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<ResponseFromAPI> call, Throwable t) {
//                Log.d("DEBUG","FAILURE: "+t.getMessage());
            }
        });
    }
}