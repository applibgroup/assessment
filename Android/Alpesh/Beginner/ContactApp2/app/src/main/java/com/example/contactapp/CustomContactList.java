package com.example.contactapp;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class CustomContactList extends ArrayAdapter {
    private String[] ContactNames;
    private Integer[] imageid;
    private Activity context;

    public CustomContactList(MainActivity context, String[] ContactNames, Integer[] imageid) {
        super(context, R.layout.row, ContactNames);
        this.context = context;
        this.ContactNames = ContactNames;
        this.imageid = imageid;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if(convertView==null)
            row = inflater.inflate(R.layout.row, null, true);
        TextView ContactName = (TextView) row.findViewById(R.id.ContactName);
        ImageView idIVContact = (ImageView) row.findViewById(R.id.idIVContact);

        ContactName.setText(ContactNames[position]);
        idIVContact.setImageResource(imageid[position]);
        return  row;
    }
}
