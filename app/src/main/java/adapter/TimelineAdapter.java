/*
 * Copyright 2015 Bartosz Lipinski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.climbachiya.timelineexample.PostLocation;
import com.example.climbachiya.timelineexample.R;

import java.util.ArrayList;

import modal.Timeline;

public class TimelineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    ArrayList<Timeline> timelines;
    Context mContext;
    private boolean test = false;
    //SampleViewHolder viewHolder;

    public TimelineAdapter(Context context, ArrayList<Timeline> timelines) {
        this.timelines = timelines;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == TYPE_HEADER)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.include_post_timeline, parent, false);
            return  new VHHeader(v);
        }
        else if(viewType == TYPE_ITEM)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_post_cell, parent, false);
            return new VHItem(v);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
        /*final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_post_cell, parent, false);
        return new SampleViewHolder(view);*/
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //final SampleViewHolder viewHolder = (SampleViewHolder) holder;



        if(holder instanceof VHHeader)
        {
            VHHeader VHheader = (VHHeader)holder;
            VHheader.txtPostLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, PostLocation.class));
                }
            });
        }
        else if(holder instanceof VHItem)
        {
            final Timeline postItem = timelines.get(position-1);
            //ListItem currentItem = getItem(position-1);
            final VHItem VHitem = (VHItem)holder;
            if(postItem.getPostText() != null){
                VHitem.txtPostDesc.setText(postItem.getPostText());
            }

            if(postItem.getPostTime() != null){
                VHitem.txtPostTime.setText(postItem.getPostTime());
            }

            VHitem.mProgressLoader.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(postItem.getPostMapUrl())
                    .centerCrop()
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            VHitem.mProgressLoader.setVisibility(View.INVISIBLE);
                            Toast.makeText(mContext, "onException", Toast.LENGTH_SHORT).show();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            VHitem.mProgressLoader.setVisibility(View.INVISIBLE);
                            Toast.makeText(mContext, "onResourceReady", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    })
                    .into(VHitem.imgPostMedia);

            VHitem.imgPostMedia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openMap(postItem.getPostLatitude(), postItem.getPostLongitude(), postItem.getPostPlaceName());
                }
            });
        }





        /*Glide
            .with(mContext)
            .load(postItem.getPostMapUrl())
            .centerCrop()
            .crossFade()
            .into(viewHolder.imgPostMedia);*/


    }

    //Need to override this method
    @Override
    public int getItemViewType(int position) {
        if(isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position)
    {
        return position == 0;
    }

    //increasing getItemcount to 1. This will be the row of header.
    @Override
    public int getItemCount() {
        return timelines.size() + 1;
    }

    class VHHeader extends RecyclerView.ViewHolder{

        TextView txtPostStatus, txtPostImage, txtPostVideo, txtPostLocation;

        public VHHeader(View headerView) {
            super(headerView);
            txtPostLocation = (TextView) headerView.findViewById(R.id.text_timeline_location);
            txtPostStatus = (TextView) headerView.findViewById(R.id.text_timeline_status);
            txtPostImage = (TextView) headerView.findViewById(R.id.text_timeline_image);
            txtPostVideo = (TextView) headerView.findViewById(R.id.text_timeline_video);
        }
    }

    class VHItem extends RecyclerView.ViewHolder{
        private ImageView imgPostMedia;
        private TextView txtPostDesc;
        private TextView txtPostTime;
        private ProgressBar mProgressLoader;
        public VHItem(View itemView) {
            super(itemView);
            imgPostMedia = (ImageView) itemView.findViewById(R.id.post_image_media);
            txtPostDesc = (TextView) itemView.findViewById(R.id.text_post_desc);
            txtPostTime = (TextView) itemView.findViewById(R.id.text_post_time);
            mProgressLoader = (ProgressBar) itemView.findViewById(R.id.progress_loader_timeline);
        }
    }

    /*public static class SampleViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgPostMedia;
        private TextView txtPostDesc;
        private TextView txtPostTime;
        private ProgressBar mProgressLoader;

        public SampleViewHolder(View view) {
            super(view);
            imgPostMedia = (ImageView) view.findViewById(R.id.post_image_media);
            txtPostDesc = (TextView) view.findViewById(R.id.text_post_desc);
            txtPostTime = (TextView) view.findViewById(R.id.text_post_time);
            mProgressLoader = (ProgressBar) view.findViewById(R.id.progress_loader_timeline);
        }
    }*/

    /**
     * Open google map with location
     * @param PLACE_LATITUDE -- location latitude
     * @param PLACE_LONGITUDE -- location longitude
     */
    protected void openMap(String PLACE_LATITUDE, String PLACE_LONGITUDE, String PLACE_NAME) {

        boolean installedMaps = false;

        // CHECK IF GOOGLE MAPS IS INSTALLED
        PackageManager pkManager = mContext.getPackageManager();
        try {
            @SuppressWarnings("unused")
            PackageInfo pkInfo = pkManager.getPackageInfo("com.google.android.apps.maps", 0);
            installedMaps = true;
        } catch (Exception e) {
            e.printStackTrace();
            installedMaps = false;
        }

        // SHOW THE MAP USING CO-ORDINATES FROM THE CHECKING
        if (installedMaps == true) {
            String geoCode = "geo:0,0?q=" + PLACE_LATITUDE + ","
                    + PLACE_LONGITUDE + "(" + PLACE_NAME + ")";
            Intent sendLocationToMap = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(geoCode));
            mContext.startActivity(sendLocationToMap);
        } else if (installedMaps == false) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);

            // SET THE ICON
            alertDialogBuilder.setIcon(R.drawable.ic_location);

            // SET THE TITLE
            //alertDialogBuilder.setTitle("Google Maps Not Found");

            // SET THE MESSAGE
            alertDialogBuilder
                    .setMessage(mContext.getString(R.string.wrn_map_not_installed))
                    .setCancelable(false)
                    .setNeutralButton("Got It",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    dialog.dismiss();
                                }
                            });

            // CREATE THE ALERT DIALOG
            AlertDialog alertDialog = alertDialogBuilder.create();

            // SHOW THE ALERT DIALOG
            alertDialog.show();
        }
    }
}