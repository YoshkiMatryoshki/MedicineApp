package com.example.medicineapp.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicineapp.R;

import java.util.ArrayList;
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

    private List<String> content = new ArrayList<>();

    @NonNull
    @Override
    public MedConfigViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_config_row, parent, false);
        return new MedConfigViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MedConfigViewHolder holder, int position) {
        String current = content.get(position);
        holder.pillInfo.setText(current);
        holder.countInfo.setText(Integer.toString(position));
        holder.dayCount.setText("3");
        holder.startEndDate.setText("08.09.20 - 18.09.20");

    }
    @Override
    public int getItemCount() {
        return content.size();
    }

    public void reload(){
        notifyDataSetChanged();
    }
    public void addNewRecord(String name){
        content.add(name);
    }
}
