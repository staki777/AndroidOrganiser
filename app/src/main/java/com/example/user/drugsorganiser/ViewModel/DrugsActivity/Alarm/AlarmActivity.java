package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Alarm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.DrugsActivity;

public class AlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drugs);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.getBoolean(DrugsActivity.ALARM)) {
            AlarmFragment alarmFragment = new AlarmFragment();
            alarmFragment.setArguments(bundle);
            String tag = alarmFragment.getClass().getSimpleName();
            getFragmentManager().beginTransaction().replace(R.id.main_to_replace, alarmFragment, tag).disallowAddToBackStack().commit();
            Log.i("AlarmActivity","onCreate");
        }
    }

    @Override
    public void onBackPressed() {}

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("AlarmActivity","onDestroy");
    }
}
