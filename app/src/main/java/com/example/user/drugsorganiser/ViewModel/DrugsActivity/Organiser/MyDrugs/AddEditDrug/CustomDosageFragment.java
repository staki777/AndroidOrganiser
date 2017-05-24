package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.MyDrugs.AddEditDrug;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.DrugsActivity;


public class CustomDosageFragment extends Fragment implements View.OnClickListener {

    private Button addNewTermBtn;
    public CustomDosageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating layout
        View v = inflater.inflate(R.layout.fragment_custom_dosage, container, false);
        // We obtain layout references

        return v;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Button reset=(Button)findViewById(R.id.reset);

    }

    @Override
    public void onStart() {
        super.onStart();
        addNewTermBtn = (Button) getView().findViewById(R.id.btnAddNewTerm);
        addNewTermBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == addNewTermBtn){
            Log.i("CustomDosageFragment", "addNetwTerm button clicked");
            ((DrugsActivity)getActivity()).removeIfExists(DateTimeFragment.class.getSimpleName());
            ((DrugsActivity)getActivity()).replaceWithNew(R.id.toReplace, new DateTimeFragment(), true);
        }
    }
}
