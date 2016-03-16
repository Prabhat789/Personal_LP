package com.mobisys.recipe.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.mobisys.recipe.R;
import com.mobisys.recipe.util.CustomVolleyRequestQueue;
import com.parse.ParseUser;

/**
 * Created by ubuntu1 on 11/3/16.
 */
public class Profile extends Fragment {

    private static final String TAG = Profile.class.getSimpleName();
    private NetworkImageView userProfileImage;
    private ImageLoader imageLoader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        userProfileImage = (NetworkImageView)rootView.findViewById(R.id.userProfileImage);
        imageLoader = CustomVolleyRequestQueue.getInstance(getActivity()).getImageLoader();
        /*imageLoader.get(ParseUser.getCurrentUser().getString("profileImage"), ImageLoader.getImageListener(
                userProfileImage,
                R.mipmap.ic_launcher, R.mipmap.ic_launcher));*/
        userProfileImage.setImageUrl(ParseUser.getCurrentUser().getString("profileImage"), imageLoader);


        return rootView;
    }


}
