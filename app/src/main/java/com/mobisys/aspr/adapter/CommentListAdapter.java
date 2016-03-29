package com.mobisys.aspr.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobisys.aspr.imageloadingutil.ImageLoader;
import com.mobisys.aspr.model.Comments;
import com.mobisys.aspr.util.CircularImage;
import com.mobisys.aspr.util.Utils;
import com.pktworld.aspr.R;

import java.util.List;

/**
 * Created by ubuntu1 on 17/3/16.
 */
public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.DataObjectHolder> {

    private static final String TAG = CommentListAdapter.class.getSimpleName();
    private List<Comments> mDataset;
    private Context mContext;
    private ImageLoader imageLoader;
    private Utils utils;

    public CommentListAdapter(Context context, List<Comments> myDataset) {
        mDataset = myDataset;
        mContext = context;
        imageLoader = ImageLoader.getInstance(mContext);
        utils = new Utils(mContext);
        //imageLoader = CustomVolleyRequestQueue.getInstance(mContext).getImageLoader();

    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comments, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, final int position) {

        holder.txtCommentUser.setText(mDataset.get(position).getUserName());
        holder.txtCommentMessage.setText(mDataset.get(position).getComment());
        loadUserIcon(mDataset.get(position).getUserIcon(), holder.userIcon, mContext);
        holder.txtCommentDate.setText(utils.getTimeDiffrence(mContext,
                utils.formatDate(mDataset.get(position).getDateTime())));


    }

    private void loadUserIcon(String url,CircularImage imageView, Context context) {
        try{
            //Ion.with(context).load(url).intoImageView(imageView);
            imageLoader.DisplayImage(url,imageView);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }

    }

    public void addItem(Comments dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {

        TextView txtCommentUser, txtCommentMessage, txtCommentDate;
        CircularImage userIcon;
        CardView view;

        public DataObjectHolder(View itemView) {
            super(itemView);
            txtCommentMessage = (TextView) itemView.findViewById(R.id.txtCommentText);
            userIcon = (CircularImage) itemView.findViewById(R.id.imgUser);
            txtCommentUser = (TextView) itemView.findViewById(R.id.txtCommentUser);
            txtCommentDate = (TextView) itemView.findViewById(R.id.txtCommentDate);
            view = (CardView) itemView.findViewById(R.id.card_view);

        }

    }

}
