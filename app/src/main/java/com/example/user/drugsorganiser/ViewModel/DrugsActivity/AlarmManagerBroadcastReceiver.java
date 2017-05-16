package com.example.user.drugsorganiser.ViewModel.DrugsActivity;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.user.drugsorganiser.R;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by DV7 on 2017-04-26.
 */

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {

    final public static String ONE_TIME = "onetime";
    final public static String DRUG = "drugName";
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
        String drugName = "Drug", description = "";
        if(extras != null) {
            oneTime = extras.getBoolean(ONE_TIME);
            msgStr.append("One time alarm : ");
            drugName = extras.getString(DRUG, "Drug");
            description = extras.getString(DESCRIPTION);
        }
        Format formatter = new SimpleDateFormat("HH:mm:ss a");
        msgStr.append(formatter.format(new Date()));
        Toast.makeText(context, msgStr, Toast.LENGTH_LONG).show();

        CreateNotification(context, intent, drugName, description, "AlertMsg", DrugsActivity.class, 1);

        wl.release();
    }

    public  void CreateNotification (Context context, Intent intent, String drugName, String drugDescription, String msgAlert, Class <?> cls, int id) {
        // TODO: Przywrócić aplikację na foreground, jeśli budzik dzwoni, a aplikacja nie jest akurat otwarta
        // TODO: Layout dla alarmu
        //Intent intent = new Intent(context, cls);
//        Intent intent2 = context.getPackageManager().
//                getLaunchIntentForPackage(ContextConstants.PACKAGE_NAME);
        PendingIntent notifIntent = PendingIntent.getActivity(context,0, intent,0);
//        if (intent.resolveActivity(context.getPackageManager()) != null) {
//            Log.i("aaa", "aktywna");
//            //startActivity(intent);
//        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.drawable.ic_alarm_on_black_24dp);
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_alarm_on_black_24dp));
        mBuilder.setContentTitle(drugName);
        mBuilder.setTicker(msgAlert);
        mBuilder.setContentText(drugDescription);
        mBuilder.setContentIntent(notifIntent);
        mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);
        mBuilder.setVibrate(new long[]{1000, 1000});
        mBuilder.setWhen(System.currentTimeMillis());
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(id, mBuilder.build());
//        context..startstartForeground(ContextConstants.LAUNCHER_SERVICE_NOTE_ID,
//                mBuilder.build());
    }

    public void SetAlarm(Context context, String drugName, String description) {
        AlarmManager alarmManager=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        intent.putExtra(ONE_TIME, Boolean.FALSE);
        intent.putExtra(DRUG, drugName);
        intent.putExtra(DESCRIPTION, description);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 5 , pi); //AlarmManager.INTERVAL_DAY
    }

    public void CancelAlarm(Context context) {
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    public void setOnetimeAlarm(Context context, String drugName, String description){
        AlarmManager alarmManager =(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        intent.putExtra(ONE_TIME, Boolean.TRUE);
        intent.putExtra(DRUG, drugName);
        intent.putExtra(DESCRIPTION, description);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+2000, pi);
    }
}