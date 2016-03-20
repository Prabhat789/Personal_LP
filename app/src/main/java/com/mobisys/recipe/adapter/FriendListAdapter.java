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
import android.widget.TextView;

import com.mobisys.recipe.R;
import com.mobisys.recipe.imageloadingutil.ImageLoader;
import com.mobisys.recipe.model.Friends;
import com.mobisys.recipe.util.ApplicationConstant;
import com.mobisys.recipe.util.CircularImage;

import java.util.List;

/**
 * Created by ubuntu1 on 17/3/16.
 */
public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.DataObjectHolder> {

    private static final String TAG = TimeLineAdapter.class.getSimpleName();
    private List<Friends> mDataset;
    private Context mContext;
    private ImageLoader imageLoader;


    public class DataObjectHolder extends RecyclerView.ViewHolder {

        TextView txtUserName, txtMessageBody;
        CircularImage userIcon;
        CardView view;

        public DataObjectHolder(View itemView) {
            super(itemView);
            txtMessageBody = (TextView) itemView.findViewById(R.id.txtMessageBody);
            userIcon = (CircularImage)itemView.findViewById(R.id.imgUser);
            txtUserName = (TextView)itemView.findViewById(R.id.txtUserName);
            view = (CardView)itemView.findViewById(R.id.card_view);

        }

    }
    public FriendListAdapter(Context context, List<Friends> myDataset) {
        mDataset = myDataset;
        mContext = context;
        imageLoader = new ImageLoader(mContext);
        //imageLoader = CustomVolleyRequestQueue.getInstance(mContext).getImageLoader();

    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_friends, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, final int position) {

        holder.txtUserName.setText(mDataset.get(position).getUserName());
        holder.txtMessageBody.setText(mDataset.get(position).getBodyText());

        loadUserIcon(mDataset.get(position).getUserIcon(),holder.userIcon,mContext);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(mDataset.get(position).getUserId(),mDataset.get(position).getUserName());
            }
        });


    }
    private void loadUserIcon(String url,CircularImage imageView, Context context) {
        try{
            //Ion.with(context).load(url).intoImageView(imageView);
            imageLoader.DisplayImage(url,imageView);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }

    }


    public void addItem(Friends dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    private void sendMessage(String fId, String usName) {
        Log.d(TAG, "Broadcasting message");
        Intent intent = new Intent(ApplicationConstant.FRIEND_LIST_ADAPTER);
        intent.putExtra(ApplicationConstant.FLAG, fId);
        intent.putExtra(ApplicationConstant.FLAG1, usName);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }
}
