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

import com.example.medicineapp.R;


public class NearestFragment extends Fragment {

    private NearestViewModel nearestViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        nearestViewModel = ViewModelProviders.of(this).get(NearestViewModel.class);

        View root = inflater.inflate(R.layout.fragment_nearest, container, false);
        final TextView textView = root.findViewById(R.id.nearest_textView);

        nearestViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textView.setText(s);
            }
        });


        return root;

    }
}
