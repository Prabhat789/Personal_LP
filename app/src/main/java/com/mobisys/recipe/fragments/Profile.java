package com.mobisys.recipe.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.mobisys.recipe.R;
import com.mobisys.recipe.activity.MapsActivity;
import com.mobisys.recipe.activity.OneToOneChatActivity;
import com.mobisys.recipe.util.CustomVolleyRequestQueue;
import com.parse.ParseUser;

/**
 * Created by ubuntu1 on 11/3/16.
 */
public class Profile extends Fragment implements View.OnClickListener {

    private static final String TAG = Profile.class.getSimpleName();
    private NetworkImageView userProfileImage;
    private ImageLoader imageLoader;
    private LinearLayout llLivesIn, llFrom;
    private TextView txtLivesIn, txtFrom;
    private Button btnMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        userProfileImage = (NetworkImageView)rootView.findViewById(R.id.userProfileImage);
        llFrom = (LinearLayout)rootView.findViewById(R.id.llFrom);
        llLivesIn = (LinearLayout)rootView.findViewById(R.id.llLivesIn);

        txtFrom = (TextView)rootView.findViewById(R.id.txtFrom);
        txtLivesIn = (TextView)rootView.findViewById(R.id.txtLivesin);
        btnMessage = (Button)rootView.findViewById(R.id.btnMessage);
        imageLoader = CustomVolleyRequestQueue.getInstance(getActivity()).getImageLoader();
        /*imageLoader.get(ParseUser.getCurrentUser().getString("profileImage"), ImageLoader.getImageListener(
                userProfileImage,
                R.mipmap.ic_launcher, R.mipmap.ic_launcher));*/
        userProfileImage.setImageUrl(ParseUser.getCurrentUser().getString("profileImage"), imageLoader);

        llLivesIn.setOnClickListener(this);
        llFrom.setOnClickListener(this);
        btnMessage.setOnClickListener(this);


        return rootView;
    }


    @Override
    public void onClick(View v) {
        if (v == llFrom){
            Intent i = new Intent(getActivity(), MapsActivity.class);
            i.putExtra("DATA",txtFrom.getText().toString());
            startActivity(i);
        }else if (v == llLivesIn){
            Intent i = new Intent(getActivity(), MapsActivity.class);
            i.putExtra("DATA",txtLivesIn.getText().toString());
            startActivity(i);
        }else if (v == btnMessage){
            Intent i = new Intent(getActivity(), OneToOneChatActivity.class);
            startActivity(i);
        }
    }
}
