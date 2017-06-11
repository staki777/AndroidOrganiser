package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.Registry;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.BaseDrugsActivityFragment;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.RegistryAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistryFragment extends BaseDrugsActivityFragment {

    private RecyclerView recyclerView;

    public RegistryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_schedule, container, false);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle(getView().getResources().getString(R.string.archive));
        recyclerView = (RecyclerView) getView().findViewById(R.id.doses_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        final RegistryAdapter registryAdapter = new RegistryAdapter(getActivity());
        recyclerView.setAdapter(registryAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        final RegistryAdapter registryAdapter = new RegistryAdapter(getActivity());
        recyclerView.setAdapter(registryAdapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("RegistryFragment", "Registry fragment on detach");
        getFragmentManager().saveFragmentInstanceState(this);
    }

}
