package com.example.user.drugsorganiser.Shared;

import android.util.Log;

import com.example.user.drugsorganiser.Model.CustomDose;
import com.example.user.drugsorganiser.Model.Drug;
import com.example.user.drugsorganiser.Model.User;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.DrugsActivity;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 2017-05-27.
 */

public class DosesManagement {

    private DrugsActivity ctx;

    public DosesManagement(DrugsActivity ctx){
        this.ctx = ctx;
    }

    public List<User> allUsers(){
        List<User> users = null;
        try{
            users =ctx.getHelper().getUserDao().queryForAll();
            Log.i(getClass().getSimpleName(), Arrays.toString(users.toArray()));
            return users;
        }
        catch (Exception e){
            Log.i(this.getClass().getSimpleName(), e.getMessage());
            e.printStackTrace();
        }
        return  users;
    }

    public List<CustomDose> findCustomDosesForNext24h(User u){
        Log.i("DosesManagement", "Finding custom doses...");
        DateTime now = DateTime.now();
        Log.i("DosesManagement", "Now is: "+now.toString());
        DateTime tomorrow = now.plusDays(1);
        Log.i("DosesManagement", "Tomorrow is: "+tomorrow.toString());
        List<CustomDose> cds = new ArrayList<>();
        Log.i("DosesManagement", "This user has: "+u.drugs.size()+" drugs.");

        for(Drug d : u.drugs){
            Log.i("DosesManagement", d.toString()+" has "+d.customDoses.size()+" custom doses.");
            for(CustomDose cd : d.customDoses){
                Log.i("DosesManagement", cd.toString());
                if(cd.doseDate.isAfter(now) && cd.doseDate.isBefore(tomorrow)){
                    cds.add(cd);
                    Log.i("DosesManagement", "Dose added: "+cd.toString());
                }

            }
        }

        return  cds;
    }
}
