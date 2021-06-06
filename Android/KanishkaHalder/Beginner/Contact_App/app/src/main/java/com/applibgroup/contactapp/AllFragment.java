package com.applibgroup.contactapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.applibgroup.contactapp.db.AppDatabase;
import com.applibgroup.contactapp.db.User;

import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class AllFragment extends Fragment implements UserListAdapter.OnItemListener{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final int ADD_USER_REQUEST_CODE = 130;

    public static List<User> userList;

    private String mParam1;
    private String mParam2;
    private static UserListAdapter userListAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllFragment newInstance(String param1, String param2) {
        AllFragment fragment = new AllFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public AllFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all, container, false);
        initRecyclerView(view);
        loadUserList();

        view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddNewUserActivity.class);
                startActivityForResult(intent,ADD_USER_REQUEST_CODE);
            }
        });

        return view;
    }
    private void initRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        userListAdapter = new UserListAdapter(getContext(),this);
        recyclerView.setAdapter(userListAdapter);
        loadUserList();
    }

    private void loadUserList() {
        AppDatabase db = AppDatabase.getINSTANCE(getContext());
        userList =db.userDao().getAllUsers();
        Collections.sort(userList);
        userListAdapter.setUserList(userList);
    }
    public static void updateUserList(Context context){
        if(userListAdapter != null){
            AppDatabase db = AppDatabase.getINSTANCE(context);
            userList =db.userDao().getAllUsers();
            Collections.sort(userList);
            userListAdapter.setUserList(userList);
        }
    }
    public static void updateUserList(){
        if(userListAdapter != null){
            Collections.sort(userList);
            userListAdapter.setUserList(userList);
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getContext(), ContactDetailActivity.class);
        intent.putExtra("USER",userList.get(position));
        FavouritesFragment.setFavQueue(userList.get(position));
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==ADD_USER_REQUEST_CODE && data != null)
        {
            String message=data.getStringExtra("OUTPUT");

            if(message != null && message.equals("SUCESS")){
                loadUserList();
            }
        } else {
            Toast.makeText(getContext(), "User not added", Toast.LENGTH_SHORT).show();
        }
    }
}
