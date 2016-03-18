package com.mobisys.recipe.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.mobisys.recipe.R;
import com.mobisys.recipe.adapter.UsersListAdapter;
import com.mobisys.recipe.util.SpacesItemDecoration;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Prabhat on 09/01/16.
 */
public class SearchActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = SearchActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private EditText editSearch;

    private ArrayList<ParseUser> allUsers;
    private UsersListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        editSearch = (EditText)findViewById(R.id.searchEditText);
        mRecyclerView = (RecyclerView) findViewById(R.id.listUsers);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(8));
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        allUsers = new ArrayList<ParseUser>();


       /* editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch(editSearch.getText().toString().trim());
                    editSearch.setText("");
                    return true;
                }
                return false;
            }

        });*/


        receiveAllUsers("");
        mAdapter = new UsersListAdapter(SearchActivity.this, allUsers);
        mRecyclerView.setAdapter(mAdapter);

    }


    /*private void performSearch(String cs) {
        if (!cs.isEmpty() && cs.length() >0){
            *//*db.addHistory(new HistoryModel(cs));
            if (Utils.isConnected(SearchActivity.this)){
                loadSearchList(SearchActivity.this,"search.php",cs);
            }*//*
            receiveAllUsers(cs);
            mAdapter = new UsersListAdapter(SearchActivity.this, allUsers);
            mRecyclerView.setAdapter(mAdapter);
        }
    }
*/
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
    public void onClick(View v) {

    }

    void receiveAllUsers(String q){
        //ParseQuery<ParseUser> query = ParseUser.getQuery(ParseUser.class);
        ParseQuery<ParseUser> query = ParseUser.getQuery();
       // query.whereMatches("username",q);
        query.setLimit(70);
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> messages, ParseException e) {
                if (e == null) {
                    allUsers.clear();
                    Collections.reverse(messages);
                    allUsers.addAll(messages);
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.invalidate();
                } else {
                    Log.d("message", "Error: " + e.getMessage());
                }
            }
        });
    }


}
