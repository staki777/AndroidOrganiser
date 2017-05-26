package com.example.user.drugsorganiser.ViewModel.DrugsActivity;


import android.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseDrugsActivityFragment extends Fragment {


    public BaseDrugsActivityFragment() {
        // Required empty public constructor
    }

    protected  DrugsActivity activity(){
        return ((DrugsActivity) getActivity());
    }

    protected String LogTag(){
        return  this.getClass().getSimpleName();
    }

}
