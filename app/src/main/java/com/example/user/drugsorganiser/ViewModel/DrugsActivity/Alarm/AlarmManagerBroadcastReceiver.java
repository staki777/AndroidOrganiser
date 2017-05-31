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
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.DrugsActivity;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.NotificationManagement;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.user.drugsorganiser.ViewModel.DrugsActivity.DrugsActivity.ALARM;
import static com.example.user.drugsorganiser.ViewModel.DrugsActivity.DrugsActivity.DESCRIPTION;
import static com.example.user.drugsorganiser.ViewModel.DrugsActivity.DrugsActivity.DRUG;
import static com.example.user.drugsorganiser.ViewModel.DrugsActivity.DrugsActivity.USER;

/**
 * Created by DV7 on 2017-04-26.
 */

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {

    final public static String ONE_TIME = "onetime";

    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "ALARM");

        wl.acquire();
        //You can do the processing here update the widget/remote views.
        Bundle extras = intent.getExtras();
        StringBuilder msgStr = new StringBuilder();
        boolean oneTime = false;
        String drugName = "Drug", description = "", user = "";
        if(extras != null) {
            oneTime = extras.getBoolean(ONE_TIME);
            msgStr.append("One time alarm : ");
            drugName = extras.getString(DRUG, "Drug");
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
        Intent newIntent = new Intent(context, AlarmActivity.class);
        newIntent.putExtra(ALARM, Boolean.TRUE);
        newIntent.putExtra(DRUG, drugName);
        newIntent.putExtra(DESCRIPTION, description);
        newIntent.putExtra(USER, user);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//  | FLAG_ACTIVITY_CLEAR_TOP /Intent.FLAG_ACTIVITY_NEW_TASK/FLAG_ACTIVITY_BROUGHT_TO_FRONT
        context.startActivity(newIntent);

        String title = String.format(context.getString(R.string.alarm_notification_title), user, drugName);
        String comment = String.format(context.getString(R.string.alarm_notification_comment), description);
        NotificationManagement.CreateNotification(context, title, comment, DrugsActivity.class, 1, 1001 );

        wl.release();
    }

    public void SetAlarm(Context context, String drugName, String description, String userName, long trigger, long interval, int requestCode) {
        AlarmManager alarmManager=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        intent.putExtra(ONE_TIME, Boolean.FALSE);
        intent.putExtra(DRUG, drugName);
        intent.putExtra(DESCRIPTION, description);
        intent.putExtra(USER, userName);
        PendingIntent pi = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, trigger, interval , pi); //AlarmManager.INTERVAL_DAY
    }

    public void CancelAlarm(Context context, int requestCode) {
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    public void setOnetimeAlarm(Context context, String drugName, String description, String userName, long trigger, int requestCode){
        AlarmManager alarmManager =(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        intent.putExtra(ONE_TIME, Boolean.TRUE);
        intent.putExtra(DRUG, drugName);
        intent.putExtra(DESCRIPTION, description);
        intent.putExtra(USER, userName);
        PendingIntent pi = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, pi);
    }
}