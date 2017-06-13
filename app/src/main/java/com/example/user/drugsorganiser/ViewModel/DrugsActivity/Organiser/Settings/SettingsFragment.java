package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.Settings;


import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;

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
        Log.i("SettingsFragment", "OnCreate");
        getActivity().setTitle(getActivity().getString(R.string.action_settings));
        addPreferencesFromResource(R.xml.preferences);
        ListPreference languageListPr = (ListPreference) findPreference (getActivity().getString(R.string.languageKey));
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String language = SP.getString(getString(R.string.languageKey), "no");
        if ("en".equals(language)) {
            languageListPr.setValueIndex(1);
        }
        else {
            languageListPr.setValueIndex(0);
        }
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
                    getActivity().setTitle(res.getString(R.string.action_settings));
                    DisplayMetrics dm = res.getDisplayMetrics();
                    android.content.res.Configuration conf = res.getConfiguration();
                    conf.setLocale(new Locale(language)); // API 17+ only.
                    res.updateConfiguration(conf, dm);
                    getActivity().recreate();
                }
            }

        };

        SP.registerOnSharedPreferenceChangeListener(spChanged);
    }


//    @Override
//    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
//
//    }
}
