package com.example.user.drugsorganiser.Shared;

import org.joda.time.DateTime;

/**
 * Created by user on 2017-05-26.
 */

public class UniversalMethods {
    public static String DateTimeToString(DateTime date){
        return date.getDayOfMonth()+"-"+(date.getMonthOfYear()+1)+"-"+date.getYear()+" "+date.getHourOfDay()+":"+((date.getMinuteOfHour()<10)?"0":"")+date.getMinuteOfHour();
    }
}
