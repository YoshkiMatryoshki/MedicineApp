package com.example.medicineapp.fragments;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicineapp.MainActivity;
import com.example.medicineapp.R;
import com.example.medicineapp.database.MedCoursePacked;
import com.example.medicineapp.database.MedicineCourse;
import com.example.medicineapp.database.MedicineTakeInfo;
import com.example.medicineapp.database.MedicineTakeToUser;

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

    //Deprec
    public void addNewRecord(MedicineCourse newCourse){
        MainActivity.database.medicineCourseDAO().insertCourse(newCourse);
    }
    public void addNewRecord(MedCoursePacked newCoursePacked){

        if (MainActivity.database.medicineCourseDAO().getUniqCourse(newCoursePacked.medName).size() != 0){
            Log.e("DB_Err","Medicine with this name already exist in DB. Name must be unique");
            return;
        }

        MedicineCourse newCourse = new MedicineCourse();
        newCourse.medName = newCoursePacked.medName;
        newCourse.medDose = newCoursePacked.medDose;
        newCourse.dayCount = newCoursePacked.dayCount;
        newCourse.startCourse = newCoursePacked.startCourse;
        newCourse.endCourse = newCoursePacked.endCourse;

        MainActivity.database.medicineCourseDAO().insertCourse(newCourse);
        int course_id = MainActivity.database.medicineCourseDAO().getUniqCourse(newCourse.medName).get(0).id;

        MedicineTakeInfo medInfo = new MedicineTakeInfo();
        medInfo.medicineId = course_id;
        medInfo.isTaken = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(newCoursePacked.startCourse);
        for (int i = 0; i <= newCoursePacked.courseLength; i++){
            calendar.add(Calendar.DAY_OF_MONTH,(i==0)?0:1);
            Calendar timeCalendar = new GregorianCalendar();
            for(Date takeTime : newCoursePacked.medicineTIme){
                timeCalendar.setTime(takeTime);
                calendar.set(Calendar.HOUR_OF_DAY, timeCalendar.get(Calendar.HOUR_OF_DAY));
                calendar.set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE));
                medInfo.takeDay = calendar.getTime();

                //WRITEINTOTABLE
                MainActivity.database.medicineTakeInfoDAO().insertInfo(medInfo);
            }
        }

    }
    //delete from BOTH TABLES!!!
    public void deleteAllEntries(int position){
        MedicineCourse swipedElement = content.get(position);
        //delete from courseTable
        MainActivity.database.medicineCourseDAO().deleteRecord(swipedElement.id);
        //delete all related from infoTable
        MainActivity.database.medicineTakeInfoDAO().deleteAllCourse(swipedElement.id);
        content.remove(position);
        notifyItemRemoved(position);

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
