package com.mobisys.aspr.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.SystemClock;
import android.util.Log;

import com.mobisys.aspr.util.ApplicationConstant;

/**
 * Created by ubuntu1 on 1/4/16.
 */
public class InternetConnectionReceiver extends BroadcastReceiver {
    private static final String TAG = InternetConnectionReceiver.class.getSimpleName();
    private AlarmManager alarmManager;
    private Intent gpsTrackerIntent;
    private PendingIntent pendingIntent;
    private int intervalInMinutes;


    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMan.getActiveNetworkInfo();
        if ((netInfo != null && netInfo.getType() == ConnectivityManager.TYPE_WIFI)) {
            startUploadingDataAlarmManager(context);
        }else{
            cancelAlarmManager(context);
        }
    }


     private void cancelAlarmManager(Context mContext) {
        Log.d(TAG, "cancelAlarmManager-MainService");
        try {
            Intent gpsTrackerIntent = new Intent(mContext,
                    DataUploadAlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0,
                    gpsTrackerIntent, 0);
            AlarmManager alarmManager = (AlarmManager) mContext
                    .getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);
            mContext.stopService(new Intent(mContext, MainService.class));
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    private void startUploadingDataAlarmManager(Context mContext) {
        Log.d(TAG, "startAlarmManager-MainService");
        try {
            SharedPreferences sharedPreferences = mContext.getSharedPreferences(
                    ApplicationConstant.APPLICATION_PREFERENCE_NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(ApplicationConstant.INTERVAL_IN_MINUTES, ApplicationConstant.INTERVAL_TIME);
            alarmManager = (AlarmManager) mContext
                    .getSystemService(Context.ALARM_SERVICE);
            gpsTrackerIntent = new Intent(mContext, DataUploadAlarmReceiver.class);
            intervalInMinutes = sharedPreferences.getInt(ApplicationConstant.INTERVAL_IN_MINUTES, ApplicationConstant.INTERVAL_TIME);
            pendingIntent = PendingIntent.getBroadcast(mContext, 0,
                    gpsTrackerIntent, 0);

            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime(), intervalInMinutes * 1 * 1000,
                    pendingIntent);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }


    }
}
