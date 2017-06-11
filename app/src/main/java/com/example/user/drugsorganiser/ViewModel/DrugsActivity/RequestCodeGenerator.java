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
    private List<Integer> unusedAlarmRequestCodes;

    public RequestCodeGenerator(HashSet<Integer> usedRequestCodes) {
        setUnusedRequestCodes(usedRequestCodes);
    }

    public int getNextAlarmRequestCode() {
        return unusedAlarmRequestCodes.size() > 0 ? unusedAlarmRequestCodes.remove(0) : nextAlarmRequestCode++;
    }

    public void notifyRequestCodeUnused(int requestCode) {
        if (requestCode > maxNotificationRequestCodes) {
            addAndSort(unusedAlarmRequestCodes, requestCode);
        }
        Log.i("RequestCodeGenerator", "notify unused " + requestCode + " actual lists: alarms = " + unusedAlarmRequestCodes);
    }

    public void setUnusedRequestCodes(HashSet<Integer> usedRequestCodes) {
        nextAlarmRequestCode = maxNotificationRequestCodes + 1;
        unusedAlarmRequestCodes = new LinkedList<>();
        int lastUsedAlarmCode = maxNotificationRequestCodes;
        for (Object i : usedRequestCodes.toArray()) {
            int code = (Integer)i;
            if (code > maxNotificationRequestCodes) {
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
