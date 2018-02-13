package com.mahediapps.model;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.format.DateFormat;

import java.util.Calendar;

import com.mahediapps.personalreport.LoginActivity;
import com.mahediapps.personalreport.R;

public class AlarmReceiver extends BroadcastReceiver {

    private PendingIntent alarmIntent;
    private AlarmManager alarmMgr;
    private String dayId = "";
    DatabaseHelper helper;
    private Calendar month;
    private SharedPreferences profile;

    private void buildNotification(Context context) {
        NotificationManager notificationmanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(context);
        alarmIntent = PendingIntent.getActivity(context, 0, new Intent(context, LoginActivity.class), 0);

        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Personal Report")
                .setContentText("you may forget to keep your report !!")
                .setTicker("keep your report")
                .setContentIntent(alarmIntent)
                .setAutoCancel(true);

        notificationmanager.notify(0, builder.getNotification());
    }

    public void cancelAlarm(Context context) {
        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmIntent = PendingIntent.getBroadcast(context, 0, new Intent(context, AlarmReceiver.class), 0);
        alarmMgr.cancel(alarmIntent);
        ComponentName componentName = new ComponentName(context, SampleBootReceiver.class);
        context.getPackageManager().setComponentEnabledSetting(componentName, 2, 1);
    }

    public void onReceive(Context context, Intent intent) {
        month = Calendar.getInstance();
        helper = new DatabaseHelper(context);
        dayId = DateFormat.format("EE, dd MMMM yyyy", month).toString();
        if (helper.getWthereReportKeepsOrNotFromDayId(dayId) == 0) {
            buildNotification(context);
        }
    }

    public void setAlarm(Context context) {
        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmIntent = PendingIntent.getBroadcast(context, 0, new Intent(context, AlarmReceiver.class), 0);
        profile = context.getSharedPreferences("profile", 0);

        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(System.currentTimeMillis());
        time.set(Calendar.HOUR_OF_DAY, profile.getInt("hour", 0));
        time.set(Calendar.MINUTE, profile.getInt("minute", 0));

        alarmMgr.set(0, time.getTimeInMillis(), alarmIntent);

        ComponentName component = new ComponentName(context, SampleBootReceiver.class);
        context.getPackageManager().setComponentEnabledSetting(component, 1, 1);
    }
}
