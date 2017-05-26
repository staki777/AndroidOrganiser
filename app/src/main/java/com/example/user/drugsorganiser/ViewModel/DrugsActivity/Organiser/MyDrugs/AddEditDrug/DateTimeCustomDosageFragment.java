package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.MyDrugs.AddEditDrug;

import android.view.View;

import com.example.user.drugsorganiser.Model.CustomDose;
import com.example.user.drugsorganiser.Model.Drug;

import org.joda.time.DateTime;

public class DateTimeCustomDosageFragment extends DateTimeBaseFragment implements View.OnClickListener {

//    private DatePicker datePicker;
//    private TimePicker timePicker;
//    private Button positiveBtn, negativeBtn;
//
//    public DateTimeFragment() {
//        // Required empty public constructor
//    }
//
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        getActivity().setTitle("Adding new term");
//        datePicker = (DatePicker) getView().findViewById(R.id.datePicker);
//        timePicker = (TimePicker) getView().findViewById(R.id.timePicker);
//        positiveBtn = (Button) getView().findViewById(R.id.positive_button);
//        negativeBtn = (Button) getView().findViewById(R.id.negative_button);
//        positiveBtn.setOnClickListener(this);
//        negativeBtn.setOnClickListener(this);
//    }
//
//        @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_date_time, container, false);
//    }
//

    @Override
    public void onClick(View v) {
        if(v == positiveBtn){
            Drug editedDrug = activity().getEditedDrug();
            TermAdapter termAdapter = new TermAdapter(editedDrug, getActivity());
            DateTime dt = new DateTime(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute());
            termAdapter.addItem(new CustomDose(editedDrug, dt));
           // activity().replaceWithNewOrExisting(R.id.toReplace, new AddEditDrugFragment());
            activity().onBackPressed();

        }
        else if(v == negativeBtn){
           // activity().replaceWithNewOrExisting(R.id.toReplace, new AddEditDrugFragment());
            activity().onBackPressed();
        }
    }
}
