package com.example.user.drugsorganiser.ViewModel.DrugsActivity.LoginRegister;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.BaseDrugsActivityFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginRegisterFragment extends BaseDrugsActivityFragment implements View.OnClickListener  {

    private Button btnLogin, btnRegister;
    private boolean btnLoginEnabled, btnRegisterEnabled;

    public LoginRegisterFragment() {
        // Required empty public constructor
    }

    private String TAG = getClass().getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i(LogTag(), "onCreateView");
        View v = inflater.inflate(R.layout.fragment_login_register, container, false);

        btnLogin= (Button) v.findViewById(R.id.btnLoginTab);
        btnRegister= (Button) v.findViewById(R.id.btnRegisterTab);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(LogTag(), "onStart");
        btnLogin.setEnabled(btnLoginEnabled);
        btnRegister.setEnabled(btnRegisterEnabled);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            btnLoginEnabled = savedInstanceState.getBoolean(TAG+"btnLogin");
            btnRegisterEnabled = savedInstanceState.getBoolean(TAG+"btnRegister");
        }
        else {
            btnLoginEnabled = true;
            btnRegisterEnabled = true;
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(TAG+"btnLogin", btnLogin.isEnabled());
        outState.putBoolean(TAG+"btnRegister", btnRegister.isEnabled());
    }

    @Override
    public void onClick(View v) {
        if(v == btnLogin){
            activity().replaceWithNewOrExisting(R.id.login_register_fragment, new LoginFragment());
            btnLogin.setEnabled(false);
            btnRegister.setEnabled(true);
        }
        else if(v == btnRegister){
            activity().replaceWithNewOrExisting(R.id.login_register_fragment, new RegisterFragment());
            btnLogin.setEnabled(true);
            btnRegister.setEnabled(false);
        }
    }
}
