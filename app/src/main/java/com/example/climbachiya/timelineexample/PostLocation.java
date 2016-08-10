package com.example.climbachiya.timelineexample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import interfaces.TimelineUpdateListener;
import modal.Timeline;
import receivers.MyBroadcastReceiver;
import utility.AppUtils;
import utility.ConstantData;
import utility.MarshMallowPermission;

/**
 * Created by C.limbachiya on 8/4/2016.
 */
public class PostLocation extends AppCompatActivity implements TimelineUpdateListener {

    private static final int REQUEST_PLACE_PICKER = 1;
    MarshMallowPermission mPermission;
    EditText edtLocationPost;
    ImageView imgMap;

    //Map data to store in array
    String staticMapUrl;
    String mapLatitude;
    String mapLongitude;
    String mapPlaceName;

    TimelineUpdateListener mOnNewPostAdded;
    MyBroadcastReceiver myBroadcastReceiver = null;
    IntentFilter intentFilter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.post_location);

        initClassObjects();
        initUIControls();
        setUpToolbar();
    }

    private void initUIControls() {
        edtLocationPost = (EditText) findViewById(R.id.edit_location_post);
        imgMap = (ImageView) findViewById(R.id.image_location_screen_shot);
    }

    private void initClassObjects() {
        mPermission = new MarshMallowPermission(this);
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.climbachiya.DYNAMIC_BROADCAST");
        myBroadcastReceiver = new MyBroadcastReceiver(PostLocation.this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(myBroadcastReceiver, intentFilter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!mPermission.checkPermissionForLocation()) {
                mPermission.requestForLocation();
            }
            /*if (!mPermission.checkPermissionForExternalStorage()) {
                mPermission.requestForExternalStorage();
            }*/
            /*else {
                checkLocationEnable();
            }*/
        } /*else {
            checkLocationEnable();
        }*/
    }

    private void checkLocationEnable() {
        // If this check succeeds, proceed with normal processing.
        // Otherwise, prompt user to get valid Play Services APK.
        if (!AppUtils.isLocationEnabled(this)) {
            // notify user
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("Location not enabled!");
            dialog.setPositiveButton("Open location settings", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                }
            });
            dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub

                }
            });
            dialog.show();
        } else {
            // Construct an intent for the place picker
            try {
                PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
                Intent intent = intentBuilder.build(this);
                // Start the Intent by requesting a result, identified by a request code.
                startActivityForResult(intent, REQUEST_PLACE_PICKER);

                // Hide the pick option in the UI to prevent users from starting the picker
                // multiple times.
                //showPickAction(false);

            } catch (GooglePlayServicesRepairableException e) {
                GooglePlayServicesUtil
                        .getErrorDialog(e.getConnectionStatusCode(), this, 0);
            } catch (GooglePlayServicesNotAvailableException e) {
                Toast.makeText(this, "Google Play Services is not available.",
                        Toast.LENGTH_LONG)
                        .show();
            }
        }

    }

    private void setUpToolbar() {
        // Enable up icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_post_location);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post_location, menu);
        return true;
    }

    //Load location picker view to get location
    public void pickLocation(View v) {
        checkLocationEnable();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // BEGIN_INCLUDE(activity_result)
        if (requestCode == REQUEST_PLACE_PICKER) {
            // This result is from the PlacePicker dialog.

            // Enable the picker option
            //showPickAction(true);

            findViewById(R.id.progress_loader).setVisibility(View.VISIBLE);
            if (resultCode == Activity.RESULT_OK) {
                /* User has picked a place, extract data.
                   Data is extracted from the returned intent by retrieving a Place object from
                   the PlacePicker.
                 */
                final Place place = PlacePicker.getPlace(data, this);

                /* A Place object contains details about that place, such as its name, address
                and phone number. Extract the name, address, phone number, place ID and place types.
                 */
                final CharSequence name = place.getName();
                final CharSequence address = place.getAddress();
                final CharSequence phone = place.getPhoneNumber();
                final String placeId = place.getId();
                String attribution = PlacePicker.getAttributions(data);
                double latitude = place.getLatLng().latitude;
                double longitude = place.getLatLng().longitude;

                mapLatitude = String.valueOf(latitude);
                mapLongitude = String.valueOf(longitude);
                mapPlaceName = (String) name;

                if(attribution == null){
                    attribution = "";
                }

                // Update data on card.
                /*getCardStream().getCard(CARD_DETAIL)
                        .setTitle(name.toString())
                        .setDescription(getString(R.string.detail_text, placeId, address, phone,
                                attribution));*/

                //edtLocationPost.setText(name +"\n"+address+"\n"+"latitude:"+latitude+"\n"+"longitude:"+longitude);
                // Print data to debug log
                //Log.d("Place : ", "latitude: " + latitude + " longitude: "+longitude);
                //Log.d("Place : ", "Place selected: " + placeId + " (" + name.toString() + ")");

                //String staticMapUrl = "https://maps.googleapis.com/maps/api/staticmap?center=" + latitude + "," + longitude + "&zoom=17&size=500x400&maptype=roadmap&markers=size:large%7Ccolor:0x00b489%7Clabel:S%"+ latitude + "," + longitude +"&sensor=false";

                staticMapUrl = "https://maps.googleapis.com/maps/api/staticmap?center="+ latitude + "," + longitude +"&zoom=15&size=600x400&maptype=roadmap&markers=size:large%7Ccolor:0x00b489%7Clabel:%7C"+ latitude + "," + longitude +"&sensor=false";
                /*String staticMapUrl = "https://maps.googleapis.com/maps/api/staticmap?center=Brooklyn+Bridge,New+York,NY&zoom=13&size=600x300&maptype=roadmap\n" +
                        "&markers=color:blue%7Clabel:S%7C40.702147,-74.015794&markers=color:green%7Clabel:G%7C40.711614,-74.012318\n" +
                        "&markers=color:red%7Clabel:C%7C40.718217,-73.998284" +
                        "&key=AIzaSyCM4jSDtpiFhR0cnlt5Fd_H_WQeS7_9veM";*/
                Glide
                        .with(getApplicationContext())
                        .load(staticMapUrl)
                        .asBitmap()
                        .centerCrop()
                        .listener(new RequestListener<String, Bitmap>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                                Toast.makeText(PostLocation.this, "Error while downloading image : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                findViewById(R.id.progress_loader).setVisibility(View.INVISIBLE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                findViewById(R.id.progress_loader).setVisibility(View.INVISIBLE);
                                return false;
                            }
                        })
                        .into(new SimpleTarget<Bitmap>(600,400) {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                                imgMap.setImageBitmap(resource); // Possibly runOnUiThread()
                            }
                        });
                // Show the card.
                //getCardStream().showCard(CARD_DETAIL);

            } else {
                findViewById(R.id.progress_loader).setVisibility(View.INVISIBLE);
                // User has not selected a place, hide the card.
                //getCardStream().hideCard(CARD_DETAIL);
                Toast.makeText(PostLocation.this, "User has not selected a place, hide the card", Toast.LENGTH_SHORT).show();
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
        // END_INCLUDE(activity_result)
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // This is the up button
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_post:
                postTimeLine();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Post Timeline in array list
    private void postTimeLine() {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
        String datetime = dateformat.format(c.getTime());

        Collections.reverse(ConstantData.timelinePost);

        ConstantData.timelinePost.add(new Timeline(mapLatitude, mapLongitude, mapPlaceName, edtLocationPost.getText().toString(), datetime, staticMapUrl));
        Collections.reverse(ConstantData.timelinePost);

        Intent intent = new Intent();
        intent.setAction("com.example.climbachiya.DYNAMIC_BROADCAST");
        sendBroadcast(intent);

        finish();
    }
    @Override
    protected void onStop() {
        super.onStop();

        if (null != myBroadcastReceiver)
            unregisterReceiver(myBroadcastReceiver);
    }

    @Override
    public void onDataAvailable(ArrayList<Timeline> newDataList) {
        // assuming you want to replace existing data and not willing to append to existing dataset
        /*MainActivity.adapter.clear();
        MainActivity.adapter.addAll(newDataList);
        MainActivity.adapter.notifyDataSetChanged();*/
        Toast.makeText(this, "New Post Added :: "+newDataList.size(), Toast.LENGTH_SHORT).show();
    }
}
