package com.mobisys.aspr.util;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.mobisys.aspr.model.Comments;
import com.mobisys.aspr.model.Friends;
import com.mobisys.aspr.model.Message;
import com.mobisys.aspr.model.TimeLine;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by ubuntu1 on 10/3/16.
 */
public class RecipeApplication extends Application {

    private static RecipeApplication mInstance;
    private static Context mAppContext;

    public static RecipeApplication getInstance() {
        return mInstance;
    }

    public static Context getAppContext() {
        return mAppContext;
    }

    public void setAppContext(Context mAppContext) {
        RecipeApplication.mAppContext = mAppContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        this.setAppContext(getApplicationContext());

        Parse.enableLocalDatastore(getAppContext());
        ParseObject.registerSubclass(Message.class);
        ParseObject.registerSubclass(TimeLine.class);
        ParseObject.registerSubclass(Friends.class);
        ParseObject.registerSubclass(ParseUser.class);
        ParseObject.registerSubclass(Comments.class);


        Parse.initialize(getAppContext());
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
        subscribeforpushnotification();
    }

    private void subscribeforpushnotification() {
        // TODO Auto-generated method stub
        ParsePush.subscribeInBackground("ChatDemo", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });

    }
}
