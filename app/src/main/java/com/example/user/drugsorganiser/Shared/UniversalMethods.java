package com.example.user.drugsorganiser.Shared;

import org.joda.time.DateTime;

/**
 * Created by user on 2017-05-26.
 */

public class UniversalMethods {
    public static String DateTimeToString(DateTime date){
        return (date.getDayOfMonth()<10?"0":"")+date.getDayOfMonth()+"."+(date.getMonthOfYear()<10?"0":"")+date.getMonthOfYear()+"."+date.getYear()+" "+date.getHourOfDay()+":"+((date.getMinuteOfHour()<10)?"0":"")+date.getMinuteOfHour();
    }
}
