package com.mobisys.aspr.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobisys.aspr.activity.MapsActivity;
import com.mobisys.aspr.imageloadingutil.ImageLoader;
import com.mobisys.aspr.util.ApplicationConstant;
import com.parse.ParseUser;
import com.pktworld.aspr.R;

/**
 * Created by ubuntu1 on 11/3/16.
 */
public class Profile extends Fragment implements View.OnClickListener {

    private static final String TAG = Profile.class.getSimpleName();
    private ImageView userProfileImage;
    private ImageLoader imageLoader;
    private LinearLayout llLivesIn, llFrom;
    private TextView txtLivesIn, txtFrom;
    //private Button btnMessage;
    private FloatingActionButton editProfileButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        userProfileImage = (ImageView)rootView.findViewById(R.id.userProfileImage);
        llFrom = (LinearLayout)rootView.findViewById(R.id.llFrom);
        llLivesIn = (LinearLayout)rootView.findViewById(R.id.llLivesIn);

        txtFrom = (TextView)rootView.findViewById(R.id.txtFrom);
        txtLivesIn = (TextView)rootView.findViewById(R.id.txtLivesin);
        //btnMessage = (Button)rootView.findViewById(R.id.btnMessage);
        editProfileButton = (FloatingActionButton)rootView.findViewById(R.id.editProfileButton);
        imageLoader =  ImageLoader.getInstance(getActivity());

        loadUserIcon(ParseUser.getCurrentUser().getString("profileImage"),userProfileImage,getActivity());

        llLivesIn.setOnClickListener(this);
        llFrom.setOnClickListener(this);
       // btnMessage.setOnClickListener(this);
        editProfileButton.setOnClickListener(this);


        return rootView;
    }

    private void loadUserIcon(String url,ImageView imageView, Context context) {
        try{
            //Ion.with(context).load(url).intoImageView(imageView);
            imageLoader.DisplayImage(url,imageView);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }

    }


    @Override
    public void onClick(View v) {
        if (v == llFrom){
            Intent i = new Intent(getActivity(), MapsActivity.class);
            i.putExtra(ApplicationConstant.LATITUDE,"0.00");
            i.putExtra(ApplicationConstant.LONGITUDE,"0.00");
            startActivity(i);
        }else if (v == llLivesIn){
            Intent i = new Intent(getActivity(), MapsActivity.class);
            i.putExtra(ApplicationConstant.LATITUDE,"0.00");
            i.putExtra(ApplicationConstant.LONGITUDE,"0.00");
            startActivity(i);
        }/*else if (v == btnMessage){
            Intent i = new Intent(getActivity(), OneToOneChatActivity.class);
            startActivity(i);
        }*/else if(v == editProfileButton){

        }
    }
}
