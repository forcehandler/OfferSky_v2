package com.example.abhiraj.offersky.DbHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.abhiraj.offersky.model.Coupon;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Abhiraj on 19-06-2017.
 */

public class CouponDbHandler extends SQLiteOpenHelper {


    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "CouponDb";

    // Coupon table name
    private static final String TABLE_COUPONS = "allottedCoupons";

    // Coupon table column names
    private static final String KEY_ID = "couponId";
    private static final String KEY_MALL_ID = "mallId";
    private static final String KEY_ALLOTMENT_DATE = "allotmentDate";
    private static final String KEY_ALLOTMENT_TIME = "allotmentTime";

    public CouponDbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_COUPONS_TABLE = "CREATE TABLE " + TABLE_COUPONS + "("
                + KEY_ID + " VARCHAR(50) PRIMARY KEY," + KEY_MALL_ID + " VARCHAR(100),"
                + KEY_ALLOTMENT_DATE + " VARCHAR(20)," + KEY_ALLOTMENT_TIME + " VARCHAR(20)" + ")";
        db.execSQL(CREATE_COUPONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUPONS);

        // Create tables again
        onCreate(db);
    }

    // Adding new coupon entry to allotted coupons
    public void addCouponToAllottedList(Coupon coupon, String mallId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, coupon.getCouponId());
        values.put(KEY_MALL_ID, mallId);

        String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        values.put(KEY_ALLOTMENT_DATE, date);

        String time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
        values.put(KEY_ALLOTMENT_TIME, time);

        Log.d("TIME", time + " is the time obtained");
        Log.d("DATE", date + " is the date obtained");

        // Inserting Row
        db.insert(TABLE_COUPONS, null, values);
        db.close(); // Closing database connection
    }

    // Get all Coupons allotted to the user from a mall
    public List<String> getAllAllottedCouponIds(String mallId){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_COUPONS, new String[] { KEY_ID}, KEY_MALL_ID + "=?",
                new String[] { String.valueOf(mallId) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        List<String> allottedCouponIds = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                Log.d("getIDs " , "coupon id received = " + cursor.getString(0));
                allottedCouponIds.add(cursor.getString(0));

            }while(cursor.moveToNext());
        }
        return allottedCouponIds;
    }

    // Getting coupons Count
    public int getCouponsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_COUPONS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        Log.d("getCouponsCount()", cursor.getCount() + " ");
        // return count
        return cursor.getCount();
    }
}
