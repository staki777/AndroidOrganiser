package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.Preview;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.drugsorganiser.Model.Drug;
import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.BaseDrugsActivityFragment;

/**
 * Created by Gosia on 31.05.2017.
 */

public class PreviewFragment extends BaseDrugsActivityFragment implements View.OnClickListener {

    private TextView name;
    private TextView dose_c;
    private TextView dose_type;
    private TextView dosage_type;
    private TextView info;
    private LinearLayout ll;
    public Drug myDrug;
    public Drug drugToView;
    private String[] dosageTypesArr;

    public PreviewFragment(){

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.drug_preview, container, false);
        dosageTypesArr = v.getResources().getStringArray(R.array.dosage_array);
        name = (TextView) v.findViewById(R.id.tv_name);
        dose_c = (TextView) v.findViewById(R.id.tv_dosePicker);
        dose_type = (TextView) v.findViewById(R.id.tv_dose_type_spinner);
        dosage_type = (TextView) v.findViewById(R.id.tv_dosage_type_spinner);
        info = (TextView) v.findViewById(R.id.tv_editComment);
        ll = (LinearLayout) v.findViewById(R.id.linear_important);
        getActivity().setTitle(v.getResources().getString(R.string.drug_preview));

        drugToView = activity().getEditedDrug();
        myDrug = drugToView;
        name.setText(drugToView.name);
        dose_c.setText(""+drugToView.doseQuantity);
        String doseName = activity().getDoseTypes().itemAtIndex(drugToView.doseType);
        if(drugToView.doseType == activity().getDoseTypes().getPositionOfOther())
            doseName = drugToView.customDoseType;
        dose_type.setText(doseName);
        info.setText(drugToView.comment);

        if(drugToView.important)
            ll.setVisibility(View.VISIBLE);
        if(drugToView.comment.length()<=0) {
            info.setText(getResources().getString(R.string.add_info));
            info.setTextColor(getResources().getColor(R.color.colorGray));
        }
        dosage_type.setText(dosageTypesArr[drugToView.dosesSeriesType]);
        if(drugToView.dosesSeriesType == 0 || drugToView.dosesSeriesType == 2) {
            Log.i("PreviewFragment", "replace");
            activity().removeIfExists(MyListFragment.class.getSimpleName());
            activity().removeIfExists(IntervalPrevFragment.class.getSimpleName());
            activity().replaceWithNew(R.id.dosage_type_to_replace_prev, new MyListFragment(),false);
        }
        if(drugToView.dosesSeriesType == 1) {
            activity().removeIfExists(IntervalPrevFragment.class.getSimpleName());
            activity().removeIfExists(MyListFragment.class.getSimpleName());
            activity().replaceWithNew(R.id.dosage_type_to_replace_prev, new IntervalPrevFragment(), false);
        }
        return v;
    }

    @Override
    public void onClick(View v) {

    }
}
