package com.example.user.drugsorganiser.ViewModel.DrugsActivity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.user.drugsorganiser.DataBase.DatabaseHelper;
import com.example.user.drugsorganiser.Model.User;
import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.LoginRegister.LoginRegisterFragment;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.stmt.PreparedQuery;

import java.sql.SQLException;


public class DrugsActivity extends AppCompatActivity
//        implements NavigationView.OnNavigationItemSelectedListener
{

    private static final int ALARM = 1;

    private AlarmManagerBroadcastReceiver alarm;
    private DatabaseHelper databaseHelper = null;
    private User user;
    private String userLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("DrugsActivity", "onCreate");
        setContentView(R.layout.activity_drugs);
        alarm = new AlarmManagerBroadcastReceiver();

        getFragmentManager().beginTransaction().replace(R.id.main_to_replace, new LoginRegisterFragment()).disallowAddToBackStack().commit();
        getPermissions();
    }

    @TargetApi(23)
    public void getPermissions() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WAKE_LOCK)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WAKE_LOCK},ALARM);
        }
    }

    //!!!
    @Override
    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
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



    public void startRepeatingTimer(View view) {
        if(alarm != null){
            alarm.SetAlarm(getApplicationContext());
        }
    }

    public void cancelRepeatingTimer(View view){
        if(alarm != null){
            alarm.CancelAlarm(getApplicationContext());
        }
    }

    public void onetimeTimer(View view){
        if(alarm != null){
            alarm.setOnetimeAlarm(getApplicationContext());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("DrugsActivity", "OnResume");
        refreshUser();
    }

    public  void refreshUser(){
        try{
            PreparedQuery<User> q= getHelper().getUserDao().queryBuilder().where().eq(User.LOGIN_COLUMN, userLogin).prepare();
            user=getHelper().getUserDao().queryForFirst(q);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void setUser(User u){
        this.user = u;
    }
    public User getUser(){
        refreshUser();
        return  user;
    }

}
