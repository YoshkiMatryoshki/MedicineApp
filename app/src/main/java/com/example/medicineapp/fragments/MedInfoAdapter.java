package com.example.medicineapp.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicineapp.R;

import java.sql.Time;
import java.util.ArrayList;
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
            countInfo = view.findViewById(R.id.recycler_row_countInfo);
            takeTime = view.findViewById(R.id.recycler_row_time);

            //HERE ONCLICK LISTENER
        }
    }

    private List<String> content = new ArrayList<>();

    @NonNull
    @Override
    public MedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_row, parent, false);

        return new MedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedViewHolder holder, int position) {
        String current = content.get(position);
        holder.pillInfo.setText(current);
        holder.countInfo.setText(Integer.toString(position));
        holder.takeTime.setText(position + ":"+ position+1);

    }

    @Override
    public int getItemCount() {
        return content.size();
    }


    public void reload(){
        content.add("TEST1");
        content.add("TEST2");
        content.add("TEST3");
        notifyDataSetChanged();
    }
}
