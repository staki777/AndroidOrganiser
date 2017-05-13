package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
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
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.DrugsActivity;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.LoginRegister.LoginRegisterFragment;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.ContactPerson.ContactPersonFragment;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.MyDrugs.MyDrugsFragment;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.Registry.RegistryFragment;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.Schedule.ScheduleFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrganiserFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {


    public OrganiserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("OrganiserFragment", "onCreateVIew");
        return inflater.inflate(R.layout.fragment_organiser, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("OrganiserFragment", "onStart");
        Toolbar toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) getView().findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) getView().findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setHasOptionsMenu(true);
        getFragmentManager().beginTransaction().replace(R.id.toReplace, new ScheduleFragment()).disallowAddToBackStack().commit();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item)  {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        ((DrugsActivity)getActivity()).refreshUserDrugs();

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
            ((DrugsActivity)getActivity()).setUser(null);
            Log.i("OrganiserFragment", "user set to null");
            getFragmentManager().beginTransaction().replace(R.id.main_to_replace, new LoginRegisterFragment()).disallowAddToBackStack().commit();

        }

        DrawerLayout drawer = (DrawerLayout) getView().findViewById(R.id.drawer_layout);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            ((DrugsActivity)getActivity()).onetimeTimer(getActivity().getCurrentFocus()); //for tests
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.i("OrganiserFragment", "OnResume");
        ((DrugsActivity)getActivity()).refreshUserDrugs();
    }

}
