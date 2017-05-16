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

import com.example.user.drugsorganiser.Model.Drug;
import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.DrugsActivity;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.DrugAdapter;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.MyDrugs.AddEditDrug.AddEditDrugFragment;

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
        Log.i("MyDrugsFragment", "onStart!");
        getActivity().setTitle(getView().getResources().getString(R.string.my_drugs));
        recyclerView = (RecyclerView) getView().findViewById(R.id.drug_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        DrugAdapter drugAdapter = new DrugAdapter(((DrugsActivity)getActivity()).getUser(), getActivity());
        recyclerView.setAdapter(drugAdapter);

        fab = (FloatingActionButton) getView().findViewById(R.id.fab);
        setOnClickListenerToFloatingButton();

        Bundle bundle=getArguments();
        if(bundle != null){
            Drug drug=(Drug)bundle.getSerializable("newDrug");
            Drug editDrug = (Drug)bundle.getSerializable("editDrug");
            if(drug != null)
                drugAdapter.addItem(drug);
            else if(editDrug!= null)
                drugAdapter.editItem(editDrug);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("MyDrugsFragment", "onResume");

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
                AddEditDrugFragment adf=new AddEditDrugFragment();
                ((DrugsActivity)getActivity()).replaceWithNewAndAddToBackStack(R.id.toReplace, adf);
            }
        });
    }
}
