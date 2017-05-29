package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.Registry;


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
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.RegistryAdapter;

import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistryFragment extends BaseDrugsActivityFragment {

    private RecyclerView recyclerView;
    private Button refreshBtn; //Only temporary

    public RegistryFragment() {
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
                Log.i(LogTag(), Arrays.toString(dm.findCustomDosesForNext24h(activity().getUser()).toArray()));
            }
        });
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle(getView().getResources().getString(R.string.archive));
        recyclerView = (RecyclerView) getView().findViewById(R.id.doses_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        final RegistryAdapter registryAdapter = new RegistryAdapter(activity().getUser(), getActivity());
        recyclerView.setAdapter(registryAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        final RegistryAdapter registryAdapter = new RegistryAdapter(activity().getUser(), getActivity());
        recyclerView.setAdapter(registryAdapter);

    }



}
