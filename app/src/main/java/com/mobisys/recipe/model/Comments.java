package com.mobisys.recipe.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by ubuntu1 on 22/3/16.
 */
@ParseClassName("Comments")
public class Comments extends ParseObject {

    public String getUserId() {
        return getString("userId");
    }
    public String getUserName() {
        return getString("userName");
    }
    public String getUserIcon() {
        return getString("userIcon");
    }
    public String getComment() {
        return getString("commentText");
    }
    public String getDateTime() {
        return getString("dateTime");
    }
    public String getParent() {
        return getString("parent");
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
    public void setComment(String bodyText) {
        put("commentText", bodyText);
    }
    public void setDateTime(String dateTime) {
        put("dateTime", dateTime);
    }
    public void setParent(String parent) {
        put("parent", parent);
    }


}
