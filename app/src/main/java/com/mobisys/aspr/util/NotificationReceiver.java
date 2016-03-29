package com.mobisys.aspr.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.mobisys.aspr.activity.OneToOneChatActivity;
import com.mobisys.aspr.db.Globals;
import com.parse.ParsePushBroadcastReceiver;

public class NotificationReceiver extends ParsePushBroadcastReceiver {
    private static final String TAG = NotificationReceiver.class.getSimpleName();
    Globals glo;

    @Override
    public void onReceive(Context context, Intent intent) {
        glo = new Globals(context);
        Log.v(TAG, "Notification Received");
        if (OneToOneChatActivity.isAppAlive == 1) {
            OneToOneChatActivity.receiveMessage();
        }
        if (glo.getIsPushEnable().equals("Yes")) {
            super.onReceive(context, intent);
        }

    }
    /*
    @Override
	protected int getSmallIconId(Context context, Intent intent) {
	    return R.drawable.ic_launcher;
	}

	@Override
	protected Bitmap getLargeIcon(Context context, Intent intent) {
		if (android.os.Build.VERSION.SDK_INT >= 5.0) {
			return BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
			}
	    else{
	    	return BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);	
	    	}
	    
	}
*/
}
