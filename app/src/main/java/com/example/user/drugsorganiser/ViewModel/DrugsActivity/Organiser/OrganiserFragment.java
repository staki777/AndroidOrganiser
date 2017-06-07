package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser;


import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.Shared.DosesManagement;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.BaseDrugsActivityFragment;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.LoginRegister.LoginRegisterFragment;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.ContactPerson.ContactPersonFragment;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.MyDrugs.AddEditDrug.AddEditDrugFragment;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.MyDrugs.MyDrugsFragment;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.Registry.RegistryFragment;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.Schedule.ScheduleFragment;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.SaveSharedPreference;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrganiserFragment extends BaseDrugsActivityFragment implements NavigationView.OnNavigationItemSelectedListener {

    final public static String ACCEPTED = "isDoseAccepted";
    private static final int ALARM_PERM = 1;

    public OrganiserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(LogTag(), "onCreateVIew");
        View v = inflater.inflate(R.layout.fragment_organiser, container, false);
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) v.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) v.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setHasOptionsMenu(true);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(LogTag(), "onStart");
        DosesManagement dm = new DosesManagement(activity());
        dm.updateUserAlarms(activity().getUser());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String contactName;

        if(activity().getUser() == null){
            contactName = savedInstanceState.getString("contactName");
        }else {
            contactName =  activity().getUser().contactName;
        }
        boolean loggedInAndNotRecover = !(savedInstanceState !=null && savedInstanceState.getBoolean("recover")==true)
                && SaveSharedPreference.getUserID(getActivity()) != -1;
        if(loggedInAndNotRecover && (contactName==null || contactName.isEmpty())) {
            activity().replaceWithNewOrExisting(R.id.toReplace, new ContactPersonFragment());
        }
        else if(loggedInAndNotRecover) {
            activity().replaceWithNewOrExisting(R.id.toReplace, new ScheduleFragment());
        }
        getPermissionToAlarm();


    }

    public void getPermissionToAlarm() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WAKE_LOCK)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WAKE_LOCK},ALARM_PERM);
        }else {
            Log.i(LogTag(),"Alarm permission granted");
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item)  {

        int id = item.getItemId();

        activity().refreshUserData();

        if (id == R.id.nav_my_drugs) {
            activity().replaceWithNewOrExisting(R.id.toReplace, new MyDrugsFragment());

        }
        else if (id == R.id.schedule) {
            activity().replaceWithNewOrExisting(R.id.toReplace, new ScheduleFragment());

        }
        else if (id == R.id.last_doses) {
            activity().replaceWithNewOrExisting(R.id.toReplace, new RegistryFragment());
        }
        else if (id == R.id.contact_person) {
            activity().replaceWithNewOrExisting(R.id.toReplace, new ContactPersonFragment());

        }
        else if (id == R.id.nav_logout) {
            SaveSharedPreference.clearPreferences(getActivity());
            activity().setUser(null);
            Log.i(LogTag(), "user set to null");
            activity().replaceWithNewOrExisting(R.id.main_to_replace, new LoginRegisterFragment());
            activity().removeIfExists(MyDrugsFragment.class.getSimpleName());
            activity().removeIfExists(ScheduleFragment.class.getSimpleName());
            activity().removeIfExists(RegistryFragment.class.getSimpleName());
            activity().removeIfExists(ContactPersonFragment.class.getSimpleName());
            activity().removeIfExists(AddEditDrugFragment.class.getSimpleName());
        }

        DrawerLayout drawer = (DrawerLayout) getView().findViewById(R.id.drawer_layout);
        if(drawer != null)
          drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.drugs, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            //TODO: settings
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.i(LogTag(), "OnResume");
        activity().refreshUserData();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("recover", true);
        outState.putString("contactName", activity().getUser().contactName);
    }

}
