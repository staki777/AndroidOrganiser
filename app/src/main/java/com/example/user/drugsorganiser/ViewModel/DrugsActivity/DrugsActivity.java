package com.example.user.drugsorganiser.ViewModel.DrugsActivity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.user.drugsorganiser.DataBase.DatabaseHelper;
import com.example.user.drugsorganiser.Model.User;
import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.LoginRegister.LoginRegisterFragment;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.OrganiserFragment;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("DrugsActivity", "onCreate");
        setContentView(R.layout.activity_drugs);

        if(savedInstanceState!=null){
            int userID=savedInstanceState.getInt("userID");
            Log.i("drugsActivity", "userID from bundle is: "+userID);
            user = findUserByID(userID);

            if(user != null && userID!= -1){
                Log.i("DrugsActivity", "OrganiserFragment will be called");
                removeAndReplaceOldFragment("MAIN", new OrganiserFragment(), R.id.main_to_replace);
                //getFragmentManager().beginTransaction().replace(R.id.main_to_replace, new OrganiserFragment()).disallowAddToBackStack().commit();
            }
            else {
                Log.i("DrugsActivity", "LoginRegisterFragment will be called");
                removeAndReplaceOldFragment("MAIN", new LoginRegisterFragment(), R.id.main_to_replace);
                //getFragmentManager().beginTransaction().replace(R.id.main_to_replace, new LoginRegisterFragment()).disallowAddToBackStack().commit();
            }
        }
        else {
            Log.i("DrugsActivity", "LoginRegisterFragment will be called");
            removeAndReplaceOldFragment("MAIN", new LoginRegisterFragment(), R.id.main_to_replace);
            //getFragmentManager().beginTransaction().replace(R.id.main_to_replace, new LoginRegisterFragment()).disallowAddToBackStack().commit();
        }

        alarm = new AlarmManagerBroadcastReceiver();
        getPermissions();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i("DrugsActivity", "onSaveInstanceState");
        super.onSaveInstanceState(outState);
        outState.putInt("userID",  user != null ? user.userId : -1);
    }

    @TargetApi(23)
    public void getPermissions() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WAKE_LOCK)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WAKE_LOCK},ALARM);
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer!=null){
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
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



    public void startRepeatingTimer(View view, String drugName, String description) {
        if(alarm != null){
            alarm.SetAlarm(getApplicationContext(), drugName, description);
        }
    }

    public void cancelRepeatingTimer(View view){
        if(alarm != null){
            alarm.CancelAlarm(getApplicationContext());
        }
    }

    public void onetimeTimer(View view, String drugName, String description){
        if(alarm != null){
            alarm.setOnetimeAlarm(getApplicationContext(), drugName, description);
        }
    }

    public  void refreshUserDrugs(){
        Log.i("DrugsActivity", "refreshUserDrugs");
        try{
            PreparedQuery<User> q= getHelper().getUserDao().queryBuilder().where().eq(User.ID_FIELD, user.userId).prepare();
            user.drugs=getHelper().getUserDao().queryForFirst(q).drugs;
        }
        catch (SQLException e){
            e.printStackTrace();
            e.toString();
        }
    }
    private  User findUserByID(int userID){
        try{
            PreparedQuery<User> q= getHelper().getUserDao().queryBuilder().where().eq(User.ID_FIELD, userID).prepare();
            return getHelper().getUserDao().queryForFirst(q);
        }
        catch (SQLException e){
            e.printStackTrace();
            e.toString();
        }
        return null;
    }
    public void setUser(User u){
        this.user = u;
    }
    public User getUser(){
        return  user;
    }

    public void  removeAndReplaceOldFragment(String tag, Fragment newFragment, int containerID){
        removeFragment(tag);
        getFragmentManager().beginTransaction().replace(containerID, newFragment, tag).disallowAddToBackStack().commit();
    }
    public void removeFragment(String tag){
        Fragment old =getFragmentManager().findFragmentByTag(tag);
        if(old != null){
            Log.i("Fragment removing", "old fragment: "+tag+" found!");
            getFragmentManager().beginTransaction().remove(old).commitAllowingStateLoss();
        }
    }

    public void restoreExistingFragment(String tag, Fragment emergencyFragment, int containerID){
        Fragment old =getFragmentManager().findFragmentByTag(tag);
        if(old == null){
            getFragmentManager().beginTransaction().replace(containerID, emergencyFragment, tag).disallowAddToBackStack().commit();
            Log.i("FragmentRestoring", "existing fragment with tag: "+tag+" not found, emergencyFragment will be used");
        }
        else {
            Log.i("FragmentRestoring", "existing fragment with tag: "+tag+" found: "+old.getClass().getName());
            try {
                getFragmentManager().beginTransaction().replace(containerID, old.getClass().newInstance(), tag).disallowAddToBackStack().commit();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}
