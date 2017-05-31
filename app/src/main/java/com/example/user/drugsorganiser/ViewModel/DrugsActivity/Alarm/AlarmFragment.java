package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Alarm;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.DrugsActivity;

import static com.example.user.drugsorganiser.R.string.alarm_notification_comment;
import static com.example.user.drugsorganiser.R.string.alarm_notification_title;
import static com.example.user.drugsorganiser.ViewModel.DrugsActivity.DrugsActivity.ACCEPTED;
import static com.example.user.drugsorganiser.ViewModel.DrugsActivity.DrugsActivity.ALARM;
import static com.example.user.drugsorganiser.ViewModel.DrugsActivity.DrugsActivity.DESCRIPTION;
import static com.example.user.drugsorganiser.ViewModel.DrugsActivity.DrugsActivity.DRUG;
import static com.example.user.drugsorganiser.ViewModel.DrugsActivity.DrugsActivity.USER;

public class AlarmFragment extends Fragment implements View.OnClickListener {

    private Button btnAccept, btnReject;
    private TextView tvStatement1, tvStatement2;

    public AlarmFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_alarm, container, false);

        btnAccept = (Button) view.findViewById(R.id.accept);
        btnReject = (Button) view.findViewById(R.id.reject);
        tvStatement1 = (TextView) view.findViewById(R.id.statement1);
        tvStatement2 = (TextView) view.findViewById(R.id.statement2);

        btnAccept.setOnClickListener(this);
        btnReject.setOnClickListener(this);

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
            tvStatement1.setText(String.format(getString(alarm_notification_title),arguments.getString(USER), arguments.getString(DRUG)));
            tvStatement2.setText(String.format(getString(alarm_notification_comment),arguments.getString(DESCRIPTION)));
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
        Bundle bundle = this.getArguments();
        Intent newIntent = new Intent(getActivity(), DrugsActivity.class);
        newIntent.putExtra(ACCEPTED,accepted);
        newIntent.putExtra(USER, bundle.getString(USER));
        newIntent.putExtra(DRUG, bundle.getString(DRUG));
        newIntent.putExtra(ALARM, bundle.getBoolean(ALARM));
        newIntent.putExtra(DESCRIPTION, bundle.getString(DESCRIPTION));
        newIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        getActivity().startActivity(newIntent);
    }

}
