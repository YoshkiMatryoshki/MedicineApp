package com.example.medicineapp.fragments.configure;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.medicineapp.R;

import java.util.zip.Inflater;

public class ConfigFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_configure,container, false);
        TextView textView = root.findViewById(R.id.config_textView);
        textView.setText("OmegalulWorking");

        return root;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }
}
