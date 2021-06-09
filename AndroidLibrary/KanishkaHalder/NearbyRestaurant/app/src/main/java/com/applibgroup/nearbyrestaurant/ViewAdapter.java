package com.applibgroup.nearbyrestaurant;

import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.paperdb.Paper;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ViewHolder> {

    private List<Place> placeList;
    private OnViewClickListener clickListener;

    public ViewAdapter(List<Place> dataList, OnViewClickListener onViewClickListener ){
        this.placeList = dataList;
        this.clickListener = onViewClickListener;
    }

    public void updateList(List<Place> list){
        this.placeList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.place_item_layout,parent,false);
        return new ViewHolder(view,clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Place place = placeList.get(position);

        holder.title.setText(place.getName());
        if(place.getVicinity() != null)
            holder.description.setText(place.getVicinity());
        else if(place.getStatus() != null)
            holder.description.setText(place.getStatus());

        if (Paper.book().read("LAT") != null){
            Location currentLocation = new Location("");
            currentLocation.setLatitude(Paper.book().read("LAT"));
            currentLocation.setLongitude(Paper.book().read("LON"));

            Location destLocation = new Location("");
            destLocation.setLatitude(place.getGeometry().getLocation().getLat());
            destLocation.setLongitude(place.getGeometry().getLocation().getLng());

            DecimalFormat df = new DecimalFormat("0.00");
            double distance = currentLocation.distanceTo(destLocation);
            holder.distance.setText("Distance: "+df.format(distance/1000)+"KM");
            Place updatedPlace = placeList.get(position);
            updatedPlace.setDistance(df.format(distance/1000));
            MainActivity.placesList.set(position,updatedPlace);
        }



    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        TextView description;
        TextView time;
        TextView distance;

        OnViewClickListener viewClickListener;

        public ViewHolder(@NonNull View itemView, OnViewClickListener onViewClickListener) {
            super(itemView);

            title = itemView.findViewById(R.id.text_view_title_place_feed);
            description = itemView.findViewById(R.id.text_view_desc_place_feed);
            time = itemView.findViewById(R.id.text_view_date_time);
            distance = itemView.findViewById(R.id.text_view_distance);
            this.viewClickListener = onViewClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            viewClickListener.onListItemClick(getAdapterPosition());
        }
    }
    public interface OnViewClickListener {
        void onListItemClick(int position);
    }

}
