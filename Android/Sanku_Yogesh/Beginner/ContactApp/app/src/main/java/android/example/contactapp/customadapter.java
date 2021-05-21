package android.example.contactapp;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class customadapter extends ArrayAdapter<contact> {

    public contact[] arr;
    public Context context;

    public customadapter(@NonNull Context context, int resource, @NonNull contact[] arr) {
        super(context, resource, arr);
        this.context = context;
        this.arr = arr;
    }


    @Nullable
    @Override
    public contact getItem(int position) {
        return arr[position];
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.customlayout,parent,false);
        TextView t = convertView.findViewById(R.id.custname);
        ImageView image = convertView.findViewById(R.id.custimage);
        t.setText(getItem(position).name);
        image.setImageResource(getItem(position).photoid);
        return convertView;

    }
}
