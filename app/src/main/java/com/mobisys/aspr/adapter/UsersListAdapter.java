package com.mobisys.aspr.adapter;

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
import android.widget.TextView;

import com.mobisys.aspr.imageloadingutil.ImageLoader;
import com.mobisys.aspr.util.ApplicationConstant;
import com.mobisys.aspr.util.CircularImage;
import com.parse.ParseUser;
import com.pktworld.aspr.R;

import java.util.List;

/**
 * Created by ubuntu1 on 18/3/16.
 */
public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.DataObjectHolder> {

    private static final String TAG = UsersListAdapter.class.getSimpleName();
    private List<ParseUser> mDataset;
    private Context mContext;
    private ImageLoader imageLoader;


    public UsersListAdapter(Context context, List<ParseUser> myDataset) {
        mDataset = myDataset;
        mContext = context;
        imageLoader =  ImageLoader.getInstance(mContext);
        //imageLoader = CustomVolleyRequestQueue.getInstance(mContext).getImageLoader();

    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, final int position) {

        holder.txtUserName.setText(mDataset.get(position).getUsername());
        //holder.userIcon.setImageUrl(mDataset.get(position).getString("profileImage"), imageLoader);
        //Picasso.with(mContext).load(mDataset.get(position).getString("profileImage")).into(holder.userIcon);
        loadUserIcon(mDataset.get(position).getString("profileImage"),holder.userIcon,mContext);
        /*holder.txtLocation.setText(Utils.getAddress(mDataset.get(position).get("latitude").toString(),
                mDataset.get(position).get("longitude").toString(),mContext));*/
        holder.txtLocation.setText(mContext.getString(R.string.location_not_available));
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sendMessage(mDataset.get(position).getUserId(),mDataset.get(position).getUserName());
            }
        });
        holder.btnRequestStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Utils.sendPush("Hi Prabhat", );
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

    public void addItem(ParseUser dataObj, int index) {
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

    public class DataObjectHolder extends RecyclerView.ViewHolder {

        TextView txtUserName, txtLocation;
        Button btnRequestStatus;
        CircularImage userIcon;
        CardView view;

        public DataObjectHolder(View itemView) {
            super(itemView);
            txtLocation = (TextView) itemView.findViewById(R.id.txtLocation);
            userIcon = (CircularImage) itemView.findViewById(R.id.imgUser);
            txtUserName = (TextView) itemView.findViewById(R.id.txtUserName);
            btnRequestStatus = (Button) itemView.findViewById(R.id.btnRequestStatus);
            view = (CardView) itemView.findViewById(R.id.card_view);

        }

    }
}