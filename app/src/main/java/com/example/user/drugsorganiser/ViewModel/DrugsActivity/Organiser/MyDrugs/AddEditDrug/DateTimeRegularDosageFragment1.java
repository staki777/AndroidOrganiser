package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.MyDrugs.AddEditDrug;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import com.example.user.drugsorganiser.Model.Drug;
import com.example.user.drugsorganiser.Model.RegularDose;
import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.BaseDrugsActivityFragment;

/**
 * Created by Gosia on 29.05.2017.
 */

public class DateTimeRegularDosageFragment1 extends BaseDrugsActivityFragment implements View.OnClickListener{

    TimePicker tp;
    NumberPicker picker;
    Button positiveBtn,negativeBtn;
    private String[] daysOfWeek;


    public void DateTimeRegularDosageFragment1(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_new_date_dialog1, container, false);
        positiveBtn = (Button) v.findViewById(R.id.positive_button1);
        negativeBtn = (Button) v.findViewById(R.id.negative_button1);
        positiveBtn.setOnClickListener(this);
        negativeBtn.setOnClickListener(this);
        tp = (TimePicker)v.findViewById(R.id.timePicker1);
        picker = (NumberPicker) v.findViewById(R.id.weekPicker);
        daysOfWeek = new String[] { "Monday" , "Tuesday", "Wednesday", "Thursday",
                "Friday", "Saturday", "Sunday"};
        if (picker != null) {
            picker.setMinValue(1);
            picker.setMaxValue(7);
            picker.setDisplayedValues(daysOfWeek);

            picker.setValue(1);
        }
        getActivity().setTitle(v.getResources().getString(R.string.adding_new_term));
        return v;
    }
    @Override
    public void onClick(View v) {
        if(v == positiveBtn){
            Drug editedDrug = activity().getEditedDrug();
            TermAdapterRegular termAdapter = new TermAdapterRegular(editedDrug, getActivity());

            termAdapter.addItem(new RegularDose(editedDrug, "week",tp.getCurrentMinute(),tp.getCurrentHour(),picker.getValue(),-1,-1));
            activity().onBackPressed();

        }
        else if(v == negativeBtn){
            activity().onBackPressed();
        }
    }
}
