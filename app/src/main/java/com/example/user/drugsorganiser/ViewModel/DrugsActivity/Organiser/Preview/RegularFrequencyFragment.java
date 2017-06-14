package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.Preview;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.drugsorganiser.Model.Drug;
import com.example.user.drugsorganiser.Model.RegularDose;
import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.BaseDrugsActivityFragment;

import java.util.ArrayList;

/**
 * Created by Gosia on 14.06.2017.
 */

public class RegularFrequencyFragment extends BaseDrugsActivityFragment {

    private Drug myDrug;
    private int pos;

    public RegularFrequencyFragment() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.regular_freq, container, false);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        myDrug = activity().getEditedDrug();
        if(myDrug == null)
            return;
        Log.i(LogTag(), "RegularFrequencyFragment");
        String[] t_array = getResources().getStringArray(R.array.time_array);
        TextView freq = (TextView) getView().findViewById(R.id.tv_FewTimesPer_unit);
        ArrayList<RegularDose> list = new ArrayList<>();
        list.addAll(myDrug.regularDoses);
        freq.setText(t_array[list.get(0).interval]);
    }
}
