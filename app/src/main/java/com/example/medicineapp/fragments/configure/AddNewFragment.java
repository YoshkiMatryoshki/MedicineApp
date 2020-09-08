package com.example.medicineapp.fragments.configure;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import com.example.medicineapp.R;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class AddNewFragment extends Fragment {

    ConfigViewModel model;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_configure_addnew, container, false);

        final EditText nameText = root.findViewById(R.id.editTextTextPersonName2);

        model = ViewModelProviders.of(getActivity()).get(ConfigViewModel.class);

        Button saveButton = root.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set data to ViewModel
                model.setValue(nameText.getText().toString());

                Calendar calendar = Calendar.getInstance();
                Date test = calendar.getTime();
                int hour = calendar.get(Calendar.HOUR);
                int hour24 = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                calendar.add(Calendar.DATE, 30);
                Date test1 = calendar.getTime();



                //go back to main config fragment
                NavHostFragment.findNavController(AddNewFragment.this)
                        .navigate(R.id.action_nav_configure_addnew_to_nav_configure);
            }
        });


        return root;
        //return super.onCreateView(inflater, container, savedInstanceState);

    }
}
