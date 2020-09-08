package com.example.medicineapp.fragments.configure;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import com.example.medicineapp.R;
import com.example.medicineapp.database.MedicineCourse;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddNewFragment extends Fragment {

    ConfigViewModel model;
    private Button incrementButton;
    private Button decrementButton;
    private Button setTimeButton;

    EditText nameText;
    EditText doseText;
    EditText startDay;
    EditText courseLength;
    EditText dailyCount;

    private List<Date>  medicineTime;
    private Calendar calendar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_configure_addnew, container, false);

        //Fragments inputs
        nameText = root.findViewById(R.id.newcourse_medname);
        doseText = root.findViewById(R.id.newcourse_doseinfo);
        startDay = root.findViewById(R.id.newcourse_startday);
        courseLength = root.findViewById(R.id.newcourse_length);
        dailyCount = root.findViewById(R.id.newcourse_dailycount);

        //buttons
        Button saveButton = root.findViewById(R.id.save_button);
        setTimeButton = root.findViewById(R.id.set_time_button);
        incrementButton = root.findViewById(R.id.dailycount_increment_button);
        decrementButton = root.findViewById(R.id.dailycount_decrement_button);

        calendar = Calendar.getInstance();
        medicineTime = new ArrayList<>();

        model = ViewModelProviders.of(getActivity()).get(ConfigViewModel.class);

        //user can choose from datepicker start day of med course
        setDatePickerToEditText();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //if some fields are missing - msg then drop
                if (!checkInputs()){
                    Snackbar.make(view,"Some fields are missing!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }

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
                calendar.setTime(courseStartDate);
                calendar.add(Calendar.DATE, Integer.parseInt(courseLength.getText().toString()));
                newCourse.endCourse = calendar.getTime();

                newCourse.dayCount = Integer.parseInt(dailyCount.getText().toString());
                //set data to ViewModel
                model.setValue(newCourse);

                //go back to main config fragment
                NavHostFragment.findNavController(AddNewFragment.this)
                        .navigate(R.id.action_nav_configure_addnew_to_nav_configure);
            }
        });
        //Buttons +1 / -1;
        setButtonOnClicks();


        return root;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        model.setValue(null);

    }

    //Check if all fields of fragment filled and fit requirements
    private boolean checkInputs(){
        return !nameText.getText().toString().equals("")
                && !doseText.getText().toString().equals("")
                && !startDay.getText().toString().equals("")
                && !courseLength.getText().toString().equals("")
                && (Integer.parseInt(dailyCount.getText().toString()) > 0);
    }


    //Mess with button clicks for inc/dec edit field
    //and pick time for medTake
    private void setButtonOnClicks() {
        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                calendar.set(Calendar.HOUR_OF_DAY,i);
                calendar.set(Calendar.MINUTE,i1);
                medicineTime.add(calendar.getTime());
                dailyCount.setText(Integer.toString(medicineTime.size()));
            }
        };

        incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //int current_count = Integer.parseInt(dailyCount.getText().toString()) + 1;
                //dailyCount.setText(Integer.toString(current_count));
                new TimePickerDialog(getContext(),time,calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),true).show();
            }
        });

        decrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int current_count = Integer.parseInt(dailyCount.getText().toString());
                if (current_count > 0)
                {
                    current_count--;
                    dailyCount.setText(Integer.toString(current_count));
                    if (medicineTime.size() > 0){
                        medicineTime.remove(medicineTime.size()-1);
                    }
                }
            }
        });
    }
    private void setDatePickerToEditText() {
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.YEAR,i);
                calendar.set(Calendar.MONTH,i1);
                calendar.set(Calendar.DAY_OF_MONTH,i2);
                updateText();
            }
        };
        startDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), date, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    //Set picked date into edittext
    private void updateText(){
        String myFormat = "dd.MM.yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        startDay.setText(sdf.format(calendar.getTime()));
    }
}
