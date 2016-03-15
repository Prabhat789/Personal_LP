package com.mobisys.recipe.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mobisys.recipe.R;
import com.mobisys.recipe.adapter.OneToOneChatListAdapter;
import com.mobisys.recipe.model.Message;
import com.mobisys.recipe.util.SpacesItemDecoration;
import com.mobisys.recipe.util.Utils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by ubuntu1 on 15/3/16.
 */
public class OneToOneChatActivity extends AppCompatActivity {

    private static String sUserId , sUserName;
    private static final String USER_ID_KEY = "userId";
    private static final String USER_NAME_KEY = "userName";
    private EditText etMessage;
    private Button btSend;
    private static ArrayList<Message> mMessages;
    private static OneToOneChatListAdapter mAdapter;
    private static RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String friendObjectId = "";
    String userNmae = "";

    public static int isAppAlive = 0;

    //ArrayList<String> friendList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_to_one_chat);
        friendObjectId = "bVTE7nVohL";
        userNmae = "Prabhat";
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.txtToolbar);
        mTitle.setText(userNmae);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
       // friendList.clear();
        /*Intent i = getIntent();
        friendObjectId =  i.getStringExtra("OBJECTID");
        userNmae = i.getStringExtra("NAME");*/
        //friendList = i.getStringArrayListExtra("FRIENDLIST");
        refreshMessages();
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("user", ParseUser.getCurrentUser());
        installation.saveInBackground();

        if (ParseUser.getCurrentUser() != null) {
            startWithCurrentUser();

        } else {
            // login();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void refreshMessages() {
        receiveMessage();
    }

    // Get the userId from the cached currentUser object
    private void startWithCurrentUser() {
        sUserId = ParseUser.getCurrentUser().getObjectId();
        sUserName = ParseUser.getCurrentUser().getUsername();
        setupMessagePosting();
    }

    @SuppressLint("SimpleDateFormat")
    private String getDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a dd-MM-yy");
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
        // TODO Auto-generated method stub

    }

    private void setupMessagePosting() {

        etMessage = (EditText) findViewById(R.id.etMessage);
        btSend = (Button) findViewById(R.id.btSend);
        mRecyclerView = (RecyclerView)findViewById(R.id.cardList);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(8));
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
       /* lvChat = (ListView) findViewById(R.id.lvChat);
        galleryImage = (ImageView)findViewById(R.id.gelleryImage);
        TextView titleText = (TextView)findViewById(R.id.textOne);
        titleText.setTypeface(myTypeface);
        titleText.setText(settitleText());
        galleryImage.setOnClickListener(this);
        try {
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.add_picture_movikit)
                    .cacheOnDisc(true).cacheOnDisc(true)
                    .bitmapConfig(Bitmap.Config.RGB_565).build();
            //	Picasso.with(this).load(ParseUser.getCurrentUser().getString("profileImage")).into(galleryImage);
            imageLoader.displayImage(ParseUser.getCurrentUser().getString("profileImage"),galleryImage, options);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }*/

        mMessages = new ArrayList<Message>();
        mAdapter = new OneToOneChatListAdapter(OneToOneChatActivity.this, sUserId, mMessages);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                //call smooth scroll
                mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount());
            }
        });
        //mRecyclerView.scrollToPosition(mMessages.size() - 1);
       /* lvChat.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                // TODO Auto-generated method stub
                String msgId = ""+mMessages.get(position).getObjectId();
                showDelEditAlert(ChatActivity.this, msgId);
                return true;
            }
        });

        lvChat.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String oblectId = mMessages.get(position).getUserId();
				*//*if (ParseUser.getCurrentUser().getObjectId().equals(oblectId)) {
					Intent i = new Intent(ChatActivity.this, ProfileActivity.class);
					i.putExtra("OID", mMessages.get(position).getUserId());
					i.putExtra("FLAG", "Owner");
					i.putExtra("FROM", "Chat");
					startActivity(i);
				}else {
					Intent i = new Intent(ChatActivity.this, ProfileActivity.class);
					i.putExtra("OID", mMessages.get(position).getUserId());
					i.putExtra("FLAG", "Friend");
					i.putExtra("FROM", "Chat");
					startActivity(i);
				}*//*


            }
        });*/
        btSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String body = etMessage.getText().toString();
                // Use Message model to create new messages now
                Message message = new Message();
                message.setUserId(sUserId);
                message.setUserName(sUserName);
                message.setDateTime(getDateTime());
                message.setProfileImage((String)ParseUser.getCurrentUser().get("profileImage"));
                message.setBody(body);
                message.setfriendobjectId(ParseUser.getCurrentUser().getObjectId()+friendObjectId);
                message.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            receiveMessage();
                            Utils.sendPush(sUserName + ": " + body, friendObjectId);
                        }

                    }
                });
                etMessage.setText("");
            }
        });
    }



    public static void receiveMessage() {
        ArrayList<String> user = new ArrayList<String>();
        String key1 = ParseUser.getCurrentUser().getObjectId()+friendObjectId;
        String key2 = friendObjectId+ParseUser.getCurrentUser().getObjectId();
        user.add(key1);
        user.add(key2);
        ParseQuery<Message> query = ParseQuery.getQuery(Message.class);
        //query.fromLocalDatastore();
        query.whereContainedIn("identificationKey", user);
        query.orderByDescending("createdAt");
        query.setLimit(50);
        query.findInBackground(new FindCallback<Message>() {
            public void done(List<Message> messages, ParseException e) {
                if (e == null) {

                    mMessages.clear();
                    Collections.reverse(messages);
                    mMessages.addAll(messages);
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.invalidate();
                    mRecyclerView.scrollToPosition(mMessages.size()-1);
                } else {
                    Log.d("message", "Error: " + e.getMessage());
                }
            }
        });
    }
}
