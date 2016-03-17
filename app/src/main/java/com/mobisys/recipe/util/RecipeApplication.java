package com.mobisys.recipe.util;

import android.app.Application;
import android.util.Log;

import com.mobisys.recipe.model.Friends;
import com.mobisys.recipe.model.Message;
import com.mobisys.recipe.model.TimeLine;
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

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(getApplicationContext());
        ParseObject.registerSubclass(Message.class);
        ParseObject.registerSubclass(TimeLine.class);
        ParseObject.registerSubclass(Friends.class);
        Parse.initialize(this);
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
