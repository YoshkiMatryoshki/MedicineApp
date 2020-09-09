package com.example.medicineapp.fragments;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class MedInfoAdapter extends RecyclerView.Adapter<MedInfoAdapter.MedViewHolder> {
    public class MedViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout containerView;
        TextView pillInfo;
        TextView countInfo;
        EditText takeTime;

        MedViewHolder (View view){
            super(view);

            containerView = view.findViewById(R.id.recycler_row);
            pillInfo = view.findViewById(R.id.recycler_row_pillInfo);
            countInfo = view.findViewById(R.id.recycler_configrow_countInfo);
            takeTime = view.findViewById(R.id.recycler_row_time);

            //HERE ONCLICK LISTENER
        }
    }

    private List<MedicineTakeInfo> content = new ArrayList<>();


    @NonNull
    @Override
    public MedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_row, parent, false);

        return new MedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedViewHolder holder, int position) {
        MedicineTakeInfo current = content.get(position);
        holder.pillInfo.setText(Integer.toString(current.medicineId));
        holder.countInfo.setText(Integer.toString(current.isTaken));
        holder.takeTime.setText(getFormatDate(current.takeDay));

    }

    @Override
    public int getItemCount() {
        return content.size();
    }


    public void reload(){
        content = MainActivity.database.medicineTakeInfoDAO().getAllInfo();
        notifyDataSetChanged();
    }
    public void addNewInfo(MedicineTakeInfo medicineTakeInfo){
        MainActivity.database.medicineTakeInfoDAO().insertInfo(medicineTakeInfo);
    }

    //CalendarWorks
    private String getFormatDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/HH:mm");

        return sdf.format(date);
    }
}
