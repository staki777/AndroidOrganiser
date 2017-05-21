package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Alarm;


import android.app.Fragment;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.DrugsActivity;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.LoginRegister.LoginRegisterFragment;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.OrganiserFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlarmFragment extends Fragment implements View.OnClickListener {

    final public static String DRUG = "drugName";
    final public static String USER = "userName";
    final public static String DESCRIPTION = "description";
    final public static String ACCEPTED = "isDoseAccepted";
    final public static String LOGGEDIN = "isLoggedIn";

    private Button btnAccept, btnReject;
    private TextView tvStatement;
    private boolean loggedIn;

    public AlarmFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_alarm, container, false);

        btnAccept = (Button) view.findViewById(R.id.accept);
        btnReject = (Button) view.findViewById(R.id.reject);
        tvStatement = (TextView) view.findViewById(R.id.statement);

        btnAccept.setOnClickListener(this);
        btnReject.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        Log.i("AlarmFragment","onCreateView");
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("AlarmFragment","onCreate");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("AlarmFragment","onStart");
        Bundle arguments = this.getArguments();
        if(arguments != null){
            Log.i("AlarmFragment","onStart, savedInstance");
            tvStatement.setText(arguments.getString(USER)+", czas na " + arguments.getString(DRUG) +
                    "\nPamiętaj: " + arguments.getString(DESCRIPTION));
            loggedIn = arguments.getBoolean(LOGGEDIN);
        }
    }

    @Override
    public void onClick(View v) {
        if(v == btnAccept){
            onBtnClick(true);
        }
        else if(v == btnReject){
            onBtnClick(false);
        }
    }

    private void onBtnClick(boolean accepted) {
        Intent stopIntent = new Intent(getActivity(), RingtonePlayingService.class);
        getActivity().stopService(stopIntent);
        Bundle bundle = new Bundle();
        bundle.putBoolean(ACCEPTED,accepted);
        if (loggedIn) {
            OrganiserFragment organiserFragment = new OrganiserFragment();
            organiserFragment.setArguments(bundle);
            ((DrugsActivity)getActivity()).replaceWithNewOrExisting(R.id.main_to_replace, organiserFragment);
            //TODO: React on information about dose (accepted/rejected).
        }
        else {
            LoginRegisterFragment loginRegisterFragment = new LoginRegisterFragment();
            loginRegisterFragment.setArguments(bundle);
            ((DrugsActivity)getActivity()).replaceWithNewOrExisting(R.id.main_to_replace, loginRegisterFragment);
            //TODO: React on information about dose (accepted/rejected) after logging in.
        }

    }

}
