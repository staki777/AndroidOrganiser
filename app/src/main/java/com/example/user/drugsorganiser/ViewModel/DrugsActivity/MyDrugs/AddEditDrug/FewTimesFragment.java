package com.example.user.drugsorganiser.ViewModel.DrugsActivity.MyDrugs.AddEditDrug;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.user.drugsorganiser.R;

import java.lang.reflect.Field;
import java.text.DateFormat;


public class FewTimesFragment extends Fragment {

    private NumberPicker FewTimesPicker;
    private Spinner spFewTimesType;
    private int typeofAddDate=0;

    public FewTimesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating layout
        View v = inflater.inflate(R.layout.fragment_few_times, container, false);
        // We obtain layout references

        return v;

    }
    @Override
    public void onStart() {
        super.onStart();

        FewTimesPicker = (NumberPicker) getView().findViewById(R.id.FewTimesPicker);
        FewTimesPicker.setMinValue(1);
        FewTimesPicker.setMaxValue(250);
        FewTimesPicker.setValue(1);

        spFewTimesType = (Spinner) getView().findViewById(R.id.FewTimes_type_spinner);

        ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, getActivity().getResources().getStringArray(R.array.time_array));
        spFewTimesType.setAdapter(timeAdapter);
        spFewTimesType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                typeofAddDate = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //nothing
            }

        });

        TextView tvAdd = (TextView) getView().findViewById(R.id.add);
        tvAdd.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setTitle("Add new date");
                alertDialogBuilder.setPositiveButton(R.string.positive_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                    }
                });
                alertDialogBuilder.setNegativeButton(R.string.negative_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                final AlertDialog alertDialog;
                switch (typeofAddDate) {
                    case 0:
                        LayoutInflater li = LayoutInflater.from(getActivity());
                        View promptsView = li.inflate(R.layout.add_new_date_dialog0, null);
                        alertDialogBuilder.setView(promptsView);
                        alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                        alertDialog.setCanceledOnTouchOutside(false);
                        break;
                    case 1:
                        li = LayoutInflater.from(getActivity());
                        promptsView = li.inflate(R.layout.add_new_date_dialog1, null);                       ;
                        alertDialogBuilder.setView(promptsView);
                        alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                        alertDialog.setCanceledOnTouchOutside(false);
                        NumberPicker picker =  (NumberPicker)promptsView.findViewById(R.id.weekPicker);
                        if(picker!=null){
                        picker.setMinValue(1);
                        picker.setMaxValue(7);
                        picker.setDisplayedValues( new String[] { "Monday" , "Tuesday", "Wednesday", "Thursday",
                                "Friday", "Saturday", "Sunday"});
                        picker.setValue(1);}


                        break;
                    case 2:
                        li = LayoutInflater.from(getActivity());
                        promptsView = li.inflate(R.layout.add_new_date_dialog2, null);
                        DatePicker pd= (DatePicker) promptsView.findViewById(R.id.datePicker2);
                        int year    = pd.getYear();
                        int month   = pd.getMonth();
                        int day     = pd.getDayOfMonth();

                        pd.init(year, month, day, new DatePicker.OnDateChangedListener(){
                            @Override
                            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                               int month_i = monthOfYear + 1;
                                Log.e("selected month:", Integer.toString(month_i));
                                //Add whatever you need to handle Date changes
                            }
                        });

                        try {
                            Field f[] = pd.getClass().getDeclaredFields();
                            for (Field field : f) {
                                if (field.getName().equals("mYearPicker")|| field.getName().equals("mYearSpinner")) {
                                    field.setAccessible(true);
                                    Object yearPicker = new Object();
                                    yearPicker = field.get(pd);
                                    ((View) yearPicker).setVisibility(View.GONE);
                                }
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }

                                              ;
                        alertDialogBuilder.setView(promptsView);
                        alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                        alertDialog.setCanceledOnTouchOutside(false);
                        break;
                    case 3:
                        li = LayoutInflater.from(getActivity());
                        promptsView = li.inflate(R.layout.add_new_date_dialog3, null);                       ;
                        alertDialogBuilder.setView(promptsView);
                        /*picker =  (NumberPicker)getView().findViewById(R.id.yearPicker);
                        picker.setMinValue(2017);
                        picker.setMaxValue(2100);
                        picker.setValue(2017);*/
                        alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                        alertDialog.setCanceledOnTouchOutside(false);
                        break;
                        }

            }


        });

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Button reset=(Button)findViewById(R.id.reset);

    }


}
