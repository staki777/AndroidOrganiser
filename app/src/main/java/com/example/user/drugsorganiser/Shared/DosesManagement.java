package com.example.user.drugsorganiser.Shared;

import android.support.v4.util.Pair;
import android.util.Log;

import com.example.user.drugsorganiser.Model.ConstantIntervalDose;
import com.example.user.drugsorganiser.Model.CustomDose;
import com.example.user.drugsorganiser.Model.Drug;
import com.example.user.drugsorganiser.Model.RegularDose;
import com.example.user.drugsorganiser.Model.SpecificDose;
import com.example.user.drugsorganiser.Model.User;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.DrugsActivity;
import com.j256.ormlite.dao.Dao;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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

    private List<Pair<Drug,DateTime>> findCustomDosesForNext24h(User u){
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

    private List<Pair<Drug,DateTime>> findConstantIntervalDosesForNext24h(User u){
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
    private List<Pair<Drug,DateTime>> findRegularDosesForNext24h(User u){
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


    public List<Pair<Drug,DateTime>> findAllDosesForNext24H(User u) {
        List<Pair<Drug,DateTime>> doses = new ArrayList<>();
        List<Pair<Drug,DateTime>> customDoses = findCustomDosesForNext24h(u);
        doses.addAll(customDoses);
        List<Pair<Drug,DateTime>> constantIntervalDoses = findConstantIntervalDosesForNext24h(u);
        doses.addAll(constantIntervalDoses);
        List<Pair<Drug,DateTime>> regularDoses = findRegularDosesForNext24h(u);
        doses.addAll(regularDoses);

        //because doses.sort(...) requires higher API :(
        Object[] dosesArr = doses.toArray();
        Arrays.sort(dosesArr, new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                return  ((Pair<Drug, DateTime>)o1).second.compareTo(((Pair<Drug, DateTime>)o2).second);
            }
        });
        doses.clear();
        for(Object p : dosesArr){
            doses.add((Pair<Drug, DateTime>)p);
        }

        return  doses;
    }

    public void updateUserAlarms(User u){
        Log.i("DosesManagement", "Updating user alarms...");
        try {
            Dao<SpecificDose, Integer> specificDoseDao = ctx.getHelper().getCSpecificDoseDao();
            Dao<Drug, Integer> drugDao = ctx.getHelper().getDrugDao();
            Dao<User, Integer> userDao = ctx.getHelper().getUserDao();

            List<Pair<Drug,DateTime>> doses = findAllDosesForNext24H(u);
            for (Pair<Drug,DateTime> d : doses){
                SpecificDose cd = new SpecificDose(d.first, d.second);
                if(!d.first.nearestDoses.contains(cd)){
                    specificDoseDao.create(cd);
                    d.first.nearestDoses.add(cd);
                    drugDao.update(d.first);
                    ctx.setAlarmForDose(cd);
                }
                userDao.update(u);
            }
        }catch (Exception e){
            Log.e("DosesManagement", e.getMessage());
            e.printStackTrace();
        }

    }
}
