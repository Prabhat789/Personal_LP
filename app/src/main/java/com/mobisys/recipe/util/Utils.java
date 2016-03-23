package com.mobisys.recipe.util;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mobisys.recipe.R;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Prabhat on 04/11/15.
 */
public class Utils {
    private static final String TAG = Utils.class.getSimpleName();
        Context mContext;

        public Utils(Context context) {
            // TODO Auto-generated constructor stub
            this.mContext = context;
        }

        public static boolean isConnected(Activity context) {
            ConnectivityManager connMgr = (ConnectivityManager) context
                    .getSystemService(Activity.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected())
                return true;
            else
                showToastMessage(context,context.getString(R.string.no_internet));
                return false;
        }

    /*send push notification*/
    public static void sendPush(String msg, String objectId) {

        ParseQuery userQuery = ParseUser.getQuery();
        userQuery.whereEqualTo("objectId", objectId);
        ParseQuery pushQuery = ParseInstallation.getQuery();
        pushQuery.whereMatchesQuery("user", userQuery);
        // Send push notification to query
        ParsePush push = new ParsePush();
        push.setQuery(pushQuery);
        push.setMessage(msg);
        push.sendInBackground();

    }

    public static String getAddress(String lat, String lng, Context context) {
        String blankAddress = context.getResources().getString(R.string.location_not_available);

        String locationAddress;
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(Double.parseDouble(lat),
                    Double.parseDouble(lng), 1);
        } catch (IOException e1) {
            Log.e(TAG, "IO Exception in getFromLocation()");
            e1.printStackTrace();
            return (blankAddress);
        } catch (IllegalArgumentException e2) {
            String errorString = "Illegal arguments "
                    + Double.toString(Double.parseDouble(lat)) + " , "
                    + Double.toString(Double.parseDouble(lat))
                    + " passed to address service";
            Log.e(TAG, errorString);
            e2.printStackTrace();
            return blankAddress;
        }
        if (addresses != null && addresses.size() > 0) {
            Address fetchedAddress = addresses.get(0);
            Log.d("CountryName", addresses.get(0).getCountryName());

            StringBuilder strAddress = new StringBuilder();

            for (int i = 0; i < fetchedAddress.getMaxAddressLineIndex(); i++) {
                strAddress.append(fetchedAddress.getAddressLine(i));
            }

            locationAddress = strAddress.toString();

            return locationAddress;
        } else {
            return blankAddress;
        }

    }

    /*public static String getlongtoago(long createdAt) {
        SimpleDateFormat userDateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
        SimpleDateFormat dateFormatNeeded = new SimpleDateFormat("MM/dd/yyyy HH:MM:SS");
        Date date = null;
        date = new Date(createdAt);
        String crdate1 = dateFormatNeeded.format(date);

        // Date Calculation
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        crdate1 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(date);

        // get current date time with Calendar()
        Calendar cal = Calendar.getInstance();
        String currenttime = dateFormat.format(cal.getTime());

        Date CreatedAt = null;
        Date current = null;
        try {
            CreatedAt = dateFormat.parse(crdate1);
            current = dateFormat.parse(currenttime);
        } catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Get msec from each, and subtract.
        long diff = current.getTime() - CreatedAt.getTime();
        long diffSeconds = diff / 1000;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        String time = null;
        if (diffDays > 0) {
            if (diffDays == 1) {
                time = diffDays + "day ago ";
            } else {
                time = diffDays + "days ago ";
            }
        } else {
            if (diffHours > 0) {
                if (diffHours == 1) {
                    time = diffHours + "hr ago";
                } else {
                    time = diffHours + "hrs ago";
                }
            } else {
                if (diffMinutes > 0) {
                    if (diffMinutes == 1) {
                        time = diffMinutes + "min ago";
                    } else {
                        time = diffMinutes + "mins ago";
                    }
                } else {
                    if (diffSeconds > 0) {
                        time = diffSeconds + "secs ago";
                    }
                }

            }

        }
        return time;
    }*/

    public String formatDate(String date) {

        SimpleDateFormat sourceFormat = new SimpleDateFormat(
                ApplicationConstant.DATE_FORMATE);
        sourceFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date parsed = null;
        try {
            // 2011-03-01 15:10:37
            parsed = sourceFormat.parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // => Date is in UTC now

        TimeZone tz = TimeZone.getDefault();
        SimpleDateFormat destFormat = new SimpleDateFormat(
                ApplicationConstant.DATE_FORMATE);
        destFormat.setTimeZone(tz);

        String result = destFormat.format(parsed);
        System.out.println("Formated Date : " + result);
        return result;

    }

    public String getTimeDiffrence(Context ctx,String date) {

        // DateTimeUtils obj = new DateTimeUtils();
        String strdate1;
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH) + 1;
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        int second = c.get(Calendar.SECOND);
        int minute = c.get(Calendar.MINUTE);
        int hourofday = c.get(Calendar.HOUR_OF_DAY);

        strdate1 = mDay + "-" + mMonth + "-" + mYear + " " + hourofday + ":"
                + minute + ":" + second;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                ApplicationConstant.DATE_FORMATE);

        try {

			/*
			 * Date date1 = simpleDateFormat.parse("13-10-2013 11:30:10"); Date
			 * date2 = simpleDateFormat.parse("13-10-2013 20:35:55");
			 */

            Date date1 = simpleDateFormat.parse(date);
            Date date2 = simpleDateFormat.parse(strdate1);

            date = printDifference(ctx, date1, date2);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;

    }

    // 1 minute = 60 seconds
    // 1 hour = 60 x 60 = 3600
    // 1 day = 3600 x 24 = 86400
    public String printDifference(Context ctx, Date startDate, Date endDate) {

        String dateDifference;
        // milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : " + endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        System.out.printf("%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);

        if (elapsedDays == 1) {
            dateDifference = ctx.getResources().getString(
                    R.string.update_ago)
                    + " "
                    + elapsedDays
                    + " "
                    + ctx.getResources().getString(R.string.day);
        }else if (elapsedDays > 1) {
            dateDifference = ctx.getResources().getString(
                    R.string.oneday_ago);
        } else if (elapsedHours >= 1) {
            dateDifference = ctx.getResources().getString(
                    R.string.update_ago)
                    + " "
                    + elapsedHours
                    + " "
                    + ctx.getResources().getString(R.string.hours);
        } else if (elapsedMinutes >= 1) {
            dateDifference = ctx.getResources().getString(
                    R.string.update_ago)
                    + " "
                    + elapsedMinutes
                    + " "
                    + ctx.getResources().getString(R.string.minutes);
        } else {
            Log.e(TAG,""+elapsedMinutes);
            dateDifference = ctx.getResources().getString(R.string.online);
        }

        return dateDifference;

    }





   /* public static void showInfoAlert(Context mContext, String msg){

        try{
            Dialog dialog = new Dialog(mContext);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.dialog_application_alert);
            dialog.setCancelable(true);

            TextView msgText = (TextView)dialog.findViewById(R.id.txtAlertMessage);
            msgText.setText(msg);
            dialog.show();

        }catch (Exception e){
            e.printStackTrace();
        }

    }*/

    /*public void userAgreementDialog(Context mContext){
        try{
            Dialog dialog = new Dialog(mContext);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.dialog_user_agreement);
            dialog.setCancelable(true);

            EditText msgText = (EditText)dialog.findViewById(R.id.txtContent);
            msgText.setText(ReadFromfile("agreement.txt",mContext));
            dialog.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }*/

    public String ReadFromfile(String fileName, Context context) {
        StringBuilder returnString = new StringBuilder();
        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;
        try {
            fIn = context.getResources().getAssets()
                    .open(fileName, Context.MODE_WORLD_READABLE);
            isr = new InputStreamReader(fIn);
            input = new BufferedReader(isr);
            String line = "";
            while ((line = input.readLine()) != null) {
                returnString.append(line);
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                if (isr != null)
                    isr.close();
                if (fIn != null)
                    fIn.close();
                if (input != null)
                    input.close();
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
        return returnString.toString();
    }

    public static void showToastMessage(Activity mContext, String msg){
        LayoutInflater li = mContext.getLayoutInflater();
        View layout = li.inflate(R.layout.custom_toast, null);
        TextView txtMsg = (TextView)layout.findViewById(R.id.txtToast);
        txtMsg.setText(msg);
        Toast toast = new Toast(mContext);
        toast.setDuration(Toast.LENGTH_SHORT);
       // toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM, 0, 0);
        toast.setView(layout);
        toast.show();
    }

    public static String getUserImage(){
        return  ParseUser.getCurrentUser().getString("profileImage");
    }
    public static String getLivesIn(){
        return ParseUser.getCurrentUser().getString("livesIn");
    }
    public static String getFromLocation(){
        return ParseUser.getCurrentUser().getString("fromLocation");
    }
    public static String getUserFullName(){
        return ParseUser.getCurrentUser().getString("userFullName");
    }
    public static String getUserId(){
        return ParseUser.getCurrentUser().getObjectId();
    }
    public static String getUserName(){
        return ParseUser.getCurrentUser().getUsername();
    }
    public static String getCurrentTimeStamp(){
        SimpleDateFormat outputFmt = new SimpleDateFormat(ApplicationConstant.DATE_FORMATE);
        outputFmt.setTimeZone(TimeZone.getTimeZone("UTC"));
        return outputFmt.format(new Date());
    }



    }
