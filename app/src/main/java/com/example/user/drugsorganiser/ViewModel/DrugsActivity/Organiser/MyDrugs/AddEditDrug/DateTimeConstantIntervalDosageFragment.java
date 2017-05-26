package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.MyDrugs.AddEditDrug;

import android.util.Log;
import android.view.View;

import com.example.user.drugsorganiser.Model.Drug;
import com.example.user.drugsorganiser.Shared.UniversalMethods;

import org.joda.time.DateTime;

public class DateTimeConstantIntervalDosageFragment extends DateTimeBaseFragment implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        if(v == positiveBtn){
            Drug editedDrug = activity().getEditedDrug();
            DateTime dt = new DateTime(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute());
            activity().getEditedDrug().constantIntervalDose.firstDose = dt;
            activity().getEditedDrug().constantIntervalDose.drug = activity().getEditedDrug();
            Log.i(LogTag(), "Date saved: "+ UniversalMethods.DateTimeToString(activity().getEditedDrug().constantIntervalDose.firstDose));
            activity().onBackPressed();

        }
        else if(v == negativeBtn){
            activity().onBackPressed();
        }
    }
}
