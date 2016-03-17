package com.mobisys.recipe.fragments;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mobisys.recipe.R;
import com.mobisys.recipe.activity.OneToOneChatActivity;
import com.mobisys.recipe.activity.SearchActivity;
import com.mobisys.recipe.adapter.FriendListAdapter;
import com.mobisys.recipe.util.ApplicationConstant;
import com.mobisys.recipe.util.SpacesItemDecoration;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ubuntu1 on 11/3/16.
 */
public class Friends extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = Friends.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressDialog mProgressDialog;
    private ArrayList<com.mobisys.recipe.model.Friends> allFriends;
    private FriendListAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_friends, container, false);

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,
                new IntentFilter(ApplicationConstant.FRIEND_LIST_ADAPTER));


        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.listFriends);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(8));
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipeRefreshLayout);
        allFriends = new ArrayList<com.mobisys.recipe.model.Friends>();
        mSwipeRefreshLayout.setOnRefreshListener(this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        receiveAllFriends();
        mAdapter = new FriendListAdapter(getActivity(), allFriends);
        mRecyclerView.setAdapter(mAdapter);
    }

    void receiveAllFriends(){
        ParseQuery<com.mobisys.recipe.model.Friends> query = ParseQuery.getQuery(com.mobisys.recipe.model.Friends.class);
        query.setLimit(50);
        query.findInBackground(new FindCallback<com.mobisys.recipe.model.Friends>() {
            public void done(List<com.mobisys.recipe.model.Friends> messages, ParseException e) {
                if (e == null) {

                    allFriends.clear();
                    Collections.reverse(messages);
                    allFriends.addAll(messages);
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.invalidate();
                } else {
                    Log.d("message", "Error: " + e.getMessage());
                }
            }
        });
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String userId = intent.getStringExtra(ApplicationConstant.FLAG);
            String userName = intent.getStringExtra(ApplicationConstant.FLAG1);

            Log.d(TAG, "Got message: " + userId);
            Intent i = new Intent(getActivity(), OneToOneChatActivity.class);
            i.putExtra(ApplicationConstant.FLAG,userId);
            i.putExtra(ApplicationConstant.FLAG1,userName);
            startActivity(i);
        }
    };


    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        onResume();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_friends_fragment, menu);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_search){
            Intent i = new Intent(getActivity(), SearchActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
