package com.example.contactapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<Contact> contactArrayList;

    public ContactAdapter(Context context, ArrayList<Contact> contactArrayList) {
        this.context = context;
        this.contactArrayList = contactArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.contact_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ViewHolder holder, int position) {
        Contact contact = contactArrayList.get(position);
        holder.contactTV.setText(contact.getName());
        holder.contactIV.setImageResource(contact.getImage());

        holder.itemView.setOnClickListener(v -> {
            // on below line we are opening a new activity and passing data to it.
            Intent i = new Intent(context, ContactDetailActivity.class);
            i.putExtra("name", contact.getName());
            i.putExtra("contact", contact.getNumber());
            i.putExtra("email", contact.getEmail());

            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), contact.getImage());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();
            String fileName = "SomeName.png";
            try {
                FileOutputStream fileOutStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
                fileOutStream.write(b);  //b is byte array
                fileOutStream.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            i.putExtra("image", fileName);
            // on below line we are starting a new activity,
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return contactArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView contactIV;
        private final TextView contactTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our image view and text view.
            contactIV = itemView.findViewById(R.id.idIVContact);
            contactTV = itemView.findViewById(R.id.idTVContactName);
        }
    }
}
