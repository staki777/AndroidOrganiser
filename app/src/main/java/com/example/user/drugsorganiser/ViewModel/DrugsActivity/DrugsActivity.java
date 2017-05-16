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

        //user retrieval
        if(savedInstanceState!=null){
            int userID=savedInstanceState.getInt("userID");
            Log.i("drugsActivity", "userID from bundle is: "+userID);
            user = findUserByID(userID);
        }
        else {
            Log.i("DrugsActivity", "LoginRegisterFragment will be called");
            replaceWithNewOrExisting(R.id.main_to_replace, new LoginRegisterFragment());
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


    public void replaceWithNewOrExisting(int containerID, Fragment newInstance){
        String tag = newInstance.getClass().getSimpleName();
        Fragment existing = getFragmentManager().findFragmentByTag(tag);

        if( existing == null) {
            Log.i("Main", "Fragment with tag: "+tag+" was a null, new instance will be created.");
            existing = newInstance;
        }
        else
            Log.i("Main", "Fragment with tag: "+tag+" was found, new instance wont be created.");
        getFragmentManager().beginTransaction().replace(containerID, existing, tag).disallowAddToBackStack().commit();

    }

    public void replaceWithNewAndAddToBackStack(int containerID, Fragment newInstance){
        String tag = newInstance.getClass().getSimpleName();
        getFragmentManager().beginTransaction().replace(containerID, newInstance, tag).addToBackStack(null).commit();
    }

    public void removeIfExists(String tag){
        Fragment existing = getFragmentManager().findFragmentByTag(tag);
        if(existing != null){
            getFragmentManager().beginTransaction().remove(existing).commit();
        }
    }


}
