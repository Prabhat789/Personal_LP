package com.mobisys.aspr.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobisys.aspr.activity.LoginActivity;
import com.mobisys.aspr.activity.MapsActivity;
import com.mobisys.aspr.db.Globals;
import com.mobisys.aspr.imageloadingutil.ImageLoader;
import com.mobisys.aspr.util.ApplicationConstant;
import com.mobisys.aspr.util.Utils;
import com.parse.LogOutCallback;
import com.parse.ParseException;
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
    private TextView txtLivesIn, txtFrom, txtName, txtEmail;
    private Button btnLogout;
    private FloatingActionButton editProfileButton;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Globals glo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        glo = new Globals(getActivity());
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        userProfileImage = (ImageView)rootView.findViewById(R.id.userProfileImage);
        llFrom = (LinearLayout)rootView.findViewById(R.id.llFrom);
        llLivesIn = (LinearLayout)rootView.findViewById(R.id.llLivesIn);

        txtFrom = (TextView)rootView.findViewById(R.id.txtFrom);
        txtLivesIn = (TextView)rootView.findViewById(R.id.txtLivesin);
        txtName = (TextView)rootView.findViewById(R.id.textView);
        txtEmail = (TextView)rootView.findViewById(R.id.txtEmail);

        btnLogout = (Button)rootView.findViewById(R.id.btnLogout);
        editProfileButton = (FloatingActionButton)rootView.findViewById(R.id.editProfileButton);
        imageLoader =  ImageLoader.getInstance(getActivity());

        loadUserIcon(ParseUser.getCurrentUser().getString("profileImage"),userProfileImage,getActivity());
        txtName.setText(Utils.getUserFullName());
        txtEmail.setText(Utils.getUserEmail());

        llLivesIn.setOnClickListener(this);
        llFrom.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
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
        }else if (v == btnLogout){
            ParseUser.logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null){
                        loginPreferences = getActivity().getSharedPreferences(ApplicationConstant.LOGIN_PREFERENCE,
                                getActivity().MODE_PRIVATE);
                        loginPrefsEditor = loginPreferences.edit();
                        loginPrefsEditor.clear();
                        loginPrefsEditor.commit();
                        glo.setIsPushEnable("No");
                        Intent i = new Intent(getActivity(), LoginActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    }
                }
            });
        }else if(v == editProfileButton){

        }
    }
}
