package com.mobisys.aspr.fragments;

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

import com.mobisys.aspr.activity.OneToOneChatActivity;
import com.mobisys.aspr.activity.SearchActivity;
import com.mobisys.aspr.adapter.FriendListAdapter;
import com.mobisys.aspr.model.FriendsResponse;
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
 * Created by ubuntu1 on 11/3/16.
 */
public class Friends extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = Friends.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<FriendsResponse> allFriends;
    private FriendListAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String userId = intent.getStringExtra(ApplicationConstant.FLAG);
            String userName = intent.getStringExtra(ApplicationConstant.FLAG1);

            Log.d(TAG, "Got message: " + userId);
            Intent i = new Intent(getActivity(), OneToOneChatActivity.class);
            i.putExtra(ApplicationConstant.FLAG, userId);
            i.putExtra(ApplicationConstant.FLAG1, userName);
            startActivity(i);
        }
    };

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
        allFriends = new ArrayList<FriendsResponse>();
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
        ArrayList<String> user = new ArrayList<String>();
        ParseQuery<FriendsResponse> query = ParseQuery.getQuery(FriendsResponse.class);
        query.setLimit(50);
        user.add(Utils.getUserId());
        query.whereNotContainedIn("userId", user);
        query.findInBackground(new FindCallback<FriendsResponse>() {
            public void done(List<FriendsResponse> messages, ParseException e) {
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
