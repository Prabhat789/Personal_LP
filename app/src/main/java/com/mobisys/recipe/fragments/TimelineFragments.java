package com.mobisys.recipe.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.kbeanie.imagechooser.api.ChooserType;
import com.kbeanie.imagechooser.api.ChosenImage;
import com.kbeanie.imagechooser.api.ImageChooserListener;
import com.kbeanie.imagechooser.api.ImageChooserManager;
import com.kbeanie.imagechooser.exceptions.ChooserException;
import com.mobisys.recipe.R;
import com.mobisys.recipe.adapter.TimeLineAdapter;
import com.mobisys.recipe.model.TimeLine;
import com.mobisys.recipe.util.SpacesItemDecoration;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by ubuntu1 on 11/3/16.
 */
public class TimelineFragments extends Fragment implements View.OnClickListener ,ImageChooserListener {
    private static final String TAG = TimelineFragments.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton fabAdd;
    private static final int RESULT_OK           = -1;
    private ImageChooserManager imageChooserManager;
    private String filePath="";
    private int chooserType;
    private String imagePath = "";
    private Dialog dialog;
    private ImageView imageCamera, imageGallery;
    private Bitmap imageBitmap = null;
    private byte[] ImageArray = null;
    private ProgressDialog mProgressDialog;
    private ArrayList<TimeLine> allPosts;
    private TimeLineAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_timeline, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.listHighway);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(8));
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        allPosts = new ArrayList<TimeLine>();
        refreshAllPost();
        mAdapter = new TimeLineAdapter(getActivity(), allPosts);
        mRecyclerView.setAdapter(mAdapter);
        fabAdd = (FloatingActionButton)rootView.findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(this);

        return rootView;

    }

    @Override
    public void onClick(View v) {
        if (v == fabAdd){
            imagePickerDialog(getActivity());
        }else if (v == imageCamera){
            dialog.dismiss();
                try {
                    takePicture();
                } catch (ChooserException e) {
                    e.printStackTrace();
                }

        }else if (v == imageGallery){
            dialog.dismiss();
                try {
                    chooseImage();
                } catch (ChooserException e) {
                    e.printStackTrace();
                }

        }
    }

    private void chooseImage() throws ChooserException {
        chooserType = ChooserType.REQUEST_PICK_PICTURE;
        imageChooserManager = new ImageChooserManager(this,
                ChooserType.REQUEST_PICK_PICTURE, "myfolder", true);
        imageChooserManager.setImageChooserListener(this);
        try {
            filePath = imageChooserManager.choose();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private void takePicture() throws ChooserException {
        chooserType = ChooserType.REQUEST_CAPTURE_PICTURE;
        imageChooserManager = new ImageChooserManager(this,
                ChooserType.REQUEST_CAPTURE_PICTURE, "myfolder", true);
        imageChooserManager.setImageChooserListener(this);
        try {
            filePath = imageChooserManager.choose();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private void reinitializeImageChooser() {
        imageChooserManager = new ImageChooserManager(this, chooserType,
                "myfolder", true);
        imageChooserManager.setImageChooserListener(this);
        imageChooserManager.reinitialize(filePath);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("chooser_type", chooserType);
        outState.putString("media_path", filePath);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onImageChosen(final ChosenImage image) {
        // TODO Auto-generated method stub
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (image != null) {
                    imagePath = image.getFilePathOriginal();
                    try {
                        filePath = image.getFilePathOriginal();
                        setupPost(filePath);
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }


                } else {
                    Toast.makeText(getActivity(), "Unable to get image path", Toast.LENGTH_SHORT).show();
                    imagePath = "";
                }
            }
        });

    }

    @Override
    public void onError(final String reason) {
        // TODO Auto-generated method stub
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(getActivity(), reason,
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("chooser_type")) {
                chooserType = savedInstanceState.getInt("chooser_type");
            }

            if (savedInstanceState.containsKey("media_path")) {
                filePath = savedInstanceState.getString("media_path");
            }
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK
                && (requestCode == ChooserType.REQUEST_PICK_PICTURE || requestCode == ChooserType.REQUEST_CAPTURE_PICTURE)) {
            if (imageChooserManager == null) {
                reinitializeImageChooser();
            }
            imageChooserManager.submit(requestCode, data);
        } else {

        }
    }

    void imagePickerDialog(Context mContext) {

        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_image_picker);
        dialog.setCancelable(true);
        imageCamera = (ImageView) dialog.findViewById(R.id.imageCamera);
        imageGallery = (ImageView) dialog.findViewById(R.id.imageGallery);
        imageCamera.setOnClickListener(this);
        imageGallery.setOnClickListener(this);

        dialog.show();
    }

    void setupPost(String filePath){
        mProgressDialog= ProgressDialog.show(getActivity(), "", getString(R.string.uploading), true);
        File imgFile = new  File(filePath);
        imageBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        ImageArray = stream.toByteArray();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        ParseFile file = new ParseFile("android" + timeStamp + ".png", ImageArray);
        file.saveInBackground();
        TimeLine timeLine = new TimeLine();
        timeLine.setPostImage(file);
        timeLine.setDateTime(timeStamp);
        timeLine.setUserIcon(getUserImage());
        timeLine.setUserId(ParseUser.getCurrentUser().getObjectId());
        timeLine.setUserName("Prabhat Tiwari");
        timeLine.setBodyText("Prabhat tiwari is a very good boy , " +
                "and he is an engineer in pune, working in software company.");

        timeLine.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    mProgressDialog.dismiss();
                    //receiveMessage();
                    receiveAllPost();
                }
            }
        });
    }

    void receiveAllPost(){
        ParseQuery<TimeLine> query = ParseQuery.getQuery(TimeLine.class);
        query.setLimit(50);
        query.findInBackground(new FindCallback<TimeLine>() {
            public void done(List<TimeLine> messages, ParseException e) {
                if (e == null) {

                    allPosts.clear();
                    Collections.reverse(messages);
                    allPosts.addAll(messages);
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.invalidate();
                    // mRecyclerView.scrollToPosition(allPosts.size()-1);
                } else {
                    Log.d("message", "Error: " + e.getMessage());
                }
            }
        });
    }

    private void refreshAllPost() {
        receiveAllPost();
    }

    private String getUserImage(){
        return ParseUser.getCurrentUser().getString("profileImage");
    }

}
