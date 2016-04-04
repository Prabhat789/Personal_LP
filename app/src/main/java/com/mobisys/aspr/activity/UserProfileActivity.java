package com.mobisys.aspr.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobisys.aspr.imageloadingutil.ImageLoader;
import com.mobisys.aspr.util.ApplicationConstant;
import com.mobisys.aspr.util.Utils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.pktworld.aspr.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ubuntu1 on 4/4/16.
 */
public class UserProfileActivity extends AppCompatActivity {

    private static final String TAG = UserProfileActivity.class.getSimpleName();
    private String userId = null;
    private ImageView userProfileImage;
    private ImageLoader imageLoader;
    private LinearLayout llLivesIn, llFrom;
    private TextView txtLivesIn, txtFrom, txtName, txtEmail;
    private ArrayList<ParseUser> allUsers;
    private TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTitle = (TextView) toolbar.findViewById(R.id.txtToolbar);
        getSupportActionBar().setTitle("");

        userId = getIntent().getStringExtra(ApplicationConstant.FLAG);
        Log.v(TAG,userId);
        imageLoader =  ImageLoader.getInstance(UserProfileActivity.this);
        userProfileImage = (ImageView)findViewById(R.id.userProfileImage);
        llFrom = (LinearLayout)findViewById(R.id.llFrom);
        llLivesIn = (LinearLayout)findViewById(R.id.llLivesIn);
        txtFrom = (TextView)findViewById(R.id.txtFrom);
        txtLivesIn = (TextView)findViewById(R.id.txtLivesin);
        txtName = (TextView)findViewById(R.id.textView);
        txtEmail = (TextView)findViewById(R.id.txtEmail);

        allUsers = new ArrayList<ParseUser>();


    }

    @Override
    protected void onResume() {
        if (Utils.isConnected(UserProfileActivity.this)){
            getProfileDetails();
        }
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    void getProfileDetails(){
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.setLimit(1);
        query.whereEqualTo("objectId", userId);
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> messages, ParseException e) {
                if (e == null) {
                    allUsers.clear();
                    allUsers.addAll(messages);
                    txtName.setText(allUsers.get(0).getString("userFullName"));
                    txtEmail.setText(allUsers.get(0).getEmail());
                    loadUserIcon(allUsers.get(0).getString("profileImage"), userProfileImage);
                    mTitle.setText(allUsers.get(0).getUsername());

                } else {
                    Log.d("message", "Error: " + e.getMessage());
                }
            }
        });
    }

    private void loadUserIcon(String url,ImageView imageView) {
        try{
            imageLoader.DisplayImage(url,imageView);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
