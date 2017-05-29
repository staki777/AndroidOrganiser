package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.MyDrugs.AddEditDrug;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.TimePicker;

import com.example.user.drugsorganiser.Model.Drug;
import com.example.user.drugsorganiser.Model.RegularDose;
import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.BaseDrugsActivityFragment;

import java.lang.reflect.Field;


public class RegularDosageFragment extends BaseDrugsActivityFragment implements View.OnClickListener {

    private Spinner spFewTimesType;
    public int typeofAddDate = 0;
    private int hour, minute;
    private String week;
    private String[] daysOfWeek;
    private int weekN;
    private int day, month;
    private Button addNewTermBtn;
    private RecyclerView recyclerView;
    private String type;

    public RegularDosageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        addNewTermBtn = (Button) getView().findViewById(R.id.add);
        addNewTermBtn.setOnClickListener(this);
        recyclerView = (RecyclerView) getView().findViewById(R.id.term_list_regular);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity().getApplicationContext()));
        TermAdapterRegular termAdapter = new TermAdapterRegular(activity().getEditedDrug(), getActivity());
        recyclerView.setAdapter(termAdapter);
    }

    @Override
    public void onClick(View v) {
        if (v == addNewTermBtn) {
            Log.i(LogTag(), "add button clicked");
            switch (typeofAddDate) {
                case 0:
                    activity().removeIfExists(DateTimeRegularDosageFragment0.class.getSimpleName());
                    activity().replaceWithNew(R.id.toReplace, new DateTimeRegularDosageFragment0(), true);
                    break;
                case 1:
                    activity().removeIfExists(DateTimeRegularDosageFragment1.class.getSimpleName());
                    activity().replaceWithNew(R.id.toReplace, new DateTimeRegularDosageFragment1(), true);
                    break;
                case 2:
                    activity().removeIfExists(DateTimeRegularDosageFragment2.class.getSimpleName());
                    activity().replaceWithNew(R.id.toReplace, new DateTimeRegularDosageFragment2(), true);
                    break;
                case 3:
                    activity().removeIfExists(DateTimeRegularDosageFragment3.class.getSimpleName());
                    activity().replaceWithNew(R.id.toReplace, new DateTimeRegularDosageFragment3(), true);
                    break;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating layout
        View v = inflater.inflate(R.layout.fragment_regular_dosage, container, false);
        // We obtain layout references

        daysOfWeek = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday",
                "Friday", "Saturday", "Sunday"};

        spFewTimesType = (Spinner) v.findViewById(R.id.FewTimes_type_spinner);

        ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(activity(), R.layout.spinner_item, activity().getResources().getStringArray(R.array.time_array));
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

        return v;
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Button reset=(Button)findViewById(R.id.reset);

    }


}
