package com.example.user.drugsorganiser.ViewModel.DrugsActivity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.example.user.drugsorganiser.Model.Drug;
import com.example.user.drugsorganiser.R;

/**
 * Created by DV7 on 2017-05-24.
 */

public final class NotificationManagement {

    private NotificationManagement() {}

    public static void CreateNotification(Context context, String title, String comment, Class <?> cls, int notificationId, int requestCode, int type, String details) {
        // id - If a notification with the same id has already been posted by your application and has not yet been canceled,
        //      it will be replaced by the updated information.
        // requestCode - If is not unique and class cls and requestCode have already been used for getting PendingIntent, it will be the same
        //               intent and they will override each other. If is not unique and class cls hasn't been used with this requestCode,
        //               they won't override each other. Same if is unique, but class cls has been used with other requestCode.
        Log.i("NotificationManagement","Create");

        Intent intent = new Intent(context, cls);
        if (type == 1) {
            intent.putExtra(DrugsActivity.SMS_ALERT, comment);
            intent.putExtra(DrugsActivity.DOSE_DETAILS, details);
            intent.putExtra(DrugsActivity.SMS, true);
            intent.putExtra(DrugsActivity.ALARM_ACTIVITY, true);
        } else if (type == 2) {
            intent.putExtra(DrugsActivity.ALARM, true);
            intent.putExtra(DrugsActivity.ALARM_ACTIVITY, true);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent notifIntent = PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.mipmap.logo);
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo));
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(comment);
        mBuilder.setContentIntent(notifIntent);
        mBuilder.setAutoCancel(true);
        mBuilder.setVibrate(new long[]{1000, 1000});
        mBuilder.setWhen(System.currentTimeMillis());

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(notificationId, mBuilder.build());
    }

    public static void CancelNotification(Context context, int notificationId) {

        Log.i("NotificationManagement","Cancel");

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(notificationId);
    }

    public static void CancelAllNotifications(Context context) {

        Log.i("NotificationManagement","Cancel all");

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

}