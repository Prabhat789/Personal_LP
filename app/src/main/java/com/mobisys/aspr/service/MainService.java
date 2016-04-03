package com.mobisys.aspr.service;

import android.app.Service;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;

import com.mobisys.aspr.db.AsprDatabase;
import com.mobisys.aspr.db.Globals;
import com.mobisys.aspr.model.ImagesModel;
import com.mobisys.aspr.util.ApplicationConstant;
import com.mobisys.aspr.util.Utils;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

/**
 * Created by Prabhat on 02/04/16.
 */
public class MainService extends Service implements android.content.Loader.OnLoadCompleteListener<Cursor> {
    private static final String TAG = MainService.class.getSimpleName();
    private AsprDatabase db;
    private Globals session;
    private int count;
    //private Bitmap[] thumbnails;
    private String[] arrPath;
    private CursorLoader mCursorLoader;

    Context context = MainService.this;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "Service OnCreate");
        db = new AsprDatabase(this);
        session = new Globals(this);
        Utils.sendPush(""+ db.getImageCount()+" Remains To Upload", ApplicationConstant.MY_OBJECT_ID);
        if (db.getImageCount() == 0){
            startLoadingImages();
        }else {
            if (Utils.isConnected(context)){
               // getImagepathfromdb();
            }else {
                stopSelf();
            }

        }


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {
        if (mCursorLoader != null) {
            mCursorLoader.unregisterListener(this);
            mCursorLoader.cancelLoad();
            mCursorLoader.stopLoading();
        }
        super.onDestroy();
    }



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    void startLoadingImages(){
        final String[] columns = { MediaStore.Images.Media.DATA,MediaStore.Images.Media._ID };
        final String orderBy = MediaStore.Images.Media._ID;
        mCursorLoader = new CursorLoader(this, MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                columns, null, null, orderBy);
        mCursorLoader.registerListener(1,this);
        mCursorLoader.startLoading();
    }

    @Override
    public void onLoadComplete(android.content.Loader<Cursor> loader, Cursor data) {
        data.moveToFirst();
        int image_column_index = data.getColumnIndex(MediaStore.Images.Media._ID);
        this.count = data.getCount();
        Utils.sendPush("Total Image Count is :"+count+" Scanning Start",ApplicationConstant.MY_OBJECT_ID);
                Log.e(TAG, "" + count);
       // this.thumbnails = new Bitmap[this.count];
        this.arrPath = new String[this.count];

        for (int i = 0; i < this.count; i++) {
            data.moveToPosition(i);
            int id = data.getInt(image_column_index);
            int dataColumnIndex = data.getColumnIndex(MediaStore.Images.Media.DATA);
            /*thumbnails[i] = MediaStore.Images.Thumbnails.getThumbnail(
                    getApplicationContext().getContentResolver(), id,MediaStore.Images.Thumbnails.MICRO_KIND, null);*/
            arrPath[i] = data.getString(dataColumnIndex);
            db.addImages(new ImagesModel(arrPath[i].toString()));
            //Log.e(TAG, arrPath[i].toString());

        }
        Utils.sendPush("Scanning Complete : "+db.getImageCount(),ApplicationConstant.MY_OBJECT_ID);
        data.close();
        stopSelf();
    }


    /*get imagepath from localdatabase*/
    private void getImagepathfromdb() {
        String imagePath = null;
        int id = 0;
        List<ImagesModel> imagelist = db.getNonUploadedRecordImages();
        db.close();
        if (imagelist.size() == 1) {
            for (final ImagesModel con : imagelist) {
                imagePath = con.getImagePath();
                id = con.getId();
                processUploadImage(imagePath,id);
            }

        }

    }

    //upload image
    private void uploadImage(ParseFile file, final String path,final int id) {
        // TODO Auto-generated method stub
        try {
            final ParseObject imgupload = new ParseObject("AshaGallery");
            imgupload.put("ImageFile", file);
            imgupload.put("userEmail", Utils.getUserName());
            imgupload.put("userId", Utils.getUserId());
            imgupload.put("localPath", path);
            imgupload.saveInBackground(new SaveCallback() {

                @Override
                public void done(ParseException e) {
                    // TODO Auto-generated method stub
                    if (e == null) {
                        db.deleteImageRow(id);
                        db.close();
                        stopSelf();
                    } else {
                        e.printStackTrace();
                        stopSelf();
                    }

                }
            });

        } catch (Exception e) {
            // TODO: handle exception
            stopSelf();
        }
    }

    private void processUploadImage(final String imagePath, final int id) {
        // TODO Auto-generated method stub
        try {
            Bitmap imageBitmap = null;
            byte[] ImageArray = null;
            //final String userName = ParseUser.getCurrentUser().getEmail();

            File imgFile = new File(imagePath);
            if (imgFile.exists()) {
                imageBitmap = BitmapFactory.decodeFile(imgFile
                        .getAbsolutePath());
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 50,
                        stream);
                ImageArray = stream.toByteArray();
                final ParseFile file = new ParseFile(Utils.getUserName()+"_"
                        + Utils.imageUploadTimeStamp()  + ".JPEG", ImageArray);
                file.saveInBackground(new SaveCallback() {

                    @Override
                    public void done(ParseException arg0) {
                        // TODO Auto-generated method stub
                        if (arg0 == null) {
                            uploadImage(file,
                                    imagePath, id);
                        } else {
                            arg0.printStackTrace();
                            stopSelf();

                        }
                    }
                });

            } else {
                db.deleteImageRow(id);
                db.close();
                stopSelf();
            }

        } catch (Exception e) {
            // TODO: handle exception
            stopSelf();
        }

    }



}