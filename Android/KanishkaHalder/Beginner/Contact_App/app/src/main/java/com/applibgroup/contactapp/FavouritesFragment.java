package com.applibgroup.contactapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.applibgroup.contactapp.db.AppDatabase;
import com.applibgroup.contactapp.db.User;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import io.paperdb.Paper;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavouritesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavouritesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private GridView gridView;
    private static MainAdapter adapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static List<User> favQueue = new ArrayList<>();

    public FavouritesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavouritesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavouritesFragment newInstance(String param1, String param2) {
        FavouritesFragment fragment = new FavouritesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Paper.init(getContext());
        if(Paper.book().contains("favourite")){
            favQueue = Paper.book().read("favourite");
        }else{
            Paper.book().write("favourite", favQueue);
        }
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public static void setFavQueue(User user){
        if(favQueue.stream().noneMatch(user1 -> user.getUid() == user.getUid())){
            favQueue.add(user);
        } else {
            favQueue.removeIf(user1 -> user1.getUid() == user.getUid());
            favQueue.add(user);
        }
        if(favQueue.size()>4)
            favQueue.remove(0);
        Paper.book().write("favourite", favQueue);
        List<User> list = new ArrayList<User>(favQueue);
        Collections.reverse(list);
        adapter.setUsers(list);
    }

    public static void updateFavQueue(){
        Paper.book().write("favourite", favQueue);
        List<User> list = new ArrayList<User>(favQueue);
        Collections.reverse(list);
        adapter.setUsers(list);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);

        gridView = view.findViewById(R.id.favourite_grid_view);

        final List<User> userList = new ArrayList<>(favQueue);
        Collections.reverse(userList);
        adapter = new MainAdapter(getContext(),userList);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), ContactDetailActivity.class);
                List<User> list = new ArrayList<>(favQueue);
                Collections.reverse(list);
                intent.putExtra("USER",list.get(i));
                FavouritesFragment.setFavQueue(list.get(i));
                startActivity(intent);
            }
        });
        return view;
    }

    private class MainAdapter extends BaseAdapter {
        private Context context;
        private List<User> users;
        private LayoutInflater inflater;

        public MainAdapter(Context c,List<User> userList){
            context = c;
            this.users = userList;
        }

        public void setUsers(List<User> list){
            this.users = list;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return users.size();
        }

        @Override
        public Object getItem(int i) {
            return users.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if(inflater == null){
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            if(view==null){
                view = inflater.inflate(R.layout.grid_item,null);
            }
            ImageView imageView = view.findViewById(R.id.grid_image_view);
            TextView textView = view.findViewById(R.id.grid_text_view);

            Bitmap bitmap = BitmapFactory.decodeByteArray(users.get(i).getImage(), 0, users.get(i).getImage().length);
            imageView.setImageBitmap(bitmap);

            textView.setText(users.get(i).getFirstName());
            return view;
        }
    }
}
