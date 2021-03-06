package com.mobisys.aspr.model;

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

    public void setUserId(String userId) {
        put("userId", userId);
    }

    public String getUserName() {
        return getString("userName");
    }

    public void setUserName(String userName) {
        put("userName", userName);
    }

    public String getUserIcon() {
        return getString("userIcon");
    }

    public void setUserIcon(String userIcon) {
        put("userIcon", userIcon);
    }

    public String getBodyText() {
        return getString("bodyText");
    }

    public void setBodyText(String bodyText) {
        put("bodyText", bodyText);
    }

    public String getDateTime() {
        return getString("dateTime");
    }

    public void setDateTime(String dateTime) {
        put("dateTime", dateTime);
    }

    public boolean getIsImageSet() {
        return getBoolean("isImageSet");
    }

    public void setIsImageSet(boolean isImageSet) {
        put("isImageSet", isImageSet);
    }

    public int getCommentCount() {
        return getInt("commentCount");
    }

    public void setCommentCount(int commentCount) {
        put("commentCount", commentCount);
    }

    public int getAudiance() {
        return getInt("audiance");
    }

    public void setAudiance(int audiance) {
        put("audiance", audiance);
    }

    public ParseFile getPostImage() {
        return getParseFile("postImage");
    }

    public void setPostImage(ParseFile postImage) {
        put("postImage", postImage);
    }



}
