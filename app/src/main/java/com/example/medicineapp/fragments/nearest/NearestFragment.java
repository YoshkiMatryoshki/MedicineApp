package com.example.medicineapp.fragments.nearest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicineapp.R;
import com.example.medicineapp.fragments.MedInfoAdapter;


public class NearestFragment extends Fragment {

    private NearestViewModel nearestViewModel;
    private RecyclerView recyclerView;
    private MedInfoAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //nearestViewModel = ViewModelProviders.of(this).get(NearestViewModel.class);
        /*
        nearestViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
            }
        });*/

        View root = inflater.inflate(R.layout.fragment_nearest, container, false);

        recyclerView = root.findViewById(R.id.main_recycler_view);
        layoutManager = new LinearLayoutManager(this.getContext());
        adapter = new MedInfoAdapter();

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        return root;

    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.reload();
    }
}
