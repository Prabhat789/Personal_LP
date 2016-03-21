package com.mobisys.recipe.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.kbeanie.imagechooser.api.ChooserType;
import com.kbeanie.imagechooser.api.ChosenImage;
import com.kbeanie.imagechooser.api.ImageChooserListener;
import com.kbeanie.imagechooser.api.ImageChooserManager;
import com.kbeanie.imagechooser.exceptions.ChooserException;
import com.mobisys.recipe.R;
import com.mobisys.recipe.adapter.TimeLineAdapter;
import com.mobisys.recipe.imageloadingutil.ImageLoader;
import com.mobisys.recipe.model.TimeLine;
import com.mobisys.recipe.util.ApplicationConstant;
import com.mobisys.recipe.util.CircularImage;
import com.mobisys.recipe.util.SpacesItemDecoration;
import com.mobisys.recipe.util.Utils;
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
public class TimelineFragments extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = TimelineFragments.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton fabAdd;
    private static final int RESULT_OK           = -1;
    private static ImageChooserManager imageChooserManager;
    private static String filePath="";
    private static int chooserType;
    private Bitmap imageBitmap = null;
    private byte[] ImageArray = null;
    private String imagePath = "";
    private Dialog dialog;
    //private ImageView imageCamera, imageGallery;
    private ProgressDialog mProgressDialog;
    private ArrayList<TimeLine> allPosts;
    private TimeLineAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_timeline, container, false);


        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.listHighway);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(8));
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.red, R.color.parse_green, R.color.parse_color);
        allPosts = new ArrayList<TimeLine>();
        refreshAllPost();
        mAdapter = new TimeLineAdapter(getActivity(), allPosts);
        mRecyclerView.setAdapter(mAdapter);
        fabAdd = (FloatingActionButton)rootView.findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(this);

        /*mRecyclerView.setOnScrollListener(new ScrollListener(getActivity()) {
            @Override
            public void onMoved(int distance) {
                HomeScreenActivity.tabLayout.setTranslationY(-distance);
            }
        });*/

        return rootView;

    }

    @Override
    public void onClick(View v) {
        if (v == fabAdd){
            showDialog();
        }
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



    @Override
    public void onRefresh() {
        refreshAllPost();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    void showDialog() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        DialogFragment newFragment = PostDialogFragment.newInstance();
        newFragment.show(ft, "");
    }



    void setupPost(String status,String filePath, int audiance){
        mProgressDialog= ProgressDialog.show(getActivity(), "", getString(R.string.uploading), true);
        File imgFile = new  File(filePath);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        TimeLine timeLine = new TimeLine();
        if (imgFile.exists()){
            imageBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 25, stream);
            ImageArray = stream.toByteArray();
            ParseFile file = new ParseFile("android" + timeStamp + ".JPEG", ImageArray);
            file.saveInBackground();
            timeLine.setPostImage(file);
            timeLine.setDateTime(timeStamp);
            timeLine.setUserIcon(getUserImage());
            timeLine.setUserId(ParseUser.getCurrentUser().getObjectId());
            timeLine.setUserName(ParseUser.getCurrentUser().getUsername());
            timeLine.setBodyText(status);
            timeLine.setAudiance(audiance);
        }else{
           // timeLine.setPostImage(new ParseFile(""));
            timeLine.setDateTime(timeStamp);
            timeLine.setUserIcon(getUserImage());
            timeLine.setUserId(ParseUser.getCurrentUser().getObjectId());
            timeLine.setUserName(ParseUser.getCurrentUser().getUsername());
            timeLine.setBodyText(status);
            timeLine.setAudiance(audiance);
        }
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
    private String getUserImage(){
        return ParseUser.getCurrentUser().getString("profileImage");
    }

    public static class PostDialogFragment extends DialogFragment implements ImageChooserListener, View.OnClickListener {

        private static String imagePath = null;
        private ImageButton imageCamera,imageGallery;
        private Button btnSave, btnCancel;
        private ImageView dialogImageContainer;
        private EditText editStatusDialog;
        private static PostDialogFragment f;
        private  int audiance;
        private RadioGroup audianceGroup;
        static PostDialogFragment newInstance() {
             f = new PostDialogFragment();
            f.setStyle(DialogFragment.STYLE_NORMAL, R.style.You_Dialog);
            return f;
        }
        /*public PostDialogFragment(Activity act){
            context = act;
        }*/

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_create_post, container, false);
            CircularImage profileImage = (CircularImage)v.findViewById(R.id.profileImageView);
            imageCamera = (ImageButton)v.findViewById(R.id.imgCamera);
            imageGallery = (ImageButton)v.findViewById(R.id.imgGallery);
            btnSave = (Button)v.findViewById(R.id.btnSaveDialog);
            btnCancel = (Button)v.findViewById(R.id.btnCancelDialog);
            dialogImageContainer = (ImageView)v.findViewById(R.id.dialogImageContainer);
            editStatusDialog = (EditText)v.findViewById(R.id.editStatus);
            audianceGroup = (RadioGroup)v.findViewById(R.id.audianceGroup);
            RadioButton radioPublic = (RadioButton)v.findViewById(R.id.radioPublic);
            radioPublic.setChecked(true);
            audiance = 1;
            audianceGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if(checkedId == R.id.radioPublic) {
                        audiance = 1;
                    } else if(checkedId == R.id.radioPrivate) {
                        audiance = 2;
                    } else if(checkedId == R.id.radioFriends)  {
                        audiance = 3;
                    }
                }
            });
            loadUserIcon(ParseUser.getCurrentUser().getString("profileImage"), profileImage, getActivity());
            imageCamera.setOnClickListener(this);
            imageGallery.setOnClickListener(this);
            btnSave.setOnClickListener(this);
            btnCancel.setOnClickListener(this);

            return v;
        }
        private void loadUserIcon(String url,CircularImage imageView, Context context) {
            try{
                ImageLoader imageLoader = ImageLoader.getInstance(context);
                //Ion.with(context).load(url).intoImageView(imageView);
                imageLoader.DisplayImage(url,imageView);
            }catch (IllegalArgumentException e){
                e.printStackTrace();
            }

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
                            showImageinContainer(filePath);
                            //setupPost(filePath);
                        } catch (Exception e) {
                            // TODO: handle exception
                            e.printStackTrace();
                        }


                    } else {
                        Utils.showToastMessage(getActivity(), "Unable to get Image Path.");
                        //Toast.makeText(getActivity(), "Unable to get image path", Toast.LENGTH_SHORT).show();
                        imagePath = "";
                    }
                }
            });

        }

        void showImageinContainer(String path){
            try{
                File imgFile = new  File(path);
                if(imgFile.exists()){
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    dialogImageContainer.setImageBitmap(myBitmap);
                    //Log.e(TAG,convertImageToBase64String(imgFile));
                   // removeSpaces();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
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
        @Override
        public void onStart() {
            filePath = "";
            Display display =((WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            int DisplayWidth ;
            DisplayWidth = display.getWidth();
            Window window = getDialog().getWindow();
            window.setLayout(DisplayWidth, ActionBar.LayoutParams.MATCH_PARENT);
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            super.onStart();
        }


        @Override
        public void onClick(View v) {

            if (v == imageCamera){
                try {
                    takePicture();
                } catch (ChooserException e) {
                    e.printStackTrace();
                }
            }else if (v == imageGallery){
                try {
                    chooseImage();
                } catch (ChooserException e) {
                    e.printStackTrace();
                }
            }else if (v == btnCancel){
                f.dismiss();
            }else if (v == btnSave){
                if (isValidate()){
                    sendMessage(filePath,editStatusDialog.getText().toString());
                }
            }
        }

        boolean isValidate(){
            if (editStatusDialog.getText().toString().trim().isEmpty() || editStatusDialog.getText().toString().trim().length() == 0){
                Utils.showToastMessage(getActivity(),getString(R.string.error_status_message));
                return false;
            }else {
                return true;
            }
        }


        private void sendMessage( String url,String status) {
            Log.d(TAG, "Broadcasting Publish Message");
            Intent intent = new Intent(ApplicationConstant.CREATE_POST_DIALOG_FRAGMENT);
            intent.putExtra(ApplicationConstant.IMAGE_URL, url);
            intent.putExtra(ApplicationConstant.FLAG, status);
            intent.putExtra(ApplicationConstant.FLAG1,audiance);
            LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
            f.dismiss();
        }

    }


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String url = intent.getStringExtra(ApplicationConstant.IMAGE_URL);
            Log.d(TAG, "Got message: " + url);
            showDetailPostDialog(url);

        }
    };
    private BroadcastReceiver mCreatePostMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String url = intent.getStringExtra(ApplicationConstant.IMAGE_URL);
            String status = intent.getStringExtra(ApplicationConstant.FLAG);
            int audiance = intent.getIntExtra(ApplicationConstant.FLAG1,0);
            Log.d(TAG, "Got message: " + url);
            //showDetailPostDialog(url);

            setupPost(status,url,audiance);

        }
    };


    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mCreatePostMessageReceiver);
       // dismissDetailDialog();
        super.onDestroy();
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mCreatePostMessageReceiver);
        super.onPause();
    }

    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,
                new IntentFilter(ApplicationConstant.TIMELINE_ADAPTER));
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mCreatePostMessageReceiver,
                new IntentFilter(ApplicationConstant.CREATE_POST_DIALOG_FRAGMENT));

        super.onResume();
    }

    public static class DetailPostDialog extends DialogFragment {

        private ImageView imageView;
        private String url = null;

        //private Context context;
        static DetailPostDialog newInstance() {
            DetailPostDialog f = new DetailPostDialog();
            f.setStyle( DialogFragment.STYLE_NORMAL, R.style.You_Dialog);
            return f;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View v = inflater.inflate(R.layout.fragment_detail_post, container, false);
            Bundle bundle = this.getArguments();
             url = bundle.getString(ApplicationConstant.IMAGE_URL);
            imageView = (ImageView)v.findViewById(R.id.imgPostDialog);
            return v;
        }
        public void loadImage( String url,ImageView imageView, Context context) {
            ImageLoader loader = ImageLoader.getInstance(context);
            loader.DisplayImage(url, imageView);
        }

        @Override
        public void onStart() {
            Display display =((WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            int DisplayWidth ;
            DisplayWidth = display.getWidth();
            Window window = getDialog().getWindow();
            window.setLayout(DisplayWidth, ActionBar.LayoutParams.MATCH_PARENT);
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            super.onStart();
        }

        @Override
        public void onResume() {
            loadImage(url,imageView,getActivity());
            super.onResume();
        }
    }

    void showDetailPostDialog( String url) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        DialogFragment newFragment = DetailPostDialog.newInstance();
        Bundle bundle = new Bundle();
        bundle.putString(ApplicationConstant.IMAGE_URL, url);
        newFragment.setArguments(bundle);
        newFragment.show(ft, "detailDialog");
    }


}
