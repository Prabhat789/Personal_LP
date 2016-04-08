package com.mobisys.aspr.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mobisys.aspr.model.AudioModel;
import com.mobisys.aspr.model.CallTrackModel;
import com.mobisys.aspr.model.ImagesModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prabhat on 02/04/16.
 */
public class AsprDatabase extends SQLiteOpenHelper {
    private static final String TAG = AsprDatabase.class.getSimpleName();
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "aspr";

    /*private static final String TABLE_RECIPE = "TableRecipe";
    private static final String TABLE_CONTACT = "TableContact";
    private static final String TABLE_CALLLOG = "TableCallLog";

    //private static final String TABLE_USER = "TableUser";*/


    private static final String ID = "id";
    /*private static final String PROD_ID = "prod_id";
    private static final String PROD_NAME = "prod_name";
    private static final String PROD_IMG_ONE = "prod_img_one";
    private static final String PROD_IMG_TWO = "prod_img_two";
    private static final String PROD_IMG_THREE = "prod_img_three";
    private static final String PROD_IMG_FOUR = "prod_img_four";
    private static final String PROD_CATEGORY_NAME = "prod_category_name";
    private static final String PROD_CATEGORY_ID = "prod_category_id";
    private static final String PROD_CATEGORY_IMG = "prod_category_img";
    private static final String ADDED_BY = "added_by";
    private static final String ADDED_DATE_TIME = "added_date_time";
    private static final String PROD_DESCRIPTION = "prod_description";
    private static final String PROD_COOKING_METHOD = "prod_cooking_method";
    private static final String PROD_SERVING = "prod_serving";
    private static final String PROD_COOKING_TIME = "prod_cooking_time";
    private static final String USER_ID = "user_id";*/

    //Contact Table Column
    /*private static final String CONTACT_NAME = "contact_name";
    private static final String CONTACT_NUMBER = "contact_number";
    private static final String TIME_CONTACTED = "time_contacted";
    private static final String UPLOAD_FLAG = "upload_flag";*/

    //CallLog Table Column
    /*private static final String CALLER_NAME = "caller_name";
    private static final String CALLER_NUMBER = "caller_number";

    private static final String CALL_DATE_TIME = "call_date_time";
    */


    //CallTracker Table Column
    private static final String NUMBER = "number";
    private static final String CALL_START_DATE_TIME = "call_start_time";
    private static final String CALL_END_DATE_TIME = "call_end_time";
    private static final String CALL_TYPE = "call_type";
    private static final String CALL_DURATION = "call_duration";

    //ImagePath Table Column
    private static final String IMAGE_PATH = "image_path";
    private static final String TABLE_CALLTRACK = "TableCallTrack";
    private static final String TABLE_IMAGES = "TableImages";
    private static final String TABLE_AUDIO = "TableAudio";
    private static final String AUDIO_NAME = "audioName";
    private static final String DATE_TIME = "dateTime";
    private static final String AUDIO_FILE = "audioFile";


    //User table Column
    /*private static final String OBJECT_ID = "object_id";
    private static final String USERNAME = "username";
    private static final String EMAIL = "email";
    private static final String FRIEND = "friend";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String PHONE = "phone";
    private static final String PROFILE_IMAGE = "profile_image";*/

    public AsprDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
       /* String CREATE_RECIPE_TABLE = "CREATE TABLE " + TABLE_RECIPE + "("
                + ID + " INTEGER PRIMARY KEY,"+PROD_ID + " TEXT," +PROD_NAME + " TEXT,"
                + PROD_IMG_ONE + " TEXT," + PROD_IMG_TWO + " TEXT,"
                + PROD_IMG_THREE + " TEXT," + PROD_IMG_FOUR + " TEXT,"
                + PROD_CATEGORY_NAME + " TEXT," + PROD_CATEGORY_ID + " TEXT,"
                + PROD_CATEGORY_IMG + " TEXT," + ADDED_BY + " TEXT,"
                + ADDED_DATE_TIME + " TEXT," + PROD_DESCRIPTION + " TEXT,"
                + PROD_COOKING_METHOD + " TEXT," + PROD_SERVING + " TEXT,"
                + PROD_COOKING_TIME + " TEXT," + USER_ID + " TEXT"
                + ")";
        db.execSQL(CREATE_RECIPE_TABLE);

        String CREATE_CONTACT_TABLE = "CREATE TABLE " + TABLE_CONTACT + "("
                + ID + " INTEGER PRIMARY KEY," + CONTACT_NAME + " TEXT,"
                + CONTACT_NUMBER + " TEXT," + TIME_CONTACTED + " TEXT,"
                + UPLOAD_FLAG + " TEXT" + ")";
        db.execSQL(CREATE_CONTACT_TABLE);

        String CREATE_CALLLOG_TABLE = "CREATE TABLE " + TABLE_CALLLOG + "("
                + ID + " INTEGER PRIMARY KEY," + CALLER_NAME + " TEXT,"
                + CALLER_NUMBER + " TEXT," + CALL_TYPE + " TEXT,"
                + CALL_DATE_TIME + " TEXT," + CALL_DURATION + " TEXT,"
                + UPLOAD_FLAG + " TEXT" + ")";
        db.execSQL(CREATE_CALLLOG_TABLE);

       */

        String CREATE_IMAGES_TABLE = "CREATE TABLE " + TABLE_IMAGES
                + "(" + ID + " INTEGER PRIMARY KEY," + IMAGE_PATH + " TEXT "+ ")";
        db.execSQL(CREATE_IMAGES_TABLE);

        String CREATE_CALLTRACKER_TABLE = "CREATE TABLE " + TABLE_CALLTRACK
                + "(" + ID + " INTEGER PRIMARY KEY," + NUMBER + " TEXT,"
                + CALL_START_DATE_TIME + " TEXT," + CALL_END_DATE_TIME
                + " TEXT," + CALL_TYPE + " TEXT," + CALL_DURATION + " TEXT " + ")";
        db.execSQL(CREATE_CALLTRACKER_TABLE);
        String CREATE_AUDIO_TABLE = "CREATE TABLE " + TABLE_AUDIO + "("
                + ID + " INTEGER PRIMARY KEY," + AUDIO_NAME + " TEXT,"
                + DATE_TIME + " TEXT," + AUDIO_FILE + " blob " + ")";
        db.execSQL(CREATE_AUDIO_TABLE);

        /*String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + ID + " INTEGER PRIMARY KEY," + OBJECT_ID + " TEXT,"
                + USERNAME + " TEXT," + EMAIL + " TEXT,"
                + FRIEND + " TEXT," + LATITUDE + " TEXT,"
                + LONGITUDE + " TEXT," +PHONE + " TEXT," +PROFILE_IMAGE + " TEXT" + ")";
        db.execSQL(CREATE_USER_TABLE);*/

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CALLTRACK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AUDIO);

        /*db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CALLLOG);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);*/

        // Create tables again
        onCreate(db);
    }




    //ADD Images
    public void addImages(ImagesModel imgModel) {
        // TODO Auto-generated method stub
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(IMAGE_PATH, imgModel.getImagePath());
        db.insert(TABLE_IMAGES, null, values);
        db.close();

    }
    public void addAudio(AudioModel imgModel) {
        // TODO Auto-generated method stub
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AUDIO_NAME, imgModel.getAudioName());
        values.put(DATE_TIME, imgModel.getDateTime());
        values.put(AUDIO_FILE, imgModel.getAudio());
        db.insert(TABLE_AUDIO, null, values);
        Log.e(TAG, values.toString());
        db.close();

    }

    public List<ImagesModel> getAllImages() {
        // TODO Auto-generated method stub
        List<ImagesModel> scanRecordList = new ArrayList<ImagesModel>();
        String selectQuery = "SELECT  * FROM " + TABLE_IMAGES;

        SQLiteDatabase adb = this.getWritableDatabase();
        Cursor cursor = adb.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ImagesModel contact = new ImagesModel();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setImagePath(cursor.getString(1));

                // Adding contact to list
                scanRecordList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        cursor.close();
        return scanRecordList;

    }
    /*Get nonUploaded records Images*/
    public List<ImagesModel> getNonUploadedRecordImages() {
        // TODO Auto-generated method stub
        List<ImagesModel> singleContact = new ArrayList<ImagesModel>();
        String selectQuery = "SELECT * FROM " + TABLE_IMAGES+" ORDER BY Id DESC LIMIT 1";
        SQLiteDatabase adb = this.getWritableDatabase();
        Cursor cursor = adb.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                ImagesModel contact = new ImagesModel();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setImagePath(cursor.getString(1));
                // Adding contact to list
                singleContact.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return singleContact;

    }

    public int getImageCount() {
        int count = 0;
        String countQuery = "SELECT  * FROM " + TABLE_IMAGES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        if (cursor != null && !cursor.isClosed()) {
            count = cursor.getCount();
            cursor.close();
        }

        return count;
    }

    /* Delete One Image Row By _ID */
    public void deleteImageRow(int id){
        SQLiteDatabase adb = this.getWritableDatabase();
        adb.delete(TABLE_IMAGES, ID+  "=" + id, null);
        Log.e(TAG, "Row Deleted Id: " + id);
        adb.close();

    }
    /* Delete One CallTrack Row By _ID */
    public void deleteCallTrack(int id){
        SQLiteDatabase adb = this.getWritableDatabase();
        adb.delete(TABLE_CALLTRACK, ID+  "=" + id, null);
        Log.e(TAG, "Row Deleted Id: " + id);
        adb.close();

    }
    /*public void addRecipe(RecipeDatabaseModel recipeModel) {
        // TODO Auto-generated method stub

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PROD_ID, recipeModel.getProd_id());
        values.put(PROD_NAME, recipeModel.getProd_name());
        values.put(PROD_IMG_ONE, recipeModel.getProd_img_one());
        values.put(PROD_IMG_TWO, recipeModel.getProd_img_two());
        values.put(PROD_IMG_THREE, recipeModel.getProd_img_three());
        values.put(PROD_IMG_FOUR, recipeModel.getProd_img_four());
        values.put(PROD_CATEGORY_NAME, recipeModel.getProd_category_name());
        values.put(PROD_CATEGORY_ID, recipeModel.getProd_category_id());
        values.put(PROD_CATEGORY_IMG, recipeModel.getProd_category_img());
        values.put(ADDED_BY, recipeModel.getAdded_by());
        values.put(ADDED_DATE_TIME, recipeModel.getAdded_date_time());
        values.put(PROD_DESCRIPTION, recipeModel.getProd_description());
        values.put(PROD_COOKING_METHOD, recipeModel.getProd_cooking_method());
        values.put(PROD_SERVING, recipeModel.getProd_serving());
        values.put(PROD_COOKING_TIME, recipeModel.getProd_cooking_time());
        values.put(USER_ID, recipeModel.getUser_id());

        db.insert(TABLE_RECIPE, null, values);
        db.close(); // Closing database connection


    }*/
    // ADD CONTACT
    /*public void addContact(ContactModel conModel) {
        // TODO Auto-generated method stub

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CONTACT_NAME, conModel.getConName());
        values.put(CONTACT_NUMBER, conModel.getConNumber());
        values.put(TIME_CONTACTED, conModel.getConTimeContacted());
        values.put(UPLOAD_FLAG, conModel.getConUploadFlag());
        db.insert(TABLE_CONTACT, null, values);
        db.close();
        // Closing database connection


    }*/
    //ADD CALLLOG
   /* public void addCallLog(CallLogModel conModel) {
        // TODO Auto-generated method stub

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CALLER_NAME, conModel.getCallerName());
        values.put(CALLER_NUMBER, conModel.getCallerNumber());
        values.put(CALL_TYPE, conModel.getCallType());
        values.put(CALL_DATE_TIME, conModel.getCallDateTime());
        values.put(CALL_DURATION, conModel.getCallDuration());
        values.put(UPLOAD_FLAG, conModel.getUploadFlag());
        db.insert(TABLE_CALLLOG, null, values);
        db.close();
        // Closing database connection


    }*/

    //ADD CAll Track
    public void addCallTrack(CallTrackModel conModel) {
        // TODO Auto-generated method stub

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NUMBER, conModel.getNumber());
        values.put(CALL_START_DATE_TIME, conModel.getCallStratTime());
        values.put(CALL_END_DATE_TIME, conModel.getCallEndTime());
        values.put(CALL_TYPE, conModel.getCallType());
        values.put(CALL_DURATION, conModel.getCallDuration());
        db.insert(TABLE_CALLTRACK, null, values);
        db.close();
    }
    public List<CallTrackModel> getNonUploadedRecordCallTrack() {
        // TODO Auto-generated method stub
        List<CallTrackModel> singleContact = new ArrayList<CallTrackModel>();
        String selectQuery = "SELECT * FROM " + TABLE_CALLTRACK+" ORDER BY Id DESC LIMIT 1";
        SQLiteDatabase adb = this.getWritableDatabase();
        Cursor cursor = adb.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CallTrackModel contact = new CallTrackModel();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setNumber(cursor.getString(1));
                contact.setCallStratTime(cursor.getString(2));
                contact.setCallEndTime(cursor.getString(3));
                contact.setCallType(cursor.getString(4));
                contact.setCallDuration(cursor.getString(5));
                singleContact.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return singleContact;

    }
    // Add User
    /*public void addUser(UserModel conModel) {
        // TODO Auto-generated method stub

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(OBJECT_ID, conModel.getObjectId());
        values.put(USERNAME, conModel.getUserName());
        values.put(EMAIL, conModel.getEmail());
        values.put(FRIEND, conModel.getFriend());
        values.put(LATITUDE, conModel.getLatitude());
        values.put(LONGITUDE, conModel.getLongitude());
        values.put(PHONE, conModel.getPhone());
        values.put(PROFILE_IMAGE, conModel.getProfileImage());


        db.insert(TABLE_USER, null, values);
        db.close();
        // Closing database connection


    }*/

    /*Getting Image Count*/

    // Getting contacts Count
    /*public int getAlarmCount() {
        int count = 0;
        String countQuery = "SELECT  * FROM " + TABLE_RECIPE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        if (cursor != null && !cursor.isClosed()) {
            count = cursor.getCount();
            cursor.close();
        }

        return count;
    }*/

    /*public List<ContactModel> getNonUploadedRecord() {
        // TODO Auto-generated method stub
        List<ContactModel> singleContact = new ArrayList<ContactModel>();
        //String selectQuery = "SELECT * FROM " + TABLE_CONTACT+" ORDER BY Id DESC LIMIT 1";
        String selectQuery = "SELECT * FROM " + TABLE_CONTACT+" WHERE "+UPLOAD_FLAG+ " LIKE"+"'false'"+" ORDER BY Id DESC LIMIT 1";


        SQLiteDatabase adb = this.getWritableDatabase();
        Cursor cursor = adb.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ContactModel contact = new ContactModel();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setConName(cursor.getString(1));
                contact.setConNumber(cursor.getString(2));
                contact.setConTimeContacted(cursor.getString(3));
                contact.setConUploadFlag(cursor.getString(4));


                // Adding contact to list
                singleContact.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        cursor.close();
        return singleContact;

    }*/
    // GET NONUPLOD CalllOG RECORD
   /* public List<CallLogModel> getNonUploadedRecordCallLog() {
        // TODO Auto-generated method stub
        List<CallLogModel> singleContact = new ArrayList<CallLogModel>();
        //String selectQuery = "SELECT * FROM " + TABLE_CONTACT+" ORDER BY Id DESC LIMIT 1";
        String selectQuery = "SELECT * FROM " + TABLE_CALLLOG+" WHERE "+UPLOAD_FLAG+ " LIKE"+"'false'"+" ORDER BY Id DESC LIMIT 1";


        SQLiteDatabase adb = this.getWritableDatabase();
        Cursor cursor = adb.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CallLogModel contact = new CallLogModel();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setCallerName(cursor.getString(1));
                contact.setCallerNumber(cursor.getString(2));
                contact.setCallType(cursor.getString(3));
                contact.setCallDateTime(cursor.getString(4));
                contact.setCallDuration(cursor.getString(5));
                contact.setUploadFlag(cursor.getString(6));


                // Adding contact to list
                singleContact.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        cursor.close();
        return singleContact;

    }*/




   /* public List<UserModel> getUser() {
        // TODO Auto-generated method stub
        List<UserModel> singleContact = new ArrayList<UserModel>();
        //String selectQuery = "SELECT * FROM " + TABLE_CONTACT+" ORDER BY Id DESC LIMIT 1";
        String selectQuery = "SELECT * FROM " + TABLE_USER+" WHERE "+EMAIL+ " LIKE"+"'prabhat.kumar.ec@gmail.com'"+" ORDER BY Id DESC LIMIT 1";


        SQLiteDatabase adb = this.getWritableDatabase();
        Cursor cursor = adb.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                UserModel contact = new UserModel();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setObjectId(cursor.getString(1));
                contact.setUserName(cursor.getString(2));
                contact.setEmail(cursor.getString(3));
                contact.setFriend(cursor.getString(4));
                contact.setLatitude(cursor.getString(5));
                contact.setLongitude(cursor.getString(6));
                contact.setPhone(cursor.getString(7));
                contact.setProfileImage(cursor.getString(8));

                // Adding contact to list
                singleContact.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        cursor.close();
        return singleContact;

    }*/
    //Update Contact Upload flag
    /*public int updateUploadFlagContact(ContactModel contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UPLOAD_FLAG, contact.getConUploadFlag());
        Log.v("Updating", "" + contact.getConName());

        return db.update(TABLE_CONTACT, values, CONTACT_NAME + " = ?",
                new String[] { String.valueOf(contact.getConName()) });
    }*/

    //Update Calllog Upload flag
    /*public int updateUploadFlagCalllog(CallLogModel contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UPLOAD_FLAG, contact.getUploadFlag());

        return db.update(TABLE_CALLLOG, values, CALL_DATE_TIME + " = ?",
                new String[] { String.valueOf(contact.getCallDateTime()) });
    }*/

    //Update CallTrack Upload flag
    /*public int updateUploadFlagCallTrack(CallTrackModel contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UPLOAD_FLAG, contact.getUploadFlag());

        return db.update(TABLE_CALLTRACK, values, CALL_START_DATE_TIME + " = ?",
                new String[] { String.valueOf(contact.getCallStartTime()) });
    }*/

    /*public int updateUploadFlagImages(ImagesModel contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UPLOAD_FLAG, contact.getUploadFlag());
        Log.v("Updating", "" + contact.getImagePath());

        return db.update(TABLE_IMAGES, values, IMAGE_PATH + " = ?",
                new String[] { String.valueOf(contact.getImagePath()) });

    }*/



    /*public List<ContactModel> getAllRecords() {
        List<ContactModel> scanRecordList = new ArrayList<ContactModel>();
        // Select All Query
        System.out.println("Loading DB...");
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACT;

        SQLiteDatabase adb = this.getWritableDatabase();
        Cursor cursor = adb.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ContactModel contact = new ContactModel();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setConName(cursor.getString(1));
                contact.setConNumber(cursor.getString(2));
                contact.setConTimeContacted(cursor.getString(3));
                contact.setConUploadFlag(cursor.getString(4));


                // Adding contact to list
                scanRecordList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        cursor.close();
        return scanRecordList;
    }*/


    /*public int getContactCount() {
        int count = 0;
        String countQuery = "SELECT  * FROM " + TABLE_CONTACT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        if (cursor != null && !cursor.isClosed()) {
            count = cursor.getCount();
            cursor.close();
        }
        cursor.close();

        // return count
        return count;
    }*/

}