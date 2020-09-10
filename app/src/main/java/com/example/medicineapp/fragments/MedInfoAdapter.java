package com.example.medicineapp.fragments;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicineapp.MainActivity;
import com.example.medicineapp.R;
import com.example.medicineapp.database.MedicineTakeInfo;
import com.example.medicineapp.database.MedicineTakeToUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MedInfoAdapter extends RecyclerView.Adapter<MedInfoAdapter.MedViewHolder> {
    public class MedViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout containerView;
        TextView pillInfo;
        TextView countInfo;
        EditText takeTime;
        TextView takeDate;

        MedViewHolder (View view){
            super(view);

            containerView = view.findViewById(R.id.recycler_row);
            pillInfo = view.findViewById(R.id.recycler_row_pillInfo);
            countInfo = view.findViewById(R.id.recycler_configrow_countInfo);
            takeTime = view.findViewById(R.id.recycler_row_time);
            takeDate = view.findViewById(R.id.recycler_row_date);

            //HERE ONCLICK LISTENER
        }
    }

    private List<MedicineTakeToUser> content = new ArrayList<>();
    private int outdatedRecordsStartPos = 0;
    //private  List<MedicineTakeToUser> tempList = new ArrayList<>();
    //private List<MedicineTakeInfo> TEST = new ArrayList<>();


    @NonNull
    @Override
    public MedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_row, parent, false);

        return new MedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedViewHolder holder, int position) {
        MedicineTakeToUser current = content.get(position);
        holder.pillInfo.setText(current.medName);
        holder.countInfo.setText(current.medDose);
        holder.takeTime.setText(getFormatTime(current.takeDay));
        holder.takeDate.setText(getFormatDate(current.takeDay));
        if (position >= outdatedRecordsStartPos){
            holder.takeTime.setTextColor(Color.RED);
        }

    }

    @Override
    public int getItemCount() {
        return content.size();
    }


    public void reload(){
        Calendar checkCalendar = Calendar.getInstance();
        Calendar currentCalendar = Calendar.getInstance();
        checkCalendar.add(Calendar.HOUR_OF_DAY, 1);
        Date checkDate = checkCalendar.getTime();

        content = MainActivity.database.medicineTakeInfoDAO().getUserDataRepresentation(checkDate.getTime());
        for (int i = 0; i < content.size(); i++){
            if (content.get(i).takeDay.before(currentCalendar.getTime())){
                outdatedRecordsStartPos = i;
                break;
            }
        }

        notifyDataSetChanged();
    }
    public void updateRecordStatus(int swipeResult, int position){
        MedicineTakeToUser swipedElement = content.get(position);
        MainActivity.database.medicineTakeInfoDAO().updateTakeInfo((swipeResult == 4)?-1:1,swipedElement.id);
        content.remove(position);
        notifyItemRemoved(position);

    }


    //CalendarWorks
    private String getFormatDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");

        return sdf.format(date);
    }
    private String getFormatTime(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        return sdf.format(date);
    }
}
