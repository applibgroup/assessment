package com.example.imageslider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.example.imageslider.databinding.ActivitySliderBinding;

public class SliderActivity extends AppCompatActivity {

    ActivitySliderBinding binding;  // for using View Binding

    private final String[] imageurls= new String[]{
            "https://images.unsplash.com/photo-1483914764278-6f2b1e39bba5?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=545&q=80",
            "https://images.unsplash.com/photo-1476121505803-741a289bba00?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=282&q=80",
            "https://images.unsplash.com/photo-1563819727292-8acb50c25e20?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NDN8fHRvcHxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
            "https://images.unsplash.com/photo-1500530855697-b586d89ba3ee?ixid=MnwxMjA3fDB8MHxzZWFyY2h8N3x8dG9wfGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
            "https://images.unsplash.com/photo-1565881606991-789a8dff9dbb?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MjV8fHRvcHxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
            "https://images.unsplash.com/photo-1543656696-00a96124f73a?ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mzh8fHRvcHxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
            "https://images.unsplash.com/photo-1603995228115-df0e33ee5514?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NzJ8fHRvcHxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= ActivitySliderBinding.inflate(getLayoutInflater());      //for view binding
        View view=binding.getRoot();
        setContentView(view);

        ViewPager viewPager=binding.viewpager;             //not required to use findViewById
        ViewPagerAdapter adapter= new ViewPagerAdapter(this,imageurls);    //passing the image urls
        viewPager.setAdapter(adapter);

    }
}