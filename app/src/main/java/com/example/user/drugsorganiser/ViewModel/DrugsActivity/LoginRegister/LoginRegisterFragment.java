package com.example.user.drugsorganiser.ViewModel.DrugsActivity.LoginRegister;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.user.drugsorganiser.R;

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
        return inflater.inflate(R.layout.fragment_login_register, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        btnLogin= (Button) getView().findViewById(R.id.btnLoginTab);
        btnRegister= (Button) getView().findViewById(R.id.btnRegisterTab);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        Toolbar toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
    }
    @Override
    public void onClick(View v) {

        if(v == btnLogin){
            getFragmentManager().beginTransaction().replace(R.id.login_register_fragment, new LoginFragment()).disallowAddToBackStack().commit();
            btnLogin.setEnabled(false);
            btnRegister.setEnabled(true);
        }
        else if(v == btnRegister){
            getFragmentManager().beginTransaction().replace(R.id.login_register_fragment, new RegisterFragment()).disallowAddToBackStack().commit();
            btnLogin.setEnabled(true);
            btnRegister.setEnabled(false);
        }
    }
}
