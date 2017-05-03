package com.example.user.drugsorganiser.ViewModel.MainActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.user.drugsorganiser.DataBase.DatabaseHelper;
import com.example.user.drugsorganiser.R;
import com.j256.ormlite.android.apptools.OpenHelperManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseHelper databaseHelper = null;


    private Button btnLogin;
    private Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin= (Button) findViewById(R.id.btnLoginTab);
        btnRegister= (Button) findViewById(R.id.btnRegisterTab);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

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

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this,DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

}
