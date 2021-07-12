package com.example.toplibrariesassignment.Model;

import com.example.toplibrariesassignment.Model.ModelData;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LocationApiResponse {
    public String status;
    @SerializedName("items")
    public ArrayList<ModelData> modelDataList;
}
