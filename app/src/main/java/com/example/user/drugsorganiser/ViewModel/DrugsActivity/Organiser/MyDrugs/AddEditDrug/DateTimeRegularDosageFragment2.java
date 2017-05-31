package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.MyDrugs.AddEditDrug;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.user.drugsorganiser.Model.Drug;
import com.example.user.drugsorganiser.Model.RegularDose;
import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.BaseDrugsActivityFragment;

import java.lang.reflect.Field;
import java.util.Locale;

/**
 * Created by Gosia on 29.05.2017.
 */

public class DateTimeRegularDosageFragment2 extends BaseDrugsActivityFragment implements View.OnClickListener {

    TimePicker tp;
    DatePicker dp;
    Button positiveBtn,negativeBtn;
    public void DateTimeRegularDosageFragment1(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Locale locale = getResources().getConfiguration().locale;
        Locale.setDefault(locale);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_new_date_dialog2, container, false);
        positiveBtn = (Button) v.findViewById(R.id.positive_button2);
        negativeBtn = (Button) v.findViewById(R.id.negative_button2);
        positiveBtn.setOnClickListener(this);
        negativeBtn.setOnClickListener(this);
        tp = (TimePicker)v.findViewById(R.id.timePicker2);
        dp = (DatePicker)v.findViewById(R.id.datePicker2);
        int year2    = dp.getYear();
        int month2   = dp.getMonth();
        int day2     = dp.getDayOfMonth();

        dp.init(year2, month2, day2, new DatePicker.OnDateChangedListener(){
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int month_i = monthOfYear + 1;
                Log.e("selected month:", Integer.toString(month_i));
                //Add whatever you need to handle Date changes
            }
        });

        try {
            Field f[] = dp.getClass().getDeclaredFields();
            for (Field field : f) {
                if (field.getName().equals("mYearPicker")|| field.getName().equals("mYearSpinner")) {
                    field.setAccessible(true);
                    Object yearPicker = new Object();
                    yearPicker = field.get(dp);
                    ((View) yearPicker).setVisibility(View.GONE);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        try {
            Field f[] = dp.getClass().getDeclaredFields();
            for (Field field : f) {
                if (field.getName().equals("mMonthPicker")|| field.getName().equals("mMonthSpinner")) {
                    field.setAccessible(true);
                    Object monthPicker = new Object();
                    monthPicker = field.get(dp);
                    ((View) monthPicker).setVisibility(View.GONE);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        };
        getActivity().setTitle("Adding new term");
        return v;
    }

    @Override
    public void onClick(View v) {
        if(v == positiveBtn){
            Drug editedDrug = activity().getEditedDrug();
            TermAdapterRegular termAdapter = new TermAdapterRegular(editedDrug, getActivity());

            termAdapter.addItem(new RegularDose(editedDrug, "month", tp.getCurrentMinute(), tp.getCurrentHour(), -1, dp.getDayOfMonth(), -1));
            activity().onBackPressed();
        }
        else if(v == negativeBtn){
            activity().onBackPressed();
        }
    }
}
