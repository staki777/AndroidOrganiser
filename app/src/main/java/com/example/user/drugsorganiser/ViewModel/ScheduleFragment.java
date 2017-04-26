package com.example.user.drugsorganiser.ViewModel;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.drugsorganiser.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleFragment extends Fragment {

    private RecyclerView recyclerView;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        recyclerView = (RecyclerView) getView().findViewById(R.id.doses_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        final DoseAdapter doseAdapter = new DoseAdapter(((DrugsActivity)getActivity()).getUser(), getActivity());
        recyclerView.setAdapter(doseAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        final DoseAdapter doseAdapter = new DoseAdapter(((DrugsActivity)getActivity()).getUser(), getActivity());
        recyclerView.setAdapter(doseAdapter);
    }

}
