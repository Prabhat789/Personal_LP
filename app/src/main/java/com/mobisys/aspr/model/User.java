package com.mobisys.aspr.model;

import com.parse.ParseClassName;
import com.parse.ParseUser;

/**
 * Created by ubuntu1 on 18/3/16.
 */
@ParseClassName("User")
public class User extends ParseUser {

    public String getUserName() {
        return getString("username");
    }

    public void setUserName(String username) {
        put("username", username);
    }

    public String getProfileImage() {
        return getString("profileImage");
    }

    public void setProfileImage(String profileImage) {
        put("profileImage", profileImage);
    }

    public String getEmail() {
        return getString("email");
    }

    public void setEmail(String email) {
        put("email", email);
    }
    public String getFullName() {
        return getString("userFullName");
    }

    public void setFullName(String email) {
        put("userFullName", email);
    }
}
