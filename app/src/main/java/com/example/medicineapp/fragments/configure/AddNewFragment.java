package com.example.medicineapp.fragments.configure;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import com.example.medicineapp.R;
import com.example.medicineapp.database.MedicineCourse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class AddNewFragment extends Fragment {

    ConfigViewModel model;
    private Calendar calendar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_configure_addnew, container, false);

        final EditText nameText = root.findViewById(R.id.newcourse_medname);
        final EditText doseText = root.findViewById(R.id.newcourse_doseinfo);
        final EditText startDay = root.findViewById(R.id.newcourse_startday);
        final EditText endDay = root.findViewById(R.id.newcourse_endday);
        final EditText dailyCount = root.findViewById(R.id.newcourse_dailycount);
        calendar = Calendar.getInstance();

        model = ViewModelProviders.of(getActivity()).get(ConfigViewModel.class);

        Button saveButton = root.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set data to ViewModel
                //model.setValue(nameText.getText().toString());

                //fill newCourse fields
                MedicineCourse newCourse = new MedicineCourse();
                newCourse.medName = nameText.getText().toString();
                newCourse.medDose = doseText.getText().toString();

                Date courseStartDate = null;

                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                try {
                    courseStartDate = format.parse(startDay.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                newCourse.startCourse = courseStartDate;
                newCourse.endCourse = courseStartDate;

                newCourse.dayCount = Integer.parseInt(dailyCount.getText().toString());

                model.setValue(newCourse);

                //go back to main config fragment
                NavHostFragment.findNavController(AddNewFragment.this)
                        .navigate(R.id.action_nav_configure_addnew_to_nav_configure);
            }
        });


        return root;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        model.setValue(null);

    }
}
