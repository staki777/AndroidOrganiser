package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.MyDrugs.AddEditDrug;

import android.view.View;

import com.example.user.drugsorganiser.Model.CustomDose;
import com.example.user.drugsorganiser.Model.Drug;
import com.example.user.drugsorganiser.R;

import org.joda.time.DateTime;

public class DateTimeConstantIntervalDosageFragment extends DateTimeBaseFragment implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        if(v == positiveBtn){
            Drug editedDrug = activity().getEditedDrug();
            TermAdapter termAdapter = new TermAdapter(editedDrug, getActivity());
            DateTime dt = new DateTime(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute());
            termAdapter.addItem(new CustomDose(editedDrug, dt));
            activity().replaceWithNewOrExisting(R.id.toReplace, new AddEditDrugFragment());

        }
        else if(v == negativeBtn){
            activity().replaceWithNewOrExisting(R.id.toReplace, new AddEditDrugFragment());
        }
    }
}
