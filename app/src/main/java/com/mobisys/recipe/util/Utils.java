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
import java.util.List;
import java.util.Locale;

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

    }
