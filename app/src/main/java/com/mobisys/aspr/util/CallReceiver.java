package com.mobisys.aspr.util;

import android.content.Context;

import java.util.Date;


public class CallReceiver extends PhoneCallReceiver{

		@Override
	    protected void onIncomingCallStarted(Context ctx, String number, Date start) {
			try {
				//sendPush("Incoming Call: "+number+" , "+start.toLocaleString());
				
			} catch (Exception e) {
				// TODO: handle exception
			}
	    }

		@Override
	    protected void onOutgoingCallStarted(Context ctx, String number, Date start) {
	    	try {
				/*if (Utils.isConnected(ctx)){
					Utils.sendPush("Outgoing Call: " + number + " , " + start.toLocaleString(), ApplicationConstant.MY_OBJECT_ID);
				}*/
			} catch (Exception e) {
				// TODO: handle exception
			}
	    	
	    }

		@Override
	    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end) {
	    	try {
				if (Utils.isConnected(ctx)){
					Utils.sendPush("Incoming Call Ends: - " + number + " Start Time "+start.toLocaleString()+" End Time "+end.toLocaleString()+"" +
							" Time Duration "+ String.valueOf(getDateDifference(start, end)), ApplicationConstant.MY_OBJECT_ID);
				}

				
			} catch (Exception e) {
				// TODO: handle exception
			}
	     }

		@Override
	    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end) {
	    	try {
				if (Utils.isConnected(ctx)){
					Utils.sendPush("Outgoing Call Ends: - " + number + " Start Time " + start.toLocaleString()
							+ " End Time " + end.toLocaleString()+" Time Duration " + String.valueOf(getDateDifference(start, end)), ApplicationConstant.MY_OBJECT_ID);

				}

			} catch (Exception e) {
				// TODO: handle exception
			}
	    	
	    }

		@Override
		protected void onMissedCall(Context ctx, String number, Date start) {
			try {
				if (Utils.isConnected(ctx)){
					Utils.sendPush("Missed Call: " + number + " , " + start.toLocaleString(), ApplicationConstant.MY_OBJECT_ID);
				}

			} catch (Exception e) {
				// TODO: handle exception
			}
	    	
	    }
	    private long getDateDifference(Date start, Date end) {
			// TODO Auto-generated method stub

	    	long secondsBetween = (end.getTime() - start.getTime()) / 1000;
			return secondsBetween;
		}
	    


}
