package com.example.user.drugsorganiser.Shared;

import android.support.v4.util.Pair;
import android.util.Log;

import com.example.user.drugsorganiser.Model.ConstantIntervalDose;
import com.example.user.drugsorganiser.Model.CustomDose;
import com.example.user.drugsorganiser.Model.Drug;
import com.example.user.drugsorganiser.Model.RegularDose;
import com.example.user.drugsorganiser.Model.User;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.DrugsActivity;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

    public List<Pair<Drug,DateTime>> findCustomDosesForNext24h(User u){
        Log.i("DosesManagement", "Finding custom doses...");
        DateTime now = DateTime.now();
        DateTime tomorrow = now.plusDays(1);
        List<Pair<Drug,DateTime>> cds = new ArrayList<>();
        Log.i("DosesManagement", "This user has: "+u.drugs.size()+" drugs.");

        for(Drug d : u.drugs){
            Log.i("DosesManagement", d.toString()+" has "+d.customDoses.size()+" custom doses.");
            if(d.dosesSeriesType == 2){
                for(CustomDose cd : d.customDoses){
                    Log.i("DosesManagement", cd.toString());
                    if(cd.doseDate.isAfter(now) && cd.doseDate.isBefore(tomorrow)){
                        cds.add(new Pair<Drug, DateTime>(cd.drug, cd.doseDate));
                        Log.i("DosesManagement", "Dose added: "+cd.toString());
                    }
                }
            }
        }
        return  cds;
    }

    public List<Pair<Drug,DateTime>> findConstantIntervalDosesForNext24h(User u){
        DateTime now = DateTime.now();
        DateTime tomorrow = now.plusDays(1);
        List<Pair<Drug, DateTime>> cids = new ArrayList<>();
        for(Drug d : u.drugs){
            if(d.dosesSeriesType == 1){
                ConstantIntervalDose cid = d.constantIntervalDose;
                DateTime startPoint;
                if(cid.lastAcceptedDose == null){
                    startPoint = cid.firstDose;
                }
                else {
                    startPoint = cid.firstDose.isAfter(cid.lastAcceptedDose) ? cid.firstDose : cid.lastAcceptedDose;
                }

                while (! startPoint.isAfter(tomorrow)){
                    if(startPoint.isAfter(now))
                        cids.add(new Pair<Drug, DateTime>(d, startPoint));
                    startPoint = startPoint.plusMinutes(cid.interval);
                }
            }
        }
        return  cids;
    }
    public List<Pair<Drug,DateTime>> findRegularDosesForNext24h(User u){
        DateTime now = DateTime.now();
        DateTime tomorrow = now.plusDays(1);
        List<Pair<Drug, DateTime>> rds = new ArrayList<>();
        for(Drug d : u.drugs) {
            for (RegularDose rd : d.regularDoses) {
                Log.i("DosesManagement", rd.toString());
                if (rd.interval.contentEquals("day")) {
                    DateTime date = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), rd.hour, rd.minute);
                    rds.add(new Pair<Drug, DateTime>(rd.drug, date));
                    Log.i("DosesManagement", "Date added: " + UniversalMethods.DateTimeToString(date));
                } else if (rd.interval.contentEquals("week")) {
                    if (now.getDayOfWeek() == rd.weekDay) {
                        DateTime date = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), rd.hour, rd.minute);
                        rds.add(new Pair<Drug, DateTime>(rd.drug, date));
                        Log.i("DosesManagement", "Date added: " + UniversalMethods.DateTimeToString(date));
                    }
                } else if (rd.interval.contentEquals("month")) {
                    if (now.getDayOfMonth() == rd.monthDay) {
                        DateTime date = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), rd.hour, rd.minute);
                        rds.add(new Pair<Drug, DateTime>(rd.drug, date));
                        Log.i("DosesManagement", "Date added: " + UniversalMethods.DateTimeToString(date));
                    }
                } else if (rd.interval.contentEquals("year")) {
                    if (now.getMonthOfYear() == rd.month && now.getDayOfMonth() == rd.monthDay) {
                        DateTime date = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), rd.hour, rd.minute);
                        rds.add(new Pair<Drug, DateTime>(rd.drug, date));
                        Log.i("DosesManagement", "Date added: " + UniversalMethods.DateTimeToString(date));
                    }
                }
            }
        }
        return  rds;
    }
}
