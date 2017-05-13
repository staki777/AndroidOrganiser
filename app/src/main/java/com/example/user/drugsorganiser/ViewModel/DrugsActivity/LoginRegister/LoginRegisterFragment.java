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
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.DrugsActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginRegisterFragment extends Fragment implements View.OnClickListener  {

    private Button btnLogin;
    private Button btnRegister;

    public LoginRegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("LoginRegisterFragment", "onCreateView");
        return inflater.inflate(R.layout.fragment_login_register, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("LoginRegisterFragment", "onStart");

        btnLogin= (Button) getView().findViewById(R.id.btnLoginTab);
        btnRegister= (Button) getView().findViewById(R.id.btnRegisterTab);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        Toolbar toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));

        Fragment existing =getFragmentManager().findFragmentByTag(getString(R.string.LOGREG_TAG));
        if(existing != null){
            btnLogin.setEnabled((existing instanceof LoginFragment)? false: true);
            btnRegister.setEnabled((existing instanceof LoginFragment)? true: false);
            ((DrugsActivity)getActivity()).restoreExistingFragment(getString(R.string.LOGREG_TAG), new LoginFragment(), R.id.login_register_fragment);
        }
    }
    @Override
    public void onClick(View v) {
        Log.i("LoginRegisterFragment", "onClick");
        if(v == btnLogin){
            ((DrugsActivity)getActivity()).removeAndReplaceOldFragment(getString(R.string.LOGREG_TAG), new LoginFragment(), R.id.login_register_fragment);
            btnLogin.setEnabled(false);
            btnRegister.setEnabled(true);
        }
        else if(v == btnRegister){
            ((DrugsActivity)getActivity()).removeAndReplaceOldFragment(getString(R.string.LOGREG_TAG), new RegisterFragment(), R.id.login_register_fragment);
            btnLogin.setEnabled(true);
            btnRegister.setEnabled(false);
        }
    }
}
