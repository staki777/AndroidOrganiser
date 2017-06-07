package com.example.user.drugsorganiser.ViewModel.DrugsActivity;

import android.util.Log;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by DV7 on 2017-05-31.
 */

public class RequestCodeGenerator {

    private final static int maxNotificationRequestCodes = 300;

    private int nextAlarmRequestCode;
    private int nextNotificationRequestCode;
    private List<Integer> unusedAlarmRequestCodes;
    private List<Integer> unusedNotificationRequestCodes;

    public RequestCodeGenerator(HashSet<Integer> usedRequestCodes) {
        setUnusedRequestCodes(usedRequestCodes);
    }

    public int getNextAlarmRequestCode() {
        return unusedAlarmRequestCodes.size() > 0 ? unusedAlarmRequestCodes.remove(0) : nextAlarmRequestCode++;
    }

    public int getNextNotificationRequestCode() {
        return unusedNotificationRequestCodes.size() > 0 ? unusedNotificationRequestCodes.remove(0) : nextNotificationRequestCode++;
    }

    public void notifyRequestCodeUnused(int requestCode) {
        if (requestCode <= maxNotificationRequestCodes) {
            addAndSort(unusedNotificationRequestCodes, requestCode);
        } else {
            addAndSort(unusedAlarmRequestCodes, requestCode);
        }
        Log.i("RequestCodeGenerator", "notify unused " + requestCode + " actual lists: notifs = " + unusedNotificationRequestCodes +
                " alarms = " + unusedAlarmRequestCodes);
    }

    public void setUnusedRequestCodes(HashSet<Integer> usedRequestCodes) {
        nextAlarmRequestCode = maxNotificationRequestCodes + 1;
        nextNotificationRequestCode = 1;
        unusedAlarmRequestCodes = new LinkedList<>();
        unusedNotificationRequestCodes = new LinkedList<>();
        int lastUsedNotificationCode = 0;
        int lastUsedAlarmCode = maxNotificationRequestCodes;
        for (Object i : usedRequestCodes.toArray()) {
            int code = (Integer)i;
            if (code <= maxNotificationRequestCodes) {
                nextNotificationRequestCode = code + 1;
                for (int j = lastUsedNotificationCode + 1; j < code; j++) {
                    unusedNotificationRequestCodes.add(j);
                }
                lastUsedNotificationCode = code;
            } else {
                nextAlarmRequestCode = code + 1;
                for (int j = lastUsedAlarmCode + 1; j < code; j++) {
                    unusedAlarmRequestCodes.add(j);
                }
                lastUsedAlarmCode = code;
            }
        }
        Log.i("RequestCodeGenerator", "used " + usedRequestCodes);
    }

    private void addAndSort(List<Integer> list, int requestCode) {
        list.add(requestCode);

        Object[] codes = list.toArray();
        Arrays.sort(codes);
        list.clear();
        for(Object p : codes){
            list.add((Integer)p);
        }

        Set s = new HashSet<Integer>(list);
        list.clear();
        for(Object p : s.toArray()){
            list.add((Integer)p);
        }
    }
}
