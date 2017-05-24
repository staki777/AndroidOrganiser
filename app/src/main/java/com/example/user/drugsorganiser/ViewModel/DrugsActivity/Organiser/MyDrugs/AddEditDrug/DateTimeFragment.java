package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.MyDrugs.AddEditDrug;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.DrugsActivity;

public class DateTimeFragment extends Fragment implements View.OnClickListener {

    private DatePicker datePicker;
    private TimePicker timePicker;
    private Button positiveBtn, negativeBtn;

    public DateTimeFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle("Adding new term");
        datePicker = (DatePicker) getView().findViewById(R.id.datePicker);
        timePicker = (TimePicker) getView().findViewById(R.id.timePicker);
        positiveBtn = (Button) getView().findViewById(R.id.positive_button);
        negativeBtn = (Button) getView().findViewById(R.id.negative_button);
        positiveBtn.setOnClickListener(this);
        negativeBtn.setOnClickListener(this);
    }

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_date_time, container, false);
    }


    @Override
    public void onClick(View v) {
        if(v == positiveBtn){

        }
        else if(v == negativeBtn){
            ((DrugsActivity) getActivity()).replaceWithNewOrExisting(R.id.toReplace, new AddEditDrugFragment());
        }
    }
}
