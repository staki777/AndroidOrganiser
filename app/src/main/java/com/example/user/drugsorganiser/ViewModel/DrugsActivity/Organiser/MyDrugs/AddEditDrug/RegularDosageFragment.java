package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.MyDrugs.AddEditDrug;

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
import android.widget.Spinner;

import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.BaseDrugsActivityFragment;


public class RegularDosageFragment extends BaseDrugsActivityFragment implements View.OnClickListener {

    private Spinner spFewTimesType;
    public int typeofAddDate = 0;
    private int position = -1;
    private Button addNewTermBtn;
    private RecyclerView recyclerView;
    private String type;
    private Boolean start;
    private TermAdapterRegular termAdapter;

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
        termAdapter = new TermAdapterRegular(activity().getEditedDrug(), getActivity());
        recyclerView.setAdapter(termAdapter);
        String[] array = getResources().getStringArray(R.array.time_array);

        if(termAdapter.getItemCount()>0) {
            if(termAdapter.getType().contentEquals(array[0]))
                position = 0;
            else if(termAdapter.getType().contentEquals(array[1]))
                position = 1;
            else if(termAdapter.getType().contentEquals(array[2]))
                position = 2;
            else
                position = 3;
        }
        start = true;
        if(position!=-1)
            spFewTimesType.setSelection(position);
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

        View v = inflater.inflate(R.layout.fragment_regular_dosage, container, false);
        spFewTimesType = (Spinner) v.findViewById(R.id.FewTimes_type_spinner);

        ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(activity(), R.layout.spinner_item, activity().getResources().getStringArray(R.array.time_array));
        spFewTimesType.setAdapter(timeAdapter);
        spFewTimesType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position!=typeofAddDate) {
                    if(!(start)) {
                        termAdapter.deleteAllItems();
                        Log.i("RegularDosageFragment", "Deleted");
                    }
                }
                typeofAddDate = position;
                start=false;
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
    }


}
