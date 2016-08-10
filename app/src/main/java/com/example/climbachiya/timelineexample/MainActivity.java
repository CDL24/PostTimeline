package com.example.climbachiya.timelineexample;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;

import java.util.ArrayList;

import adapter.MainRecycleAdapter;
import interfaces.TimelineUpdateListener;
import modal.UserInfo;
import utility.ConstantData;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerViewHeader recyclerHeader;
    private RecyclerView recycler;
    ArrayList<UserInfo> listUsers;
    Typeface fontAwesome;
    TextView txtPostStatus, txtPostImage, txtPostVideo, txtPostLocation;
    RecyclerViewHeader header;
    static MainRecycleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUIControls();

        initClassObjects();

        //setupViews();

        eventListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupViews();

    }

    private void initClassObjects() {
        fontAwesome = Typeface.createFromAsset(getAssets(), "fonts/fontawesome-webfont.ttf");
        /*listUsers = new ArrayList<>();
        listUsers.add(new UserInfo("1","A"));
        listUsers.add(new UserInfo("2","B"));
        listUsers.add(new UserInfo("3","C"));
        listUsers.add(new UserInfo("4","D"));
        listUsers.add(new UserInfo("5","E"));
        listUsers.add(new UserInfo("6","F"));
        listUsers.add(new UserInfo("7","G"));
        listUsers.add(new UserInfo("8","H"));
        listUsers.add(new UserInfo("9","I"));
        listUsers.add(new UserInfo("10","J"));
        listUsers.add(new UserInfo("11","K"));
        listUsers.add(new UserInfo("12","L"));
        listUsers.add(new UserInfo("13","M"));
        listUsers.add(new UserInfo("14","N"));
        listUsers.add(new UserInfo("15","O"));
        listUsers.add(new UserInfo("16","P"));
        listUsers.add(new UserInfo("17","Q"));*/
    }

    private void initUIControls() {
        recycler = (RecyclerView) findViewById(R.id.recycler);
        recyclerHeader = (RecyclerViewHeader)findViewById(R.id.header);
        header = (RecyclerViewHeader) findViewById(R.id.header);
        txtPostLocation = (TextView) findViewById(R.id.text_timeline_location);
        txtPostStatus = (TextView) findViewById(R.id.text_timeline_status);
        txtPostImage = (TextView) findViewById(R.id.text_timeline_image);
        txtPostVideo = (TextView) findViewById(R.id.text_timeline_video);
    }

    private void eventListeners() {

        txtPostStatus.setOnClickListener(this);
        txtPostImage.setOnClickListener(this);
        txtPostVideo.setOnClickListener(this);
        txtPostLocation.setOnClickListener(this);
        /*mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });*/
    }

    void refreshItems() {
        // Load items
        // ...

        //listUsers.clear();
        //initClassObjects();
        // Load complete
        onItemsLoadComplete();
    }

    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

        // Stop refresh animation
        //mSwipeRefreshLayout.setRefreshing(false);
    }

    private void setupViews() {

        if(ConstantData.timelinePost.size() <= 0){
            findViewById(R.id.text_no_data_found).setVisibility(View.VISIBLE);
        }else{

            findViewById(R.id.text_no_data_found).setVisibility(View.INVISIBLE);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(linearLayoutManager);
        adapter = new MainRecycleAdapter(this, ConstantData.timelinePost);
        recycler.setAdapter(adapter);

        /*adapter.setOnDataChangeListener(new TimelineUpdateListener() {
            @Override
            public void onNewPostAdded(int size) {
                Toast.makeText(MainActivity.this, "New Post Added :: "+ConstantData.timelinePost.size(), Toast.LENGTH_SHORT).show();
            }
        });*/
        //recyclerHeader = (RecyclerViewHeader)findViewById(R.id.header);
        recyclerHeader.attachTo(recycler);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.text_timeline_status :
                Toast.makeText(MainActivity.this, "Status", Toast.LENGTH_SHORT).show();
                break;
            case R.id.text_timeline_image :
                Toast.makeText(MainActivity.this, "Image", Toast.LENGTH_SHORT).show();
                break;
            case R.id.text_timeline_video :
                Toast.makeText(MainActivity.this, "Video", Toast.LENGTH_SHORT).show();
                break;
            case R.id.text_timeline_location :
                startActivity(new Intent(MainActivity.this, PostLocation.class));
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // This is the up button
            case R.id.action_next:
                goToNext();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void goToNext() {
        startActivity(new Intent(this, TimelineActivity.class));
    }
}
