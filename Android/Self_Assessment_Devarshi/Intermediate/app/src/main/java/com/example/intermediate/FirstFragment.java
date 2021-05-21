package com.example.intermediate;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intermediate.TransportViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;

public class FirstFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private TripAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TransportViewModel transportViewModel;
    private ArrayList<String>[]idsList=new ArrayList[]{new ArrayList<ToDo>(1)};
    private ArrayList<ToDo>[]arrayList=new ArrayList[]{new ArrayList<ToDo>(1)};
    private ArrayList<ToDo>[] triplist = new ArrayList[]{new ArrayList<ArrayList<String>>(1)};
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        transportViewModel =
                ViewModelProviders.of(this).get(TransportViewModel.class);
        View root = inflater.inflate(R.layout.fragment_first, container, false);

        mLayoutManager = new LinearLayoutManager(super.getContext());
        arrayList[0].clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("todos")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("TAG", document.getId() + " => " + document.getData());
                            ToDo t = new ToDo(document.getString("task"), document.getString("detail"), document.getString("datetime"), document.getString("type"));
                            arrayList[0].add(t);
                            idsList[0].add(document.getId());
                        }
                        mRecyclerView = root.findViewById(R.id.recyclerview);
                        mRecyclerView.setHasFixedSize(true);
                        mAdapter = new TripAdapter(arrayList[0]);
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mRecyclerView.setAdapter(mAdapter);
                        mAdapter.setOnItemClickListener(new TripAdapter.OnItemClickListener() {
                            @Override
                            public void onDetailsClick(int position) {
                                SecondFragment someFragment = new SecondFragment();
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                someFragment.setToDo(arrayList[0].get(position),idsList[0].get(position));
                                transaction.replace(R.id.nav_host_fragment, someFragment); // give your fragment container id in first parameter
                                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                                transaction.commit();


                            }
                        });
                    }});
        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

    }
}
