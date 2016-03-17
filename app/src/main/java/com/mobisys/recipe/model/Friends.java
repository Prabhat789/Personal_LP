package com.mobisys.recipe.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by ubuntu1 on 17/3/16.
 */
@ParseClassName("Friends")
public class Friends extends ParseObject {

    public String getUserId() {
        return getString("userId");
    }
    public String getUserName() {
        return getString("userName");
    }
    public String getUserIcon() {
        return getString("userIcon");
    }
    public String getBodyText() {
        return getString("bodyText");
    }
    public String getDateTime() {
        return getString("dateTime");
    }

    public void setUserId(String userId) {
        put("userId", userId);
    }
    public void setUserIcon(String userIcon) {
        put("userIcon", userIcon);
    }
    public void setUserName(String userName) {
        put("userName", userName);
    }
    public void setBodyText(String bodyText) {
        put("bodyText", bodyText);
    }
    public void setDateTime(String dateTime) {
        put("dateTime", dateTime);
    }
}
