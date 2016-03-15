package com.mobisys.recipe.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.mobisys.recipe.R;
import com.mobisys.recipe.model.Message;
import com.mobisys.recipe.util.CustomVolleyRequestQueue;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;


/**
 * Created by ubuntu1 on 15/3/16.
 */
public class OneToOneChatListAdapter extends RecyclerView.Adapter<OneToOneChatListAdapter.DataObjectHolder> {

    private static final String TAG = OneToOneChatListAdapter.class.getSimpleName();
    private List<Message> mDataset;
    private Context mContext;
    private ImageLoader imageLoader;
    private String mUserId;

    public class DataObjectHolder extends RecyclerView.ViewHolder {

        TextView txtBody;
        ImageView imageLeft, imageRight;
        LinearLayout llBody;

        public DataObjectHolder(View itemView) {
            super(itemView);
            imageLeft = (NetworkImageView) itemView.findViewById(R.id.ivProfileLeft);
            imageRight = (NetworkImageView) itemView.findViewById(R.id.ivProfileRight);
            txtBody = (TextView) itemView.findViewById(R.id.txtBody);
            llBody = (LinearLayout)itemView.findViewById(R.id.llText);

            //txtUserName = (TextView) itemView.findViewById(R.id.userName);
            //llBody = (LinearLayout)itemView.findViewById(R.id.llBody);
        }

    }
    public OneToOneChatListAdapter(Context context,String userId, List<Message> myDataset) {
        mDataset = myDataset;
        mContext = context;
        mUserId = userId;
        imageLoader = CustomVolleyRequestQueue.getInstance(mContext).getImageLoader();


    }
    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat1, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        final boolean isMe = mDataset.get(position).getUserId().equals(mUserId);
       // LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (isMe) {
            holder.imageRight.setVisibility(View.VISIBLE);
            holder.imageLeft.setVisibility(View.GONE);
           // holder.txtBody.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
           // params.gravity = Gravity.CENTER_VERTICAL | Gravity.RIGHT;
             holder.txtBody.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
            //holder.txtBody.setLayoutParams(params);
            holder.txtBody.setTextColor(mContext.getResources().getColor(R.color.smoke_white));
            holder.llBody.setGravity(Gravity.CENTER_VERTICAL |Gravity.RIGHT);
           // holder.txtUserName.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
           // holder.llBody.setBackground(mContext.getResources().getDrawable(R.drawable.bg_me));
            // holder.body.setTextColor(Color.parseColor("#29464A"));

        } else {
            holder.imageLeft.setVisibility(View.VISIBLE);
            holder.imageRight.setVisibility(View.GONE);

           // params.gravity = Gravity.CENTER_VERTICAL | Gravity.LEFT;
            holder.txtBody.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
           // holder.txtBody.setLayoutParams(params);
            holder.txtBody.setTextColor(mContext.getResources().getColor(R.color.cardview_dark_background));
            holder.llBody.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
           // holder.txtUserName.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            //holder.llBody.setBackground(mContext.getResources().getDrawable(R.drawable.bg_friend));
            //  holder.body.setTextColor(Color.parseColor("#301041"));

        }
        final NetworkImageView profileView = (NetworkImageView) (isMe ? holder.imageRight : holder.imageLeft);
        try {
            if (mDataset.get(position).getProfileImage().length() != 0) {
                /*imageLoader.displayImage(message.getProfileImage(),
                        profileView, options);*/
                imageLoader.get( mDataset.get(position).getProfileImage(), ImageLoader.getImageListener(
                        profileView,
                        R.mipmap.ic_launcher, R.mipmap.ic_launcher));
                profileView.setImageUrl( mDataset.get(position).getProfileImage(), imageLoader);
                //Picasso.with(getContext()).load(message.getProfileImage()).into(profileView);
            }else {
                /*imageLoader.displayImage(getProfileUrl(message.getUserId()),
                        profileView, options);*/
                imageLoader.get(getProfileUrl( mDataset.get(position).getUserId()), ImageLoader.getImageListener(
                        profileView,
                        R.mipmap.ic_launcher, R.mipmap.ic_launcher));
                profileView.setImageUrl(mDataset.get(position).getProfileImage(), imageLoader);
                //Picasso.with(getContext()).load(getProfileUrl(message.getUserId())).into(profileView);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        holder.txtBody.setText(mDataset.get(position).getBody());
        //holder.body.setTypeface(myTypeface);
        //holder.txtUserName.setText(mDataset.get(position).getUserName() + " at " + mDataset.get(position).getDateTime());
        profileView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
               // fullImageDialog(message.getProfileImage());
            }
        });
    }
    // Create a gravatar image based on the hash value obtained from userId
    private static String getProfileUrl(final String userId) {
        String hex = "";
        try {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            final byte[] hash = digest.digest(userId.getBytes());
            final BigInteger bigInt = new BigInteger(hash);
            hex = bigInt.abs().toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "http://www.gravatar.com/avatar/" + hex + "?d=identicon";
    }

    public void addItem(Message dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}
