package com.mobisys.recipe.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mobisys.recipe.R;

/**
 * Created by Prabhat on 09/01/16.
 */
public class SearchActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "SearchActivity";
    private ListView listSearch;
    private EditText editSearch;
    private Button btnClearHistory;
    private LinearLayout layoutList, layoutRecyclerList;
    private ListView listV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        editSearch = (EditText)findViewById(R.id.searchEditText);
        listSearch = (ListView)findViewById(R.id.listHistory);
        btnClearHistory = (Button)findViewById(R.id.btnClearHistory);
        btnClearHistory.setOnClickListener(this);

        layoutList = (LinearLayout)findViewById(R.id.layoutList);
        layoutRecyclerList = (LinearLayout)findViewById(R.id.layoutRecyclerList);
        layoutRecyclerList.setVisibility(View.GONE);
        listV = (ListView)findViewById(R.id.cardList);
        /*mRecyclerView = (RecyclerView) findViewById(R.id.cardList);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(8));
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);*/

        //loadData();


        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch(editSearch.getText().toString().trim());
                    editSearch.setText("");
                    return true;
                }
                return false;
            }

        });




    }
    private void performSearch(String cs) {
        if (!cs.isEmpty() && cs.length() >0){
            /*db.addHistory(new HistoryModel(cs));
            if (Utils.isConnected(SearchActivity.this)){
                loadSearchList(SearchActivity.this,"search.php",cs);
            }*/

        }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v == btnClearHistory){
            try{

            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }


}
