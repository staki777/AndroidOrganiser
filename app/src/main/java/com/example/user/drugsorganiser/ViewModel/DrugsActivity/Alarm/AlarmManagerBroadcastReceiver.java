package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Alarm;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.DrugsActivity;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by DV7 on 2017-04-26.
 */

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {

    final public static String ONE_TIME = "onetime";
    final public static String DRUG = "drugName";
    final public static String ALARM = "alarm";
    final public static String USER = "userName";
    final public static String DESCRIPTION = "description";

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

        Intent newIntent = new Intent(context, DrugsActivity.class);
        newIntent.putExtra(ALARM, Boolean.TRUE);
        newIntent.putExtra(DRUG, drugName);
        newIntent.putExtra(DESCRIPTION, description);
        newIntent.putExtra(USER, user);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);// | FLAG_ACTIVITY_CLEAR_TOP /Intent.FLAG_ACTIVITY_NEW_TASK/FLAG_ACTIVITY_BROUGHT_TO_FRONT
        context.startActivity(newIntent);

        CreateNotification(context, drugName, description, user, DrugsActivity.class, 1);

        wl.release();
    }

    private void CreateNotification (Context context, String drugName, String drugDescription, String user, Class <?> cls, int id) {
        Intent intent = new Intent(context, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);//Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent notifIntent = PendingIntent.getActivity(context,1001, intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.drawable.ic_alarm_on_black_24dp);
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_alarm_on_black_24dp));
        mBuilder.setContentTitle(user + ", czas na " + drugName);
        mBuilder.setContentText("Pamiętaj: " + drugDescription);
        mBuilder.setContentIntent(notifIntent);
        mBuilder.setAutoCancel(true);
        mBuilder.setVibrate(new long[]{1000, 1000, 1000});
        mBuilder.setWhen(System.currentTimeMillis());

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(id, mBuilder.build());
    }

    public void SetAlarm(Context context, String drugName, String description, String userName, long trigger, long interval) {
        AlarmManager alarmManager=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        intent.putExtra(ONE_TIME, Boolean.FALSE);
        intent.putExtra(DRUG, drugName);
        intent.putExtra(DESCRIPTION, description);
        intent.putExtra(USER, userName);
        PendingIntent pi = PendingIntent.getBroadcast(context, 1001, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, trigger, interval , pi); //AlarmManager.INTERVAL_DAY
    }

    public void CancelAlarm(Context context) {
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 1001, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    public void setOnetimeAlarm(Context context, String drugName, String description, String userName, long trigger){
        AlarmManager alarmManager =(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        intent.putExtra(ONE_TIME, Boolean.TRUE);
        intent.putExtra(DRUG, drugName);
        intent.putExtra(DESCRIPTION, description);
        intent.putExtra(USER, userName);
        PendingIntent pi = PendingIntent.getBroadcast(context, 1001, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, pi);
    }
}