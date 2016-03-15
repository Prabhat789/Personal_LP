/*
package com.mobisys.recipe.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.Contacts;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobisys.adapter.CombineTabAdapter;
import com.mobisys.recipe.R;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseUser;

public class FriendListActivity extends FragmentActivity implements OnClickListener{
	
	private ViewPager Tab;
	private CombineTabAdapter TabAdapter;
	private ActionBar actionBar;
	private TextView titleText;
	static final int PICK_CONTACT_REQUEST = 1;
	//ImageView refreshImage,addFriend;
	 //ProgressBar refreshProgressbar;
	private ImageView addFriend;
	
	
	String email, name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend_list);
		Typeface myTypeface = Typeface.createFromAsset(this.getAssets(), "alighty.ttf");
 	    
		
		TabAdapter = new CombineTabAdapter(getSupportFragmentManager());
		  actionBar = getActionBar();
		  actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009688")));
	      	actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#009688")));       
	      	//actionBar.show();
	        actionBar.setDisplayShowHomeEnabled(false);
	        View mActionBarView = getLayoutInflater().inflate(R.layout.custom_action_bar, null);
	       
	        actionBar.setCustomView(mActionBarView);
	        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
	        */
/*refreshImage = (ImageView)findViewById(R.id.refresh);
	        refreshProgressbar = (ProgressBar)findViewById(R.id.refreshProgress);*//*

	        addFriend = (ImageView)findViewById(R.id.gelleryImage);
	        addFriend.setVisibility(View.INVISIBLE);
	        titleText= (TextView)findViewById(R.id.textOne);
	        titleText.setTypeface(myTypeface);
	        titleText.setText("HOME");
	        */
/*refreshImage.setVisibility(View.INVISIBLE);
		    refreshProgressbar.setVisibility(View.INVISIBLE);
		   
		*//*

		    
        Tab = (ViewPager)findViewById(R.id.pager);
        Tab.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
 
                    	
                        actionBar.setSelectedNavigationItem(position);               
                        }
                });
        Tab.setAdapter(TabAdapter);
 
        actionBar = getActionBar();
        //Enable Tabs on Action Bar
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.TabListener tabListener = new ActionBar.TabListener(){
 
            @Override
            public void onTabReselected(ActionBar.Tab tab,
                    FragmentTransaction ft) {
                // TODO Auto-generated method stub
 
            }
 
            @Override
             public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
 
                Tab.setCurrentItem(tab.getPosition());
            }
 
            @Override
            public void onTabUnselected(ActionBar.Tab tab,
                    FragmentTransaction ft) {
                // TODO Auto-generated method stub
 
            }};
            //Add New Tab
            actionBar.addTab(actionBar.newTab().setText("My Friends").setTabListener(tabListener));
           // actionBar.addTab(actionBar.newTab().setText("Near my Location").setTabListener(tabListener));
            actionBar.addTab(actionBar.newTab().setText("All Users").setTabListener(tabListener));
            actionBar.addTab(actionBar.newTab().setText("Settings").setTabListener(tabListener));
            
	       
	        
	}
	
	
	
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		*/
/*if (v == addFriend) {
			Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
					Email.CONTENT_URI);
			contactPickerIntent
					.setType(ContactsContract.CommonDataKinds.Email.CONTENT_TYPE);
			startActivityForResult(contactPickerIntent,
					PICK_CONTACT_REQUEST);
			
		}*//*

		
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		if (resultCode == -1) {
			switch (requestCode) {
			case PICK_CONTACT_REQUEST:
				Cursor cursor = null;

				try {
					Uri result = data.getData();
					String id = result.getLastPathSegment();
					cursor = getContentResolver().query(
							Email.CONTENT_URI, null, Email._ID + "=" + id,
							null, null);
					int nameId = cursor.getColumnIndex(Contacts.DISPLAY_NAME);
					int emailIdx = cursor.getColumnIndex(Email.DATA);
					if (cursor.moveToFirst()) {
						email = cursor.getString(emailIdx);
						name = cursor.getString(nameId);
					} else {

					}
				} catch (Exception e) {
				} finally {
					if (cursor != null) {
						cursor.close();
					}
					if (ParseUser.getCurrentUser().getEmail().equalsIgnoreCase(email)) {
						Toast.makeText(this, "This is your email", Toast.LENGTH_SHORT).show();
						email = "";
						name = "";
					} else {
						sendInviteMail(email, name);
						*/
/*if (comparExistingEmail(eMail, emailList)) {
							ServiceManager serviceManager = new ServiceManager(
									getActivity(), PersonFragments.this);
							serviceManager.addPersonas(co_Id, Name, eMail, "1");
						}else {
							infoDialog.showInfoAlert(getResources().getString(R.string.contact_inlist));
						}*//*

						
					}

				}
				break;
			}

		} else {
		}
	}
	
	
	private void sendInviteMail(String email, String name) {
		// TODO Auto-generated method stub
		
		Map<String, String> params = new HashMap<String, String>();
	    params.put("text", "HI Prabhat");
	    params.put("subject", "Request to add connection");
	    params.put("fromEmail", ParseUser.getCurrentUser().getEmail());
	    params.put("fromName", ParseUser.getCurrentUser().getUsername());
	    params.put("toEmail", email);
	    params.put("toName", name);
	    ParseCloud.callFunctionInBackground("sendMail", params, new FunctionCallback<Object>() {
	        @Override
	        public void done(Object response, ParseException exc) {
	            Log.e("cloud code example", "response: " + response);
	        }
	    });

	}

	
	 
   

}
*/
