package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.Preview;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.drugsorganiser.Model.Drug;
import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.Shared.UniversalMethods;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.BaseDrugsActivityFragment;

/**
 * Created by Gosia on 31.05.2017.
 */

public class IntervalPrevFragment extends BaseDrugsActivityFragment {

    private Drug myDrug;

    public IntervalPrevFragment(){

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.interval_prev, container, false);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        myDrug = activity().getEditedDrug();

        if(myDrug == null) return;
        Log.i(LogTag(), "onStartIntervalPrevFragment");
        TextView startDose = (TextView) getView().findViewById(R.id.item_date);
        TextView interval = (TextView) getView().findViewById(R.id.tv_intervalNumberPicker);
        TextView unit = (TextView) getView().findViewById(R.id.tv_intervalUnitSpinner);

        startDose.setText(UniversalMethods.DateTimeToString(myDrug.constantIntervalDose.firstDose));
        interval.setText(translateInterval(myDrug.constantIntervalDose.interval)[0]);
        unit.setText(translateInterval(myDrug.constantIntervalDose.interval)[1]);
    }

    private String[] translateInterval(int interval) {
        String[] time_unit = getResources().getStringArray(R.array.time_units_array);;
        int[] multiplicators = {1, 60, 60 * 24, 60 * 24 * 30, 60 * 24 * 30 * 256};
        String[] result = new String[2];
        for (int i = multiplicators.length - 1; i > 0; i--) {
            if (interval % multiplicators[i] == 0) {
                result[0] = Integer.toString(interval / multiplicators[i]);
                result[1] = time_unit[i];
            }
        }
        return result;
    }
}
