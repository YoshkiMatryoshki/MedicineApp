package com.example.medicineapp.fragments.configure;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicineapp.R;
import com.example.medicineapp.fragments.MedInfoAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.zip.Inflater;

public class ConfigFragment extends Fragment {

    private RecyclerView recyclerView;
    private MedInfoAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_configure,container, false);

        recyclerView = root.findViewById(R.id.main_recycler_view);
        layoutManager = new LinearLayoutManager(this.getContext());
        adapter = new MedInfoAdapter();

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        //Button
        FloatingActionButton configFAB = root.findViewById(R.id.config_fab);
        configFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ConfigFragment.this)
                        .navigate(R.id.action_nav_configure_to_nav_configure_addnew);

                /*
                Snackbar.make(view, "ITS WORKING!", Snackbar.LENGTH_LONG)
                        .setAction("Action",null).show();*/
            }
        });


        //get liveData from model
        ConfigViewModel model = ViewModelProviders.of(getActivity()).get(ConfigViewModel.class);
        model.getValue().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                String test = s;

            }
        });


        return root;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.reload();
    }
}
