package com.example.user.drugsorganiser.ViewModel.DrugsActivity;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.example.user.drugsorganiser.DataBase.DatabaseHelper;
import com.example.user.drugsorganiser.Model.Drug;
import com.example.user.drugsorganiser.Model.RegistryDose;
import com.example.user.drugsorganiser.Model.SpecificDose;
import com.example.user.drugsorganiser.Model.User;
import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.Shared.UniversalMethods;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.Alarm.AlarmActivity;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.Alarm.AlarmManagerBroadcastReceiver;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.LoginRegister.LoginRegisterFragment;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.OrganiserFragment;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;

import org.joda.time.DateTime;

import java.sql.SQLException;
import java.util.Locale;


public class DrugsActivity extends AppCompatActivity {
//        implements NavigationView.OnNavigationItemSelectedListener

    final public static String DRUG = "drugName";
    final public static String USER = "userName";
    final public static String ALARM = "alarm";
    final public static String ALARM_ACTIVITY = "alarmActivity";
    final public static String SMS = "sms";
    final public static String SMS_ALERT = "smsAlert";
    final public static String DOSE_DETAILS = "details";
    final public static String REQUEST_CODE = "requestCode";
    final public static String DESCRIPTION = "description";
    final public static String ACCEPTED = "isDoseAccepted";

    private AlarmManagerBroadcastReceiver alarm;
    private DatabaseHelper databaseHelper = null;
    private User user;
    private Drug editedDrug;

    // ACTIVITY MANAGEMENT

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("DrugsActivity", "onCreate");
        setContentView(R.layout.activity_drugs);

        if (android.os.Build.VERSION.SDK_INT >=17){
            Log.i("DrugsActivity", "Locale will be set.");
            setLocale();
        }

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

        if (bundle != null && bundle.getBoolean(ALARM_ACTIVITY, Boolean.FALSE)) {
            startAlarmActivity(bundle);
        } else if (bundle != null && bundle.getBoolean(ALARM, Boolean.FALSE)) {
            reactOnLastAlarm(bundle);
            Log.i("DrugsActivity", "bundle ALARM");
        }

        alarm = new AlarmManagerBroadcastReceiver(this);
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
                Log.i("DrugsActivity", "Super.OnBackPressed");
                super.onBackPressed();
                Log.i("DrugsActivity", "After Super.OnBackPressed");

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
        if (getIntent().getBooleanExtra(ALARM_ACTIVITY, Boolean.FALSE)) {
            startAlarmActivity(getIntent().getExtras());
        } else
            if (getIntent().getBooleanExtra(ALARM, Boolean.FALSE)) {
            reactOnLastAlarm(getIntent().getExtras());
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

    public  void setAlarmForDose(SpecificDose specificDose){
        Log.i("DrugsActivity", "Alarm for dose: "+specificDose.toString()+" will be set.");
        String comment = specificDose.drug.doseQuantity + " " +specificDose.drug.doseDescription+"\n"+specificDose.drug.comment;
        specificDose.alarmId = startOnetimeAlarm(getCurrentFocus(), specificDose.drug.name, comment, specificDose.doseDate.getMillis());
        if (specificDose.alarmId == -1) {
            specificDose.alarmId = 0;
        }
        updateSpecificDose(specificDose);
        Log.i("DrugsActivity", "Alarm for dose: "+specificDose.toString()+" is set with code: "+specificDose.alarmId+".");
    }

    public void cancelAlarmForDose(SpecificDose specificDose){
        Log.i("DrugsActivity", "Alarm for dose: "+specificDose.toString()+" will be canceled.");
        cancelAlarm(getCurrentFocus(), specificDose.alarmId);
        Log.i("DrugsActivity","RequestCode " + specificDose.alarmId + " is " + (alarm.freeRequestCode(specificDose.alarmId) ? "free" : "not free"));
        specificDose.alarmId = 0;
        updateSpecificDose(specificDose);
    }

    private boolean updateSpecificDose(SpecificDose specificDose) {
        try {
            this.getHelper().getSpecificDoseDao().update(specificDose);
            return true;
        }
        catch (Exception e){
            Log.i(this.getClass().getSimpleName(), e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private int startRepeatingAlarm(View view, String drugName, String description, long interval, long trigger) {
        if(alarm != null){
            return alarm.setAlarm(getApplicationContext(), drugName, description, user.login, trigger, interval);
        } else {
            return -1;
        }
    }

    private void cancelAlarm(View view, int requestCode){
        if(alarm != null){
            alarm.cancelAlarm(getApplicationContext(), requestCode);
        }
    }

    private int startOnetimeAlarm(View view, String drugName, String description, long trigger){
        if(alarm != null) {
            return alarm.setOnetimeAlarm(getApplicationContext(), drugName, description, user.login, trigger);
        } else {
            return -1;
        }
    }

    private void startAlarmActivity(Bundle bundle) {
        Intent newIntent = new Intent(this, AlarmActivity.class);
        newIntent.putExtra(ALARM, bundle.getBoolean(ALARM, Boolean.FALSE));
        newIntent.putExtra(SMS, bundle.getBoolean(SMS, Boolean.FALSE));
        newIntent.putExtra(DRUG, bundle.getString(DRUG));
        newIntent.putExtra(DESCRIPTION, bundle.getString(DESCRIPTION));
        newIntent.putExtra(SMS_ALERT, bundle.getString(SMS_ALERT));
        newIntent.putExtra(DOSE_DETAILS, bundle.getString(DOSE_DETAILS));
        newIntent.putExtra(REQUEST_CODE, bundle.getInt(REQUEST_CODE));
        newIntent.putExtra(USER, bundle.getString(USER));
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //newIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(newIntent);
        Log.i("DrugsActivity", "bundle ALARM_ACTIVITY");
        if (bundle.getBoolean(ALARM, Boolean.FALSE)) {
            String title = String.format(getString(R.string.alarm_notification_title), bundle.getString(USER), bundle.getString(DRUG));
            String comment = String.format(getString(R.string.alarm_notification_comment), bundle.getString(DESCRIPTION));
            NotificationManagement.CreateNotification(this, title, comment, DrugsActivity.class, 2, 1001, 2, null);
        }
    }

    private void reactOnLastAlarm(Bundle bundle){
        bundle.remove(ALARM);
        String userName = bundle.getString(USER,"");
        String drugName = bundle.getString(DRUG,"");
        String description = bundle.getString(DESCRIPTION,"");
        int requestCode = bundle.getInt(REQUEST_CODE, 0);
        boolean isDoseAccepted = bundle.getBoolean(ACCEPTED,false);
        Log.i("DrugsActivity","Reacting on last alarm with requestCode " + requestCode);

        SpecificDose specificDose = findSpecificDose(userName, drugName ,requestCode);
        removeDoseToRegistry(specificDose, isDoseAccepted);
        if (alarm == null) {
            alarm = new AlarmManagerBroadcastReceiver(this);
        }
        Log.i("DrugsActivity","RequestCode " + requestCode + " is " + (alarm.freeRequestCode(requestCode) ? "free" : "not free"));
        //TODO: React on information about dose (accepted/rejected).
        if (!isDoseAccepted) {
            sendMessage(userName, drugName, description);
            Log.i("DrugsActivity","Dose wasn't accepted.");
        }
        NotificationManagement.CancelNotification(this, 2);
    }

    private void removeDoseToRegistry(SpecificDose specificDose, boolean accepted) {
        RegistryDose registryDose = new RegistryDose(specificDose.drug, specificDose.doseDate);
        try {
            final Dao<RegistryDose, Integer> registryDao = this.getHelper().getRegistryDao();
            registryDao.create(registryDose);
            final Dao<SpecificDose, Integer> specificDoseDao = this.getHelper().getSpecificDoseDao();
            specificDoseDao.delete(specificDose);
            Log.i("DrugsActivity","RegistryDose " + registryDose + " is created from " + specificDose + " .");
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private SpecificDose findSpecificDose(String userName, String drugName, int requestCode) {
        SpecificDose specificDose = null;
        try {
            final Dao<User, Integer> userDao = getHelper().getUserDao();
            PreparedQuery<User> q = userDao.queryBuilder().where().eq(User.LOGIN_COLUMN, userName).prepare();
            User user = userDao.queryForFirst(q);
            final Dao<Drug, Integer> drugDao = getHelper().getDrugDao();
            PreparedQuery<Drug> q1 = drugDao.queryBuilder().where().eq(Drug.NAME_COLUMN, drugName).and().eq(Drug.USER_COLUMN, user).prepare();
            Drug drug = drugDao.queryForFirst(q1);
            final Dao<SpecificDose, Integer> specificDosesDao = getHelper().getSpecificDoseDao();
            PreparedQuery<SpecificDose> q2 = specificDosesDao.queryBuilder().where().eq(SpecificDose.ALARM_COLUMN, requestCode).and().eq(SpecificDose.DRUG_COLUMN, drug).prepare();
            specificDose = specificDosesDao.queryForFirst(q2);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return specificDose;
    }

    private void sendMessage(String userName, String drugName, String description) {
        SmsManager smsManager = SmsManager.getDefault();
        String message = String.format(getString(R.string.alert_message), userName, drugName);
        Log.i("AlarmActivity", message);
        //smsManager.sendTextMessage(user.contactNumber, null, message, null, null);
        String comment = String.format(getString(R.string.alert_notification_comment), userName);
        String details = String.format(getString(R.string.sms_alert_details), drugName, description, UniversalMethods.DateTimeToString(new DateTime()));
        NotificationManagement.CreateNotification(getApplicationContext(), getString(R.string.app_name), comment, DrugsActivity.class, 1, 1001, 1, details);
    }

    // USER MANAGEMENT

    public  void refreshUserData(){
        Log.i("DrugsActivity", "refreshUserData");
        try{
            PreparedQuery<User> q= getHelper().getUserDao().queryBuilder().where().eq(User.ID_FIELD, user.userId).prepare();
            user = getHelper().getUserDao().queryForFirst(q);
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

    public Drug getEditedDrug() {
        return editedDrug;
    }

    public void setEditedDrug(Drug editedDrug) {
        this.editedDrug = editedDrug;
    }

    @TargetApi(17)
    private void setLocale(){
        DisplayMetrics dm = getResources().getDisplayMetrics();
        android.content.res.Configuration conf = getResources().getConfiguration();
        conf.setLocale(new Locale("en_EN")); // API 17+ only.
        getResources().updateConfiguration(conf, dm);
    }
}
