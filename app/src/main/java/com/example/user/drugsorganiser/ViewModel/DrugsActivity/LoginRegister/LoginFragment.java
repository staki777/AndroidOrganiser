package com.example.user.drugsorganiser.ViewModel.DrugsActivity.LoginRegister;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.drugsorganiser.Model.User;
import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.DrugsActivity;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.OrganiserFragment;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.SaveSharedPreference;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;

import java.sql.SQLException;


public class LoginFragment extends Fragment implements View.OnClickListener {

    private Button btnLogin;
    private EditText etLogin, etPassword;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("LoginFragment", "onACreateVIew");

        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("LoginFragment", "onCreate");
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.i("LoginFragment", "onStart");
        btnLogin = (Button) getView().findViewById(R.id.btnLogin);
        etLogin = (EditText) getView().findViewById(R.id.etLogin);
        etPassword = (EditText) getView().findViewById(R.id.etPassword);

        btnLogin.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if(v == btnLogin)
        {
            if(etLogin.getText().toString().trim().isEmpty() ||
                    etPassword.getText().toString().trim().isEmpty())
            {
                Toast.makeText(getActivity(), getActivity().getString(R.string.fields_must_be_filled), Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                final Dao<User, Integer> userDao = ((DrugsActivity)getActivity()).getHelper().getUserDao();
                PreparedQuery<User> q=userDao.queryBuilder().where().eq(User.LOGIN_COLUMN, etLogin.getText().toString().trim()).prepare();
                final User user = userDao.queryForFirst(q);
                if(user == null || !user.password.equals(etPassword.getText().toString().trim())){
                    Toast.makeText(getActivity(), getActivity().getString(R.string.invalid_login_or_password), Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(getActivity(), getActivity().getString(R.string.login_successfull), Toast.LENGTH_SHORT).show();
                Log.i("LoginFragment", "setting user..");
                ((DrugsActivity)getActivity()).setUser(user);
                SaveSharedPreference.setUserID(getActivity(), user.userId);

                ((DrugsActivity)getActivity()).replaceWithNewOrExisting(R.id.main_to_replace, new OrganiserFragment());
                ((DrugsActivity)getActivity()).removeIfExists(LoginFragment.class.getSimpleName());
                ((DrugsActivity)getActivity()).removeIfExists(RegisterFragment.class.getSimpleName());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
