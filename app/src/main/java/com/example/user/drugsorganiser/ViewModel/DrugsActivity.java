package com.example.user.drugsorganiser.ViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.user.drugsorganiser.DataBase.DatabaseHelper;
import com.example.user.drugsorganiser.Model.User;
import com.example.user.drugsorganiser.R;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.stmt.PreparedQuery;

import java.sql.SQLException;


public class DrugsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseHelper databaseHelper = null;
    private User user;
    private String userLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drugs);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_my_drugs) {
            getFragmentManager().beginTransaction().replace(R.id.toReplace, new MyDrugsFragment()).disallowAddToBackStack().commit();
          }
        else if (id == R.id.schedule) {
            getFragmentManager().beginTransaction().replace(R.id.toReplace, new ScheduleFragment()).disallowAddToBackStack().commit();
        }
        else if (id == R.id.contact_person) {
            getFragmentManager().beginTransaction().replace(R.id.toReplace, new ContactPersonFragment()).disallowAddToBackStack().commit();
        }
        else if (id == R.id.nav_logout) {
            Intent intent=new Intent(this, MainActivity.class);
            this.finish();
            startActivity(intent);
        }
        //else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

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
