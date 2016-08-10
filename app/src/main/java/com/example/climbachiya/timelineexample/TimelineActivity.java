package com.example.climbachiya.timelineexample;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import adapter.MainRecycleAdapter;
import adapter.TimelineAdapter;
import modal.UserInfo;
import utility.ConstantData;

/**
 * Created by C.limbachiya on 8/9/2016.
 */
public class TimelineActivity extends AppCompatActivity {

    SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView recycler;
    ArrayList<UserInfo> listUsers;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeline_activity);
        initUIControls();

        //initClassObjects();
        setUpToolbar();


        eventListeners();
    }

    private void eventListeners() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });

        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimaryDark),
                ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.colorAccent),
                ContextCompat.getColor(this, R.color.colorPrimaryDark)
                );

    }

    void refreshItems() {
        // Load items
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                onItemsLoadComplete();

            }
        }, 5000);
        // Load complete

    }

    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // Stop refresh animation
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupViews();
    }

    private void setUpToolbar() {
        // Enable up icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_timeline_listing);
    }

    private void setupViews() {
        if(ConstantData.timelinePost.size() <= 0){
            findViewById(R.id.text_no_data_found).setVisibility(View.VISIBLE);
        }else{

            findViewById(R.id.text_no_data_found).setVisibility(View.INVISIBLE);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(linearLayoutManager);
        recycler.setAdapter(new TimelineAdapter(this, ConstantData.timelinePost));
        //recyclerHeader = (RecyclerViewHeader)findViewById(R.id.header);
        //recyclerHeader.attachTo(recycler);
    }

    private void initUIControls() {
        recycler = (RecyclerView) findViewById(R.id.my_recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_post);
        item.setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post_location, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // This is the up button
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}