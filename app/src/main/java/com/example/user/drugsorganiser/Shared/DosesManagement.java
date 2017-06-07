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
import com.j256.ormlite.stmt.PreparedQuery;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
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
                if (rd.interval.contentEquals("day")) {
                    DateTime date = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), rd.hour, rd.minute);
                    if(date.isAfter(now)) {
                        rds.add(new Pair<Drug, DateTime>(rd.drug, date));
                        Log.i("DosesManagement", "Date added: " + UniversalMethods.DateTimeToString(date));
                    }
                    else {
                        date = new DateTime(tomorrow.getYear(), tomorrow.getMonthOfYear(), tomorrow.getDayOfMonth(), rd.hour, rd.minute);
                        rds.add(new Pair<Drug, DateTime>(rd.drug, date));
                        Log.i("DosesManagement", "Date added: " + UniversalMethods.DateTimeToString(date));
                    }

                } else if (rd.interval.contentEquals("week")) {
                    if (now.getDayOfWeek() == rd.weekDay) {
                        DateTime date = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), rd.hour, rd.minute);
                        if(date.isAfter(now)) {
                            rds.add(new Pair<Drug, DateTime>(rd.drug, date));
                            Log.i("DosesManagement", "Date added: " + UniversalMethods.DateTimeToString(date));
                        }
                    }
                    else if(tomorrow.getDayOfWeek() == rd.weekDay) {
                        DateTime date = new DateTime(tomorrow.getYear(), tomorrow.getMonthOfYear(), tomorrow.getDayOfMonth(), rd.hour, rd.minute);
                        if (date.isBefore(tomorrow)) {
                            rds.add(new Pair<Drug, DateTime>(rd.drug, date));
                            Log.i("DosesManagement", "Date added: " + UniversalMethods.DateTimeToString(date));
                        }
                    }
                } else if (rd.interval.contentEquals("month")) {
                    if (now.getDayOfMonth() == rd.monthDay) {
                        DateTime date = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), rd.hour, rd.minute);
                        if(date.isAfter(now)) {
                            rds.add(new Pair<Drug, DateTime>(rd.drug, date));
                            Log.i("DosesManagement", "Date added: " + UniversalMethods.DateTimeToString(date));
                        }
                    }
                    else if(tomorrow.getDayOfMonth() == rd.monthDay) {
                        DateTime date = new DateTime(tomorrow.getYear(), tomorrow.getMonthOfYear(), tomorrow.getDayOfMonth(), rd.hour, rd.minute);
                        if(date.isBefore(tomorrow)){
                            rds.add(new Pair<Drug, DateTime>(rd.drug, date));
                            Log.i("DosesManagement", "Date added: " + UniversalMethods.DateTimeToString(date));
                        }
                    }
                } else if (rd.interval.contentEquals("year")) {
                    if (now.getMonthOfYear() == rd.month && now.getDayOfMonth() == rd.monthDay) {
                        DateTime date = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), rd.hour, rd.minute);
                        if(date.isAfter(now)) {
                            rds.add(new Pair<Drug, DateTime>(rd.drug, date));
                            Log.i("DosesManagement", "Date added: " + UniversalMethods.DateTimeToString(date));
                        }

                    }
                    else if(now.getMonthOfYear() == rd.month && tomorrow.getDayOfMonth() == rd.monthDay) {
                        DateTime date = new DateTime(tomorrow.getYear(), tomorrow.getMonthOfYear(), tomorrow.getDayOfMonth(), rd.hour, rd.minute);
                        if (date.isBefore(tomorrow)) {
                            rds.add(new Pair<Drug, DateTime>(rd.drug, date));
                            Log.i("DosesManagement", "Date added: " + UniversalMethods.DateTimeToString(date));
                        }
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
            Dao<SpecificDose, Integer> specificDoseDao = ctx.getHelper().getSpecificDoseDao();
            Dao<Drug, Integer> drugDao = ctx.getHelper().getDrugDao();

            List<Pair<Drug,DateTime>> doses = findAllDosesForNext24H(u);
            for (Pair<Drug,DateTime> d : doses){
                SpecificDose cd = new SpecificDose(d.first, d.second);
                if(!d.first.nearestDoses.contains(cd)){
                    specificDoseDao.create(cd);
                    ctx.setAlarmForDose(cd);
                    drugDao.update(d.first);
                }
            }
        }catch (Exception e){
            Log.e("DosesManagement", e.getMessage());
            e.printStackTrace();
        }

    }

    public void cancelAllAlarmsForDrug(User u, Drug d){
        Log.i("DosesManagement", "Canceling user alarms for drug...");

        try {
            Dao<SpecificDose, Integer> specificDoseDao = ctx.getHelper().getSpecificDoseDao();
            Dao<Drug, Integer> drugDao = ctx.getHelper().getDrugDao();
            PreparedQuery<Drug> q1 = drugDao.queryBuilder().where().eq(Drug.NAME_COLUMN, d.name).and().eq(Drug.USER_COLUMN, u).prepare();
            Drug drug = drugDao.queryForFirst(q1);
            Dao<User, Integer> userDao = ctx.getHelper().getUserDao();

            for(SpecificDose sd : drug.nearestDoses){
                ctx.cancelAlarmForDose(sd);
            }
            specificDoseDao.delete(drug.nearestDoses);
            drugDao.update(d);
            userDao.update(u);

        }catch (Exception e){
            Log.e("DosesManagement", e.getMessage());
            e.printStackTrace();
        }
    }

    public void setAlarmsForDrug(User user, Drug drug){
        Log.i("DosesManagement", "Updating user alarms for drug...");
        try {
            Dao<SpecificDose, Integer> specificDoseDao = ctx.getHelper().getSpecificDoseDao();
//            Dao<Drug, Integer> drugDao = ctx.getHelper().getDrugDao();
            Dao<User, Integer> userDao = ctx.getHelper().getUserDao();

            List<Pair<Drug,DateTime>> doses = findAllDosesForNext24H(user);
            for (Pair<Drug,DateTime> d : doses){
                SpecificDose cd = new SpecificDose(d.first, d.second);
                if(d.first.drugId == drug.drugId){
                    Log.i("DosesManagement", "We found the drug to set alarms");
                    specificDoseDao.create(cd);
//                    d.first.nearestDoses.add(cd);
//                    drugDao.update(d.first);
                    ctx.setAlarmForDose(cd);
                }
             // userDao.update(user);
            }
        }catch (Exception e){
            Log.e("DosesManagement", e.getMessage());
            e.printStackTrace();
        }
    }

    public void resetAlarmsForDrug(User user, Drug drug){
        Log.i("DosesManagement", "Reseting alarms for drug: "+drug.toString());
        cancelAllAlarmsForDrug(user, drug);
        setAlarmsForDrug(user, drug);
    }

    public HashSet<Integer> findAllUsedRequestCodes() {
        LinkedList<Integer> requestCodes = new LinkedList<>();
        try{
            List<SpecificDose> alarms = ctx.getHelper().getSpecificDoseDao().queryForAll();
            for (Object o : alarms) {
                if (((SpecificDose)o).alarmId != 0) {
                    requestCodes.add(((SpecificDose)o).alarmId);
                }
            }
        }
        catch (Exception e){
            Log.i(this.getClass().getSimpleName(), e.getMessage());
            e.printStackTrace();
        }

        Object[] codes = requestCodes.toArray();
        Arrays.sort(codes);
        requestCodes.clear();
        for(Object p : codes){
            requestCodes.add((Integer)p);
        }

        return new HashSet<Integer>(requestCodes);
    }
}
