package com.example.user.drugsorganiser.ViewModel;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.drugsorganiser.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyDrugsFragment extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;

    public MyDrugsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();

        recyclerView = (RecyclerView) getView().findViewById(R.id.drug_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        final DrugAdapter drugAdapter = new DrugAdapter(((DrugsActivity)getActivity()).getUser(), getActivity());
        recyclerView.setAdapter(drugAdapter);

        fab = (FloatingActionButton) getView().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogHelper dh=new DialogHelper(((DrugsActivity)getActivity()).getUser(), getActivity(), drugAdapter);
                dh.showAddDialog(view);
            }
        });



    }

    @Override
    public void onResume() {
        super.onResume();

        final DrugAdapter drugAdapter = new DrugAdapter(((DrugsActivity)getActivity()).getUser(), getActivity());
        recyclerView.setAdapter(drugAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogHelper dh=new DialogHelper(((DrugsActivity)getActivity()).getUser(), getActivity(), drugAdapter);
                dh.showAddDialog(view);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_drugs, container, false);
    }


}
