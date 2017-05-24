package com.example.user.drugsorganiser.ViewModel.DrugsActivity;

import android.app.Fragment;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;

import com.example.user.drugsorganiser.DataBase.DatabaseHelper;
import com.example.user.drugsorganiser.Model.User;
import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.Alarm.AlarmActivity;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.Alarm.AlarmManagerBroadcastReceiver;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.LoginRegister.LoginRegisterFragment;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.OrganiserFragment;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.stmt.PreparedQuery;

import java.sql.SQLException;


public class DrugsActivity extends AppCompatActivity {
//        implements NavigationView.OnNavigationItemSelectedListener

    final public static String DRUG = "drugName";
    final public static String USER = "userName";
    final public static String ALARM = "alarm";
    final public static String DESCRIPTION = "description";
    final public static String ACCEPTED = "isDoseAccepted";

    private AlarmManagerBroadcastReceiver alarm;
    private DatabaseHelper databaseHelper = null;
    private User user;

    // ACTIVITY MANAGEMENT

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("DrugsActivity", "onCreate");
        setContentView(R.layout.activity_drugs);

        // odkomentować na pierwsze uruchomienie aplikacji po podbiciu wersji bazy danych
        // SaveSharedPreference.clearPreferences(getApplicationContext());

        Bundle bundle = getIntent().getExtras();
        if(savedInstanceState!=null){
            int userID = savedInstanceState.getInt("userID");
            Log.i("DrugsActivity", "userID from bundle is: "+userID);
            user = findUserByID(userID);
        }
        else if (SaveSharedPreference.getUserID(DrugsActivity.this) != -1) {
            Log.i("SAVE_SHARED_PREFERENCE","Getting user ID from sharedPreferences " + (SaveSharedPreference.getUserID(DrugsActivity.this)!=-1));
            int userID = SaveSharedPreference.getUserID(DrugsActivity.this);
            user = findUserByID(userID);
            replaceWithNewOrExisting(R.id.main_to_replace, new OrganiserFragment());
        }
        else {
            Log.i("DrugsActivity", "LoginRegisterFragment will be called");
            replaceWithNewOrExisting(R.id.main_to_replace, new LoginRegisterFragment());
        }

        if (bundle != null && bundle.getBoolean(ALARM, Boolean.FALSE)) {
            ReactOnLastAlarm(bundle);
        }

        alarm = new AlarmManagerBroadcastReceiver();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i("DrugsActivity", "onSaveInstanceState");
        super.onSaveInstanceState(outState);
        outState.putInt("userID",  user != null ? user.userId : -1);
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
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        Log.i("DrugsActivity", "onNewIntent");
        if (getIntent().getBooleanExtra(ALARM, Boolean.FALSE)) {
            ReactOnLastAlarm(getIntent().getExtras());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

    // ALARMS MANAGEMENT

    public void startRepeatingTimer(View view, String drugName, String description, long trigger, long interval) {
        if(alarm != null){
            alarm.SetAlarm(getApplicationContext(), drugName, description, user.login, trigger, interval);
        }
    }

    public void cancelRepeatingTimer(View view){
        if(alarm != null){
            alarm.CancelAlarm(getApplicationContext());
        }
    }

    public void onetimeTimer(View view, String drugName, String description, long trigger){
        if(alarm != null){
            alarm.setOnetimeAlarm(getApplicationContext(), drugName, description, user.login, trigger);
        }
    }

    private void ReactOnLastAlarm(Bundle bundle){
        bundle.remove(ALARM);
        String userName = bundle.getString(USER,"");
        String drugName = bundle.getString(DRUG,"");
        boolean isDoseAccepted = bundle.getBoolean(ACCEPTED,false);
        Log.i("AlarmActivity","Reacting on last alarm.");
        //TODO: React on information about dose (accepted/rejected).
        //sendMessage(userName, drugName);
    }

    private void sendMessage(String userName, String drugsNames) {
        SmsManager smsManager = SmsManager.getDefault();
        String message = String.format(getString(R.string.alert_message), userName, drugsNames);
        Log.i("AlarmActivity", message);
        smsManager.sendTextMessage(user.contactNumber, null, message, null, null);

        String comment = String.format(getString(R.string.alert_notification_comment), userName);
        NotificationManagement.CreateNotification(getApplicationContext(), getString(R.string.app_name), comment, AlarmActivity.class, 2, 1001);
        NotificationManagement.CancelNotification(DrugsActivity.this, 1);
    }

    // USER MANAGEMENT

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

    // FRAGMENTS MANAGEMENT

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

    public void replaceWithNew(int containerID, Fragment newInstance, Boolean addToBackStack){

        String tag = newInstance.getClass().getSimpleName();
        if(addToBackStack){
            getFragmentManager().beginTransaction().replace(containerID, newInstance, tag).addToBackStack(null).commit();
        }
        else {
            getFragmentManager().beginTransaction().replace(containerID, newInstance, tag).disallowAddToBackStack().commit();
        }
    }

    public void removeIfExists(String tag){
        Fragment existing = getFragmentManager().findFragmentByTag(tag);
        if(existing != null){
            getFragmentManager().beginTransaction().remove(existing).commit();
        }
    }


}
