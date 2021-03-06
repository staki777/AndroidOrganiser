package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.MyDrugs;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.BaseDrugsActivityFragment;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.DrugAdapter;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.MyDrugs.AddEditDrug.AddEditDrugFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyDrugsFragment extends BaseDrugsActivityFragment {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;


    public MyDrugsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(LogTag(), "onStart!");
        getActivity().setTitle(getView().getResources().getString(R.string.my_drugs));
        activity().refreshUserData();

        recyclerView = (RecyclerView) getView().findViewById(R.id.drug_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        DrugAdapter drugAdapter = new DrugAdapter(activity().getUser(), getActivity());
        recyclerView.setAdapter(drugAdapter);

        fab = (FloatingActionButton) getView().findViewById(R.id.fab);
        setOnClickListenerToFloatingButton();

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(LogTag(), "onResume");
        activity().refreshUserData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_drugs, container, false);
    }

    private void setOnClickListenerToFloatingButton(){

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity().removeIfExists(AddEditDrugFragment.class.getSimpleName());
                activity().replaceWithNew(R.id.toReplace, new AddEditDrugFragment(), true);
            }
        });
    }


}
