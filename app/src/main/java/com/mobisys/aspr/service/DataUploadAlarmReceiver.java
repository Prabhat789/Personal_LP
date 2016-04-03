package com.mobisys.aspr.service;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by ubuntu1 on 1/4/16.
 */
public class DataUploadAlarmReceiver extends WakefulBroadcastReceiver {
    private static final String TAG = DataUploadAlarmReceiver.class.getSimpleName();

    @Override
    public void onReceive(final Context context, Intent intent) {
        context.startService(new Intent(context, MainService.class));
    }
}


