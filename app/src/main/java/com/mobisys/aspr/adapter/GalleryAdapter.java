package com.mobisys.aspr.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mobisys.aspr.imageloadingutil.ImageLoader;
import com.mobisys.aspr.model.Gallery;
import com.mobisys.aspr.util.ApplicationConstant;
import com.pktworld.aspr.R;

import java.util.List;

/**
 * Created by Prabhat on 06/04/16.
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.DataObjectHolder> {

    private static final String TAG = GalleryAdapter.class.getSimpleName();
    private List<Gallery> mDataset;
    private Context mContext;
    private ImageLoader imageLoader;

    public GalleryAdapter(Context context, List<Gallery> myDataset) {
        mDataset = myDataset;
        mContext = context;
        imageLoader = ImageLoader.getInstance(mContext);

    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gallery, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, final int position) {
        String url = mDataset.get(position).getImage().getUrl();
        holder.imagePost.setVisibility(View.VISIBLE);
        loadImage(url, holder.imagePost, mContext);
        holder.imagePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = mDataset.get(position).getImage().getUrl();
                sendCommentMessage(url);
            }
        });


    }


    public void addItem(Gallery dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void loadImage(String url, ImageView imageView, Context context) {
        imageLoader.DisplayImage(url, imageView);
    }


    public class DataObjectHolder extends RecyclerView.ViewHolder {

        ImageView imagePost;
        public DataObjectHolder(View itemView) {
            super(itemView);
            imagePost = (ImageView) itemView.findViewById(R.id.imgGallery);


        }


    }
    private void sendCommentMessage( String postId) {
        Log.d(TAG, "Broadcasting message");
        Intent intent = new Intent(ApplicationConstant.FLAG);
        intent.putExtra(ApplicationConstant.FLAG1, postId);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }
}


