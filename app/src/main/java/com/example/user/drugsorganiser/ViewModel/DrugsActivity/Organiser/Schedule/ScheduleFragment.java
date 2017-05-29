package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.Schedule;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.Shared.DosesManagement;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.BaseDrugsActivityFragment;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.DoseAdapter;

import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleFragment extends BaseDrugsActivityFragment {

    private Button refreshBtn;
    private RecyclerView recyclerView;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_schedule, container, false);
        refreshBtn = (Button) v.findViewById(R.id.refreshBtn);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LogTag(), "In on click");
                DosesManagement dm = new DosesManagement(activity());
                Log.i(LogTag(), Arrays.toString(dm.allUsers().toArray()));
            }
        });
        return v;
    }

    @Override
    public void onStart() {
        Log.i(LogTag(), "onStart");
        super.onStart();
        getActivity().setTitle(getView().getResources().getString(R.string.schedule));
        recyclerView = (RecyclerView) getView().findViewById(R.id.doses_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        final DoseAdapter doseAdapter = new DoseAdapter(activity().getUser(), getActivity());
        recyclerView.setAdapter(doseAdapter);
    }

    @Override
    public void onResume() {
        Log.i(LogTag(), "onResume");

        super.onResume();
        final DoseAdapter doseAdapter = new DoseAdapter(activity().getUser(), getActivity());
        recyclerView.setAdapter(doseAdapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(LogTag(), "Schedule fragment on detach");
        getFragmentManager().saveFragmentInstanceState(this);
    }
}
