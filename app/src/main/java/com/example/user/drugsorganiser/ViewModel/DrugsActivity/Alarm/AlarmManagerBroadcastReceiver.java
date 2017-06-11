package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.widget.Toast;

import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.Shared.DosesManagement;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.DrugsActivity;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.NotificationManagement;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.RequestCodeGenerator;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;

import static android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
import static com.example.user.drugsorganiser.ViewModel.DrugsActivity.DrugsActivity.ALARM;
import static com.example.user.drugsorganiser.ViewModel.DrugsActivity.DrugsActivity.ALARM_ACTIVITY;
import static com.example.user.drugsorganiser.ViewModel.DrugsActivity.DrugsActivity.DESCRIPTION;
import static com.example.user.drugsorganiser.ViewModel.DrugsActivity.DrugsActivity.DRUG;
import static com.example.user.drugsorganiser.ViewModel.DrugsActivity.DrugsActivity.REQUEST_CODE;
import static com.example.user.drugsorganiser.ViewModel.DrugsActivity.DrugsActivity.USER;

/**
 * Created by DV7 on 2017-04-26.
 */

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {

    final public static String ONE_TIME = "onetime";
    private RequestCodeGenerator requestCodeGenerator;

    public AlarmManagerBroadcastReceiver() {
        requestCodeGenerator = null;
    }

    public AlarmManagerBroadcastReceiver(Context context) {
        DosesManagement dosesManagement = new DosesManagement((DrugsActivity)context);
        HashSet<Integer> usedRequestCodes = dosesManagement.findAllUsedRequestCodes();
        requestCodeGenerator = new RequestCodeGenerator(usedRequestCodes);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "ALARM");

        wl.acquire();
        //You can do the processing here update the widget/remote views.
        Bundle extras = intent.getExtras();
        StringBuilder msgStr = new StringBuilder();
        boolean oneTime = false;
        int requestCode = 0;
        String drugName = "Drug", description = "", user = "";
        if(extras != null) {
            oneTime = extras.getBoolean(ONE_TIME);
            msgStr.append("One time alarm : ");
            drugName = extras.getString(DRUG, "Drug");
            requestCode = extras.getInt(REQUEST_CODE);
            description = extras.getString(DESCRIPTION);
            user = extras.getString(USER);
        }
        Format formatter = new SimpleDateFormat("HH:mm:ss a");
        msgStr.append(formatter.format(new Date()));
        Toast.makeText(context, msgStr, Toast.LENGTH_LONG).show();

        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        Intent ringtoneIntent = new Intent(context, RingtonePlayingService.class);
        ringtoneIntent.putExtra("ringtone-uri", alarmUri.toString());
        context.startService(ringtoneIntent);
        Intent newIntent = new Intent(context, DrugsActivity.class);
        newIntent.putExtra(ALARM, Boolean.TRUE);
        newIntent.putExtra(ALARM_ACTIVITY, Boolean.TRUE);
        newIntent.putExtra(DRUG, drugName);
        newIntent.putExtra(DESCRIPTION, description);
        newIntent.putExtra(REQUEST_CODE, requestCode);
        newIntent.putExtra(USER, user);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//  | FLAG_ACTIVITY_CLEAR_TOP /Intent.FLAG_ACTIVITY_NEW_TASK/FLAG_ACTIVITY_BROUGHT_TO_FRONT
        context.startActivity(newIntent);

        wl.release();
    }

    public int setAlarm(Context context, String drugName, String description, String userName, long trigger, long interval) {
        int requestCode = requestCodeGenerator != null ? requestCodeGenerator.getNextAlarmRequestCode() : 0;
        AlarmManager alarmManager=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        intent.putExtra(ONE_TIME, Boolean.FALSE);
        intent.putExtra(DRUG, drugName);
        intent.putExtra(DESCRIPTION, description);
        intent.putExtra(USER, userName);
        intent.putExtra(REQUEST_CODE, requestCode);
        PendingIntent pi = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, trigger, interval , pi); //AlarmManager.INTERVAL_DAY
        return requestCode;
    }

    public void cancelAlarm(Context context, int requestCode) {
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    public boolean freeRequestCode(int requestCode) {
        if (requestCodeGenerator != null) {
            requestCodeGenerator.notifyRequestCodeUnused(requestCode);
            return true;
        }
        return false;
    }

    public int setOnetimeAlarm(Context context, String drugName, String description, String userName, long trigger){
        int requestCode = requestCodeGenerator != null ? requestCodeGenerator.getNextAlarmRequestCode() : 0;
        AlarmManager alarmManager =(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        intent.putExtra(ONE_TIME, Boolean.TRUE);
        intent.putExtra(DRUG, drugName);
        intent.putExtra(DESCRIPTION, description);
        intent.putExtra(USER, userName);
        intent.putExtra(REQUEST_CODE, requestCode);
        PendingIntent pi = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, pi);
        return requestCode;
    }
}