package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Schedule;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.DoseAdapter;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.DrugsActivity;

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
        getActivity().setTitle(getView().getResources().getString(R.string.schedule));
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
