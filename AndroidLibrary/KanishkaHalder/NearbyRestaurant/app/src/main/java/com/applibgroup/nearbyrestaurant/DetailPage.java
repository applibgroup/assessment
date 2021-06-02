package com.applibgroup.nearbyrestaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;

import com.applibgroup.nearbyrestaurant.databinding.ActivityDetailPageBinding;
import com.applibgroup.nearbyrestaurant.databinding.ActivityMainBinding;
import com.squareup.picasso.Picasso;

public class DetailPage extends AppCompatActivity {

    ActivityDetailPageBinding binding;
    private String IMAGE_URL = "https://maps.googleapis.com/maps/api/place/photo?photoreference=PHOTO_REFERENCE&sensor=false&maxheight=MAX_HEIGHT&maxwidth=MAX_WIDTH&key=API_KEY";
    private String API_KEY = BuildConfig.CONSUMER_KEY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Place place = (Place) getIntent().getSerializableExtra("PLACE");
        if(place != null){
            if(place.getPhotoDetailsList().size()>0) {
                Place.PhotoDetail photoDetail = place.getPhotoDetailsList().get(0);
                IMAGE_URL = IMAGE_URL.replaceFirst("PHOTO_REFERENCE", photoDetail.getPhotoRefference());
                IMAGE_URL = IMAGE_URL.replaceFirst("MAX_HEIGHT",String.valueOf(photoDetail.getHeight()));
                IMAGE_URL = IMAGE_URL.replaceFirst("MAX_WIDTH",String.valueOf(photoDetail.getWidth()));
                IMAGE_URL = IMAGE_URL.replaceFirst("API_KEY",API_KEY);
                Picasso.get().load(IMAGE_URL).into(binding.imageViewDetail);
            }

            binding.textViewTitleDetail.setText(place.getName());
            if( place.getRating() !=  null)
                binding.textViewRatingDetail.setText("Rating: "+String.valueOf(place.getRating()));
            if(place.getDistance() != null)
                binding.textViewDistanceDetail.setText("Distance: "+place.getDistance() + "KM");
            if(place.getVicinity() != null)
                binding.textViewVicinityDetail.setText(place.getVicinity());
            if(place.getTags()!=null && place.getTags().size()>0) {
                String tags="";
                for (int i=0; i < place.getTags().size();i++){
                    tags = tags + " " +place.getTags().get(i) +",";
                }
                binding.textViewTags.setText(tags);
            }
            if(place.getOpenHours()!=null && place.getOpenHours().getOpenNow()){
                if(place.getOpenHours().getOpenNow()){
                    binding.textViewStatusDetail.setText("Open");
                    binding.textViewStatusDetail.setTextColor(Color.parseColor("#81B622"));
                }else{
                    binding.textViewStatusDetail.setText("Closed");
                    binding.textViewStatusDetail.setTextColor(Color.parseColor("#D10000"));
                }
            }
        }

    }
}