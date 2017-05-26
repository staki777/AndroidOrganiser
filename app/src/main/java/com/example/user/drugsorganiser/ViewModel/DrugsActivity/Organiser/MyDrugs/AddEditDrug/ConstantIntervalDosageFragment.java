package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.MyDrugs.AddEditDrug;

import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.Shared.UniversalMethods;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.BaseDrugsActivityFragment;

import org.joda.time.DateTime;


public class ConstantIntervalDosageFragment extends BaseDrugsActivityFragment {

    private TextView startingDoseTermTv;
    private TextView optionsTv;

    public ConstantIntervalDosageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating layout
        View v = inflater.inflate(R.layout.fragment_constant_interval, container, false);
        // We obtain layout references
        startingDoseTermTv = (TextView) v.findViewById(R.id.item_date);
        if(activity().getEditedDrug().constantIntervalDose.firstDose == null){
            activity().getEditedDrug().constantIntervalDose.firstDose = DateTime.now();
        }
        startingDoseTermTv.setText(UniversalMethods.DateTimeToString(activity().getEditedDrug().constantIntervalDose.firstDose));

        optionsTv = (TextView) v.findViewById(R.id.item_options);
        optionsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                PopupMenu popup = new PopupMenu(getActivity(), optionsTv);
                popup.inflate(R.menu.menu_only_edit_item);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit_menu_item:
                                activity().removeIfExists(DateTimeConstantIntervalDosageFragment.class.getSimpleName());
                                activity().replaceWithNew(R.id.toReplace, new DateTimeConstantIntervalDosageFragment(), true);
                                break;
                        }
                        return false;
                    }
                });
                popup.show();

            }
        });

        return v;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
