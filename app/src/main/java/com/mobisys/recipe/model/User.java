package com.mobisys.recipe.model;

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
    public String getProfileImage() {
        return getString("profileImage");
    }
    public String getEmail() {
        return getString("email");
    }
    public void setProfileImage(String profileImage) {
        put("profileImage", profileImage);
    }
    public void setUserName(String username) {
        put("username", username);
    }
    public void setEmail(String email) {
        put("email", email);
    }
}
