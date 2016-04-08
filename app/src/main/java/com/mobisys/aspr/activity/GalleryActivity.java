package com.mobisys.aspr.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.mobisys.aspr.adapter.GalleryAdapter;
import com.mobisys.aspr.model.Gallery;
import com.mobisys.aspr.util.ApplicationConstant;
import com.mobisys.aspr.util.SpacesItemDecoration;
import com.mobisys.aspr.util.Utils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.pktworld.aspr.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Prabhat on 06/04/16.
 */
public class GalleryActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = GalleryActivity.class.getSimpleName();
    private GalleryAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Gallery> allPosts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.txtToolbar);
        getSupportActionBar().setTitle("");
        mTitle.setText("Gallery");
        LocalBroadcastManager.getInstance(GalleryActivity.this).registerReceiver(mMessageReceiver,
                new IntentFilter(ApplicationConstant.FLAG));

        mRecyclerView = (RecyclerView) findViewById(R.id.listGallery);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(8));
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.red, R.color.parse_green, R.color.parse_color);
        allPosts = new ArrayList<Gallery>();
        refreshAllPost();
        mAdapter = new GalleryAdapter(GalleryActivity.this, allPosts);
        mRecyclerView.setAdapter(mAdapter);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String userId = intent.getStringExtra(ApplicationConstant.FLAG1);
            Intent i = new Intent(GalleryActivity.this, FullImageActivity.class);
            i.putExtra(ApplicationConstant.FLAG1, userId);
            startActivity(i);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
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

    @Override
    public void onRefresh() {
        if (Utils.isConnected(this)){
            receiveAllPost();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    void receiveAllPost() {
        ParseQuery<Gallery> query = ParseQuery.getQuery(Gallery.class);
        query.setLimit(2000);
        query.findInBackground(new FindCallback<Gallery>() {
            public void done(List<Gallery> messages, ParseException e) {
                if (e == null) {
                    allPosts.clear();
                    Collections.reverse(messages);
                    allPosts.addAll(messages);
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.invalidate();
                } else {
                    Log.d("message", "Error: " + e.getMessage());
                }
            }
        });


    }

    private void refreshAllPost() {
        receiveAllPost();

    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(GalleryActivity.this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }
}
