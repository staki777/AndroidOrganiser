package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.MyDrugs.AddEditDrug;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import com.example.user.drugsorganiser.Model.Drug;
import com.example.user.drugsorganiser.Model.RegularDose;
import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.BaseDrugsActivityFragment;

/**
 * Created by Gosia on 29.05.2017.
 */

public class DateTimeRegularDosageFragment0 extends BaseDrugsActivityFragment implements View.OnClickListener {

    TimePicker tp;
    Button positiveBtn,negativeBtn;
    public void DateTimeRegularDosageFragment1(){

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_new_date_dialog0, container, false);
        positiveBtn = (Button) v.findViewById(R.id.positive_button0);
        negativeBtn = (Button) v.findViewById(R.id.negative_button0);
        positiveBtn.setOnClickListener(this);
        negativeBtn.setOnClickListener(this);
        tp = (TimePicker)v.findViewById(R.id.timePicker0);
        getActivity().setTitle(v.getResources().getString(R.string.adding_new_term));
        return v;
    }

    @Override
    public void onClick(View v) {
        if(v == positiveBtn){
            Drug editedDrug = activity().getEditedDrug();
            TermAdapterRegular termAdapter = new TermAdapterRegular(editedDrug, getActivity());
            termAdapter.addItem(new RegularDose(editedDrug, 0,tp.getCurrentMinute(),tp.getCurrentHour(),-1,-1,-1));
            activity().onBackPressed();

        }
        else if(v == negativeBtn){
            activity().onBackPressed();
        }
    }
}
