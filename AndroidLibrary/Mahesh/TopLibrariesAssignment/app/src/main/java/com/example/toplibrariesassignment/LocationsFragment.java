package com.example.toplibrariesassignment;

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

import com.example.toplibrariesassignment.Model.LocationApiResponse;
import com.example.toplibrariesassignment.Model.ModelData;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationsFragment extends Fragment {

    private static final HashMap<Integer, String> cityNames;
    private static final int CITIES_COUNT;
    private static final String TAG = "LOCAPI";

    static {
        cityNames = new HashMap<Integer, String>();
        cityNames.put(0,"kochi");
        cityNames.put(1,"chennai");
        CITIES_COUNT = cityNames.size();
    }

    ArrayList<ModelData> locationItems = new ArrayList <ModelData>();
    ItemAdapter adapter;

    Context context;
    int tabPosition;
    public LocationsFragment() {
        this.tabPosition = 0;
    }

    public LocationsFragment(int i) {
        this.tabPosition = i;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_cities_recyclerview, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String newsSiteName = getCityName();
        final RecyclerView recyclerView = getView().findViewById(R.id.recyclerView);

        Call<LocationApiResponse> locationRequest = RetrofitClient.getInstance().getLocationApi().getLocationDetailsAt(newsSiteName);
        locationRequest.enqueue(new Callback<LocationApiResponse>() {
            @Override
            public void onResponse(Call<LocationApiResponse> call, Response<LocationApiResponse> response) {
                LocationApiResponse responseObject = response.body();
                if (responseObject == null)
                    Log.e("APIREQUEST", "Retrieved null object: " + response.message());
                else {
                    Log.d("APIREQUEST", "Api request obtained response with status: " + responseObject.status);

                    locationItems = responseObject.modelDataList;

                    adapter = new ItemAdapter(getActivity(), locationItems);
                    recyclerView.setAdapter(adapter);

                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                }
            }
            @Override
            public void onFailure(Call<LocationApiResponse> call, Throwable t) {
                //handle error or failure cases here
                Log.e("APIREQUEST", "Api request failed to obtain successful response. Exception:" + t.getMessage());
            }
        });
    }

    private String getCityName() {
        return getCityName(tabPosition);
    }

    public static String getCityName(int position) {
        String newsSiteName="";
        if (position >= 0 && position < CITIES_COUNT - 1)
        {
            newsSiteName = cityNames.get(position);
        }
        else
        {
            newsSiteName = cityNames.get(CITIES_COUNT - 1);
        }
        return newsSiteName;
    }
}
