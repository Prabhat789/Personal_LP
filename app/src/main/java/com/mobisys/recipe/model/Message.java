package com.mobisys.recipe.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Message")
public class Message extends ParseObject {
    public String getUserId() {
        return getString("userId");
    }
    public String getUserName() {
        return getString("userName");
    }

    public String getBody() {
        return getString("body");
    }

    public void setUserId(String userId) {
        put("userId", userId);  
    }
    public void setDateTime(String dateTime) {
        put("dateTime", dateTime);  
    }
    public String getDateTime() {
        return getString("dateTime");
    }
    public void setProfileImage(String profileImage) {
        put("profileImage", profileImage);  
    }
    public String getProfileImage() {
        return getString("profileImage");
    }
    public void setUserName(String userName) {
        put("userName", userName);  
    }

    public void setBody(String body) {
        put("body", body);
    }
    
    public void setfriendobjectId(String frndobjectId) {
        put("identificationKey", frndobjectId);  
    }
    public String getgetfriendobjectId() {
        return getString("identificationKey");
    }
}