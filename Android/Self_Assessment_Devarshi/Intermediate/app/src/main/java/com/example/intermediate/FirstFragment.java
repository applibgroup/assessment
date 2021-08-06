package com.example.intermediate;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class FirstFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ToDoAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private final ArrayList<String>[]idsList=new ArrayList[]{new ArrayList<String>(1)};
    private final ArrayList<ToDo>[]arrayList=new ArrayList[]{new ArrayList<ToDo>(1)};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_first, container, false);

        mLayoutManager = new LinearLayoutManager(super.getContext());
        arrayList[0].clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("todos")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            Log.d("TAG", document.getId() + " => " + document.getData());
                            ToDo t = new ToDo(document.getString("task"), document.getString("detail"), document.getString("datetime"), document.getString("type"));
                            arrayList[0].add(t);
                            idsList[0].add(document.getId());
                        }
                        mRecyclerView = root.findViewById(R.id.recyclerview);
                        mRecyclerView.setHasFixedSize(true);
                        mAdapter = new ToDoAdapter(arrayList[0]);
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mRecyclerView.setAdapter(mAdapter);
                        mAdapter.setOnItemClickListener(position -> {
                            SecondFragment someFragment = new SecondFragment();
                            if(isAdded()){
                                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
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

        view.findViewById(R.id.button_first).setOnClickListener(view1 -> NavHostFragment.findNavController(FirstFragment.this)
                .navigate(R.id.action_FirstFragment_to_SecondFragment));

    }
}
