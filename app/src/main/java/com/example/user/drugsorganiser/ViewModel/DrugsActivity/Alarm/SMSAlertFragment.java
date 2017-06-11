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

import static com.example.user.drugsorganiser.ViewModel.DrugsActivity.DrugsActivity.DOSE_DETAILS;
import static com.example.user.drugsorganiser.ViewModel.DrugsActivity.DrugsActivity.SMS_ALERT;

public class SMSAlertFragment extends Fragment implements View.OnClickListener {

    private Button btnOK;
    private TextView tvStatement1, tvStatement2;

    public SMSAlertFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_smsalert, container, false);

        btnOK = (Button) view.findViewById(R.id.smsAlertOk);
        tvStatement1 = (TextView) view.findViewById(R.id.statementSMS1);
        tvStatement2 = (TextView) view.findViewById(R.id.statementSMS2);

        btnOK.setOnClickListener(this);

        Log.i("SMSAlertFragment","onCreateView");

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("SMSAlertFragment","onCreate");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("SMSAlertFragment","onStart");
        Bundle arguments = this.getArguments();
        if(arguments != null){
            Log.i("SMSAlertFragment","onStart, savedInstance" + arguments);
            tvStatement1.setText(arguments.getString(SMS_ALERT));
            tvStatement2.setText(arguments.getString(DOSE_DETAILS));
        }
    }

    @Override
    public void onClick(View v) {
        Intent newIntent = new Intent(getActivity(), DrugsActivity.class);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        getActivity().startActivity(newIntent);
    }
}

