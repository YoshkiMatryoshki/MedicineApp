package com.example.medicineapp.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicineapp.MainActivity;
import com.example.medicineapp.R;
import com.example.medicineapp.database.MedicineCourse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class MedConfigAdapter extends RecyclerView.Adapter<MedConfigAdapter.MedConfigViewHolder> {
    public class MedConfigViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout containerView;
        TextView pillInfo;
        TextView countInfo;
        TextView dayCount;
        TextView startEndDate;

        MedConfigViewHolder(View view){
            super(view);

            pillInfo = view.findViewById(R.id.recycler_configrow_pillInfo);
            countInfo = view.findViewById(R.id.recycler_configrow_countInfo);
            dayCount = view.findViewById(R.id.recycler_configrow_daycount);
            startEndDate = view.findViewById(R.id.recycler_configrow_startend);
        }

    }

    //private List<String> content = new ArrayList<>();
    private List<MedicineCourse> content = new ArrayList<>();

    @NonNull
    @Override
    public MedConfigViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_config_row, parent, false);
        return new MedConfigViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MedConfigViewHolder holder, int position) {
        MedicineCourse current = content.get(position);
        holder.pillInfo.setText(current.medName);
        holder.countInfo.setText(current.medDose);
        holder.dayCount.setText(String.format("%d",current.dayCount));
        String resultStart = getFormatDate(current.startCourse);
        String resultEnd = getFormatDate(current.endCourse);
        holder.startEndDate.setText(resultStart + " — " + resultEnd);

    }
    @Override
    public int getItemCount() {
        return content.size();
    }

    public void reload(){
        content = MainActivity.database.medicineCourseDAO().getAllCourses();
        notifyDataSetChanged();
    }
    public void addNewRecord(MedicineCourse newCourse){
        MainActivity.database.medicineCourseDAO().insertCourse(newCourse);
    }


    //CalendarWorks
    private String getFormatDate(Date date){
        String res = "DateError";
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1; //month return values starting from 0!!!
        int year = calendar.get(Calendar.YEAR);

        res = String.format("%02d.%02d.%d",day,month,year);

        return res;
    }
}
