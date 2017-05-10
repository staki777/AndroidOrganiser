package com.example.user.drugsorganiser.ViewModel.DrugsActivity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.user.drugsorganiser.DataBase.DatabaseHelper;
import com.example.user.drugsorganiser.Model.User;
import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.ContactPerson.ContactPersonFragment;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.MyDrugs.MyDrugsFragment;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.Registry.RegistryFragment;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.Schedule.ScheduleFragment;
import com.example.user.drugsorganiser.ViewModel.MainActivity.MainActivity;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.stmt.PreparedQuery;

import java.sql.SQLException;


public class DrugsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int ALARM = 1;

    private AlarmManagerBroadcastReceiver alarm;
    private DatabaseHelper databaseHelper = null;
    private User user;
    private String userLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_drugs);
        alarm = new AlarmManagerBroadcastReceiver();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        userLogin = getIntent().getStringExtra(getString(R.string.current_user));
        try{
            PreparedQuery<User> q= getHelper().getUserDao().queryBuilder().where().eq(User.LOGIN_COLUMN, userLogin).prepare();
            user=getHelper().getUserDao().queryForFirst(q);
        }catch (SQLException e){
            e.printStackTrace();
        }

        getFragmentManager().beginTransaction().replace(R.id.toReplace, new ScheduleFragment()).disallowAddToBackStack().commit();
        getPermissions();
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
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drugs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            onetimeTimer(getCurrentFocus()); //for tests
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        try{
            PreparedQuery<User> q= getHelper().getUserDao().queryBuilder().where().eq(User.LOGIN_COLUMN, userLogin).prepare();
            user=getHelper().getUserDao().queryForFirst(q);
        }catch (SQLException e){
            e.printStackTrace();
        }

        if (id == R.id.nav_my_drugs) {
            getFragmentManager().beginTransaction().replace(R.id.toReplace, new MyDrugsFragment()).disallowAddToBackStack().commit();
          }
        else if (id == R.id.schedule) {
            getFragmentManager().beginTransaction().replace(R.id.toReplace, new ScheduleFragment()).disallowAddToBackStack().commit();
        }
        else if (id == R.id.last_doses) {
            getFragmentManager().beginTransaction().replace(R.id.toReplace, new RegistryFragment()).disallowAddToBackStack().commit();
        }
        else if (id == R.id.contact_person) {
            getFragmentManager().beginTransaction().replace(R.id.toReplace, new ContactPersonFragment()).disallowAddToBackStack().commit();
        }
        else if (id == R.id.nav_logout) {
            Intent intent=new Intent(this, MainActivity.class);
            this.finish();
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

    public User getUser(){
        return  user;
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
        try{
            PreparedQuery<User> q= getHelper().getUserDao().queryBuilder().where().eq(User.LOGIN_COLUMN, userLogin).prepare();
            user=getHelper().getUserDao().queryForFirst(q);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
