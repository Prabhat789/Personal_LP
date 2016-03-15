package com.mobisys.recipe.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

/**
 * Created by ubuntu1 on 15/3/16.
 */
@ParseClassName("TimeLine")
public class TimeLine extends ParseObject {

    public String getUserId() {
        return getString("userId");
    }
    public String getUserName() {
        return getString("userName");
    }
    public String getBodyText() {
        return getString("bodyText");
    }
    public String getDateTime() {
        return getString("dateTime");
    }
    public ParseFile getPostImage() {
        return getParseFile("postImage");
    }
    public void setUserId(String userId) {
        put("userId", userId);
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
    public void setPostImage(ParseFile postImage) {
        put("postImage", postImage);
    }
}
