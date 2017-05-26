package com.example.user.drugsorganiser.ViewModel.DrugsActivity.LoginRegister;

import android.content.Context;
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
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.BaseDrugsActivityFragment;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.OrganiserFragment;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.SaveSharedPreference;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;

import java.sql.SQLException;
import java.util.ArrayList;


public class RegisterFragment extends BaseDrugsActivityFragment implements View.OnClickListener {


    private Button btnRegister;
    private EditText etLogin, etPassword, etMail;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(LogTag(), "in onCreateView");
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart(){
        super.onStart();

        btnRegister = (Button) getView().findViewById(R.id.btnRegister);
        etLogin = (EditText) getView().findViewById(R.id.etLogin);
        etPassword = (EditText) getView().findViewById(R.id.etPassword);
        etMail = (EditText) getView().findViewById(R.id.etMail);
        btnRegister.setOnClickListener(this);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onClick(View v) {
        if(v == btnRegister)
        {
                if(etLogin.getText().toString().trim().isEmpty() ||
                        etMail.getText().toString().trim().isEmpty() ||
                        etPassword.getText().toString().trim().isEmpty()){
                    Toast.makeText(getActivity(), getActivity().getString(R.string.fields_must_be_filled), Toast.LENGTH_SHORT).show();
                    return;
                }


                    final User user = new User();
                    user.login = etLogin.getText().toString();
                    user.password = etPassword.getText().toString();
                    user.mail = etMail.getText().toString();
                    user.drugs = new ArrayList<>();

                    try {
                        final Dao<User, Integer> userDao = activity().getHelper().getUserDao();
                        PreparedQuery<User> q=userDao.queryBuilder().where().eq(User.LOGIN_COLUMN, user.login).prepare();
                        if(!userDao.query(q).isEmpty()){
                            Toast.makeText(getActivity(), getActivity().getString(R.string.login_exists_warning), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        q=userDao.queryBuilder().where().eq(User.MAIL_COLUMN, user.mail).prepare();
                        if(!userDao.query(q).isEmpty()){
                            Toast.makeText(getActivity(), getActivity().getString(R.string.mail_already_in_use_warning), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        //insert data into a database table
                        userDao.create(user);
                        Toast.makeText(getActivity(), getActivity().getString(R.string.account_created), Toast.LENGTH_SHORT).show();

                        Log.i(LogTag(), "Setting user..");
                        activity().setUser(user);
                        SaveSharedPreference.setUserID(getActivity(), user.userId);

                        activity().replaceWithNewOrExisting(R.id.main_to_replace, new OrganiserFragment());
                        activity().removeIfExists(LoginFragment.class.getSimpleName());
                        activity().removeIfExists(RegisterFragment.class.getSimpleName());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
            }
    }





}
