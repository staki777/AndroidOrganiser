package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.MyDrugs.AddEditDrug;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.drugsorganiser.R;


public class ConstantIntervalFragment extends Fragment {

    public ConstantIntervalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating layout
        View v = inflater.inflate(R.layout.fragment_constant_interval, container, false);
        // We obtain layout references

        return v;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Button reset=(Button)findViewById(R.id.reset);

    }
}
