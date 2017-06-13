package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.Schedule;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.BaseDrugsActivityFragment;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.DoseAdapter;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.Preview.PreviewFragment;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.Preview.RecycleItemClickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleFragment extends BaseDrugsActivityFragment {

    private RecyclerView recyclerView;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_schedule, container, false);
        return v;
    }

    @Override
    public void onStart() {
        Log.i(LogTag(), "onStart");
        super.onStart();
        getActivity().setTitle(getView().getResources().getString(R.string.schedule));
        recyclerView = (RecyclerView) getView().findViewById(R.id.doses_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        final DoseAdapter doseAdapter = new DoseAdapter( getActivity());
        recyclerView.setAdapter(doseAdapter);
        recyclerView.addOnItemTouchListener(
                new RecycleItemClickListener(getActivity(), new RecycleItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        activity().setEditedDrug(doseAdapter.getDrug(position).first);
                        activity().removeIfExists(PreviewFragment.class.getSimpleName());
                        activity().replaceWithNew(R.id.toReplace, new PreviewFragment(), true);
                    }
                })
        );
    }

    @Override
    public void onResume() {
        Log.i(LogTag(), "onResume");

        super.onResume();
        final DoseAdapter doseAdapter = new DoseAdapter(getActivity());
        recyclerView.setAdapter(doseAdapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(LogTag(), "Schedule fragment on detach");
        getFragmentManager().saveFragmentInstanceState(this);
    }


}
