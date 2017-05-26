package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.MyDrugs.AddEditDrug;

import android.view.View;

import com.example.user.drugsorganiser.Model.CustomDose;
import com.example.user.drugsorganiser.Model.Drug;

import org.joda.time.DateTime;

public class DateTimeCustomDosageFragment extends DateTimeBaseFragment implements View.OnClickListener {


    @Override
    public void onClick(View v) {
        if(v == positiveBtn){
            Drug editedDrug = activity().getEditedDrug();
            TermAdapter termAdapter = new TermAdapter(editedDrug, getActivity());
            DateTime dt = new DateTime(datePicker.getYear(), datePicker.getMonth()+1, datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute());
            termAdapter.addItem(new CustomDose(editedDrug, dt));
            activity().onBackPressed();

        }
        else if(v == negativeBtn){
            activity().onBackPressed();
        }
    }
}
