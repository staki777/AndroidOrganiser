package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.MyDrugs.AddEditDrug;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.BaseDrugsActivityFragment;

public abstract class DateTimeBaseFragment extends BaseDrugsActivityFragment implements View.OnClickListener {

    protected DatePicker datePicker;
    protected TimePicker timePicker;
    protected Button positiveBtn, negativeBtn;

    public DateTimeBaseFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();


    }

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_date_time, container, false);
            datePicker = (DatePicker) v.findViewById(R.id.datePicker);
            timePicker = (TimePicker) v.findViewById(R.id.timePicker);
            positiveBtn = (Button) v.findViewById(R.id.positive_button);
            negativeBtn = (Button) v.findViewById(R.id.negative_button);
            positiveBtn.setOnClickListener(this);
            negativeBtn.setOnClickListener(this);
            getActivity().setTitle("Adding new term");
        return v;
    }


    @Override
    public abstract void onClick(View v);

}
