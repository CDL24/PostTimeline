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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.climbachiya.timelineexample.R;

import java.util.ArrayList;

import modal.UserInfo;

public class ColorItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<UserInfo> userInfo;
    Context mContext;
    private boolean test = false;

    public ColorItemsAdapter(Context context, ArrayList<UserInfo> userInfo) {
        this.userInfo = userInfo;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_post_cell, parent, false);
        return new SampleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        SampleViewHolder viewHolder = (SampleViewHolder) holder;
        viewHolder.imgMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Clicked : "+position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return userInfo.size() - (test ? 1 : 0);
    }

    public static class SampleViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgMedia;

        public SampleViewHolder(View view) {
            super(view);
            imgMedia = (ImageView) view.findViewById(R.id.post_image_media);
        }
    }
}