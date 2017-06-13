package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.Settings;


import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;

import com.example.user.drugsorganiser.R;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public  class SettingsFragment extends PreferenceFragment {

    private SharedPreferences.OnSharedPreferenceChangeListener spChanged;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        spChanged = new SharedPreferences.OnSharedPreferenceChangeListener() {

            @Override @TargetApi(17)
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if(!isAdded())
                    return;
                if (key.equals(getString(R.string.languageKey))) {
                    String language = sharedPreferences.getString(getString(R.string.languageKey), "en");
                    if ("en".equals(language)) {
                        language = "en_EN";
                    }
                    else {
                        language = "pl_PL";
                    }
                    Resources res = getActivity().getApplicationContext().getResources();
                    DisplayMetrics dm = res.getDisplayMetrics();
                    android.content.res.Configuration conf = res.getConfiguration();
                    conf.setLocale(new Locale(language)); // API 17+ only.
                    res.updateConfiguration(conf, dm);
                    getActivity().recreate();
                }
            }

        };

        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        SP.registerOnSharedPreferenceChangeListener(spChanged);
    }


//    @Override
//    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
//
//    }
}
