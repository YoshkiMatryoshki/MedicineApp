package com.example.medicineapp.fragments.configure;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicineapp.MainActivity;
import com.example.medicineapp.R;
import com.example.medicineapp.database.MedCoursePacked;
import com.example.medicineapp.database.MedicineCourse;
import com.example.medicineapp.database.MedicineTakeToUser;
import com.example.medicineapp.fragments.MedConfigAdapter;
import com.example.medicineapp.fragments.MedInfoAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class ConfigFragment extends Fragment {

    private RecyclerView recyclerView;
    private MedConfigAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ConfigViewModel model;

    private Observer<MedCoursePacked> observer = new Observer<MedCoursePacked>() {
        @Override
        public void onChanged(MedCoursePacked medCoursePacked) {
            if (medCoursePacked != null){
                adapter.addNewRecord(medCoursePacked);
                //CREATE NOTIFICATIONS FOR RECENTLY ADDED RECORDS!
                MainActivity xd = (MainActivity)getActivity();
                assert xd != null;
                xd.CreateNotificationsById(medCoursePacked.medName);
            }
        }
    };;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_configure,container, false);


        recyclerView = root.findViewById(R.id.main_recycler_view);
        layoutManager = new LinearLayoutManager(this.getContext());
        adapter = new MedConfigAdapter();

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


        //Button
        FloatingActionButton configFAB = root.findViewById(R.id.config_fab);
        configFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ConfigFragment.this)
                        .navigate(R.id.action_nav_configure_to_nav_configure_addnew);

            }
        });

        model = ViewModelProviders.of(getActivity()).get(ConfigViewModel.class);
        //get liveData from model
        model.getValue().observe(getViewLifecycleOwner(), observer);


        return root;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        model.getValue().removeObserver(observer);
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.reload();
    }


    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getLayoutPosition();
            //BuildAlertMessage
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Confirm");
            builder.setMessage("Delete all records from this medicine course?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    List<MedicineTakeToUser> notToDel =  adapter.deleteAllEntries(position);
                    List<Integer> updateToJava8Stpd = new ArrayList<>();
                    for (MedicineTakeToUser el : notToDel){
                        updateToJava8Stpd.add(el.id);
                    }

                    MainActivity mainActivity = (MainActivity)getActivity();
                    assert mainActivity != null;
                    mainActivity.DeleteNotificationsById(updateToJava8Stpd);
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            });
            AlertDialog alertMessage = builder.create();
            alertMessage.show();
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getContext(),R.color.swipeDelete))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };
}
