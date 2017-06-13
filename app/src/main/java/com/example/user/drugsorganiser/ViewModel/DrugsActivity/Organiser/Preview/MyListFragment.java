package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.Preview;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.drugsorganiser.Model.Drug;
import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.BaseDrugsActivityFragment;

/**
 * Created by Gosia on 31.05.2017.
 */

public class MyListFragment extends BaseDrugsActivityFragment {

    private RecyclerView list;
    private Drug myDrug;
    private int pos;

    public MyListFragment(){

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list, container, false);

        return v;
    }
    @Override
    public void onStart() {
        Log.i(LogTag(), "onStartListFragment");
        super.onStart();
        list = (RecyclerView) getView().findViewById(R.id.term_list_prev);
        list.setLayoutManager(new LinearLayoutManager(activity().getApplicationContext()));
        //DoseAdapter doseAdapter = new DoseAdapter(getActivity());
        //Pair<Drug, DateTime> drug = doseAdapter.getDrug(pos);
        myDrug = activity().getEditedDrug();
        PreviewAdapter adapter = new PreviewAdapter(myDrug, getActivity());
        list.setAdapter(adapter);
    }
}
