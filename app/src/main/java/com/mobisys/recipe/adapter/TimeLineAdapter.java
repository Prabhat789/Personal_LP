package com.mobisys.recipe.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.mobisys.recipe.R;
import com.mobisys.recipe.model.TimeLine;
import com.mobisys.recipe.util.CircularImage;
import com.mobisys.recipe.util.CustomVolleyRequestQueue;

import java.util.List;

/**
 * Created by Prabhat on 16/03/16.
 */
public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.DataObjectHolder> {

    private static final String TAG = TimeLineAdapter.class.getSimpleName();
    private List<TimeLine> mDataset;
    private Context mContext;
    private ImageLoader imageLoader;

    public class DataObjectHolder extends RecyclerView.ViewHolder {

        TextView txtBody, txtUsername;
        NetworkImageView imagePost;
        CircularImage userIcon;
        Button btnComments;

        public DataObjectHolder(View itemView) {
            super(itemView);
            txtBody = (TextView) itemView.findViewById(R.id.txtTimeline);
            imagePost = (NetworkImageView)itemView.findViewById(R.id.imgPost);
            userIcon = (CircularImage)itemView.findViewById(R.id.imgUser);
            txtUsername = (TextView)itemView.findViewById(R.id.txtUsername);
            btnComments = (Button)itemView.findViewById(R.id.btnComments);

        }

    }
    public TimeLineAdapter(Context context, List<TimeLine> myDataset) {
        mDataset = myDataset;
        mContext = context;
        imageLoader = CustomVolleyRequestQueue.getInstance(mContext).getImageLoader();



    }
    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_timeline, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        holder.txtBody.setText(mDataset.get(position).getBodyText());
        holder.txtUsername.setText(mDataset.get(position).getUserName());
        imageLoader.get(mDataset.get(position).getPostImage().getUrl(), ImageLoader.getImageListener(
                holder.imagePost,
                R.drawable.ic_loading, R.drawable.ic_error));
        holder.imagePost.setImageUrl(mDataset.get(position).getPostImage().getUrl(), imageLoader);
        imageLoader.get(mDataset.get(position).getUserIcon(), ImageLoader.getImageListener(
                holder.userIcon,
                R.drawable.ic_loading, R.drawable.ic_error));
        holder.userIcon.setImageUrl(mDataset.get(position).getUserIcon(), imageLoader);

        /*loadImage(mDataset.get(position).getPostImage().getUrl(),holder.imagePost,mContext);
        loadUserIcon(mDataset.get(position).getUserIcon(), holder.userIcon, mContext);*/


        holder.btnComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    /*public void loadImage( String url,ImageView imageView, Context context) {
        Ion.with(context).load(url).intoImageView(imageView);
    }

    private void loadUserIcon(String url,CircularImage imageView, Context context) {
        try{
            Ion.with(context).load(url).intoImageView(imageView);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }

    }*/


}

