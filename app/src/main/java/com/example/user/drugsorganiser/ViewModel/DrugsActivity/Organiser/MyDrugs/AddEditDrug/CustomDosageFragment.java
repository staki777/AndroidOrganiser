package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.MyDrugs.AddEditDrug;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.BaseDrugsActivityFragment;


public class CustomDosageFragment extends BaseDrugsActivityFragment implements View.OnClickListener {

    private Button addNewTermBtn;
    private RecyclerView recyclerView;

    public CustomDosageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_custom_dosage, container, false);

        return v;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        addNewTermBtn = (Button) getView().findViewById(R.id.btnAddNewTerm);
        addNewTermBtn.setOnClickListener(this);
        recyclerView = (RecyclerView) getView().findViewById(R.id.term_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        TermAdapter termAdapter = new TermAdapter( activity().getEditedDrug() ,getActivity());
        recyclerView.setAdapter(termAdapter);
    }

    @Override
    public void onClick(View v) {
        if(v == addNewTermBtn){
            Log.i(LogTag(), "addNetwTerm button clicked");
            activity().removeIfExists(DateTimeCustomDosageFragment.class.getSimpleName());
            activity().replaceWithNew(R.id.toReplace, new DateTimeCustomDosageFragment(), true);
        }
    }

}
