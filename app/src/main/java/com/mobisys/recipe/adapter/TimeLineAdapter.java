package com.mobisys.recipe.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobisys.recipe.R;
import com.mobisys.recipe.imageloadingutil.ImageLoader;
import com.mobisys.recipe.model.TimeLine;
import com.mobisys.recipe.util.ApplicationConstant;
import com.mobisys.recipe.util.CircularImage;
import com.mobisys.recipe.util.Utils;

import java.util.List;

/**
 * Created by Prabhat on 16/03/16.
 */
public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.DataObjectHolder> {

    private static final String TAG = TimeLineAdapter.class.getSimpleName();
    private List<TimeLine> mDataset;
    private Context mContext;
    private ImageLoader imageLoader;
    private Utils utils;

    public class DataObjectHolder extends RecyclerView.ViewHolder {

        TextView txtBody, txtUsername, txtTimeAgo;
        ImageView imagePost, audIcom;
        CircularImage userIcon;
        Button btnComments;
        CardView card_view;


        public DataObjectHolder(View itemView) {
            super(itemView);
            txtBody = (TextView) itemView.findViewById(R.id.txtTimeline);
            imagePost = (ImageView)itemView.findViewById(R.id.imgPost);
            txtTimeAgo = (TextView)itemView.findViewById(R.id.dateText);
            userIcon = (CircularImage)itemView.findViewById(R.id.imgUser);
            txtUsername = (TextView)itemView.findViewById(R.id.txtUsername);
            btnComments = (Button)itemView.findViewById(R.id.btnComments);
            card_view = (CardView)itemView.findViewById(R.id.card_view);
            audIcom = (ImageView)itemView.findViewById(R.id.audianceIcon);

        }

    }
    public TimeLineAdapter(Context context, List<TimeLine> myDataset) {
        mDataset = myDataset;
        mContext = context;
        imageLoader = ImageLoader.getInstance(mContext);
        utils = new Utils(mContext);

    }
    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_timeline, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, final int position) {

        holder.txtBody.setText(mDataset.get(position).getBodyText());
        holder.txtUsername.setText(mDataset.get(position).getUserName());
        //Log.e(TAG,mDataset.get(position).getDateTime());
        holder.txtTimeAgo.setText(utils.getTimeDiffrence(mContext, utils.formatDate(mDataset.get(position).getDateTime())));
        setAudIcon(holder.audIcom, mDataset.get(position).getAudiance());
        loadUserIcon(mDataset.get(position).getUserIcon(), holder.userIcon, mContext);
        /*String url = null;
        try{
            url = mDataset.get(position).getPostImage().getUrl();
        }catch (Exception e){
            e.printStackTrace();
            url = null;
        }*/

        if (mDataset.get(position).getIsImageSet()){
            String url = mDataset.get(position).getPostImage().getUrl();
            holder.imagePost.setVisibility(View.VISIBLE);
            loadImage(url, holder.imagePost, mContext);
        }else {
            holder.imagePost.setVisibility(View.GONE);

        }


        holder.btnComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommentMessage(mDataset.get(position).getObjectId().toString());
            }
        });

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataset.get(position).getIsImageSet()){
                    sendMessage(mDataset.get(position).getPostImage().getUrl());
                }

            }
        });

    }


    public void addItem(TimeLine dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void loadImage( String url,ImageView imageView, Context context) {
        imageLoader.DisplayImage(url,imageView);
    }

    private void loadUserIcon(String url,CircularImage imageView, Context context) {
        try{
            imageLoader.DisplayImage(url,imageView);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }

    }

    private void sendMessage( String url) {
        Log.d(TAG, "Broadcasting message");
        Intent intent = new Intent(ApplicationConstant.TIMELINE_ADAPTER);
        intent.putExtra(ApplicationConstant.IMAGE_URL, url);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    private void sendCommentMessage( String postId) {
        Log.d(TAG, "Broadcasting message");
        Intent intent = new Intent(ApplicationConstant.COMMENT_DIALOG_FRAGMENT);
        intent.putExtra(ApplicationConstant.FLAG, postId);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }



    private void setAudIcon(ImageView icon,int id){
        switch (id){
            case 1:
                 icon.setImageResource(R.drawable.ic_public_grey);
                break;
            case 2:
                icon.setImageResource( R.drawable.ic_personal_grey);
                break;
            case 3:
                icon.setImageResource( R.drawable.ic_friends_grey);
                break;
            default:
                icon.setImageResource(R.drawable.ic_public_grey);
                break;
        }

    }


}

