package com.example.user.drugsorganiser.Shared;

import android.support.v4.util.Pair;

import org.joda.time.DateTime;

/**
 * Created by user on 2017-05-26.
 */

public class UniversalMethods {
    public static String DateTimeToString(DateTime date){
        return (date.getDayOfMonth()<10?"0":"")+date.getDayOfMonth()+"."+(date.getMonthOfYear()<10?"0":"")+date.getMonthOfYear()+"."+date.getYear()+" "+date.getHourOfDay()+":"+((date.getMinuteOfHour()<10)?"0":"")+date.getMinuteOfHour();
    }

    public static int computeInterval(int pickerValue, int spinnerPos){
        //minutes, hours, days, months, years
        int[] multiplicators = {1, 60, 60*24, 60*24*30, 60*24*30*256};
        return  pickerValue * multiplicators[spinnerPos];
    }
    public static Pair<Integer, Integer> translateInterval(int interval){
        if(interval <= 0)
            throw new IllegalArgumentException("Argument cannot be les or equal 0.");
        //Log.i("UniversalMethods", "Translating interval: "+interval);
        int[] multiplicators = {1, 60, 60*24, 60*24*30, 60*24*30*256};

        for(int i = multiplicators.length-1; i>0; i--){
            if(interval % multiplicators[i] == 0){
                return new Pair<>(i, interval/multiplicators[i]);
            }
        }
        return  new Pair<>(0, interval);
    }
}
