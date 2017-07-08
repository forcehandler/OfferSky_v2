package com.example.abhiraj.offersky.utils;

import android.content.Context;
import android.util.Log;

import com.example.abhiraj.offersky.DbHandler.CouponDbHandler;
import com.example.abhiraj.offersky.model.Coupon;

import java.util.Calendar;

/**
 * Created by Abhiraj on 18-06-2017.
 */

public class CouponUtils {

    private static final String TAG = CouponUtils.class.getSimpleName();

    // Function to test the validity of the coupon before allotting to the user
    public static boolean isCouponAllotable(Context context, Coupon coupon){

        //TODO: Add expiry date to the coupons
        // We have an integer number denoting number of hours for which the coupon is valid
        // after allotting it to the user
        int validity = coupon.getValidity();        // integer value specifying hours for which coupon is valid
        Log.d(TAG, "validity of coupon " + coupon.getBrand() + " is  = " + validity);
        CouponDbHandler db = new CouponDbHandler(context);

        String allotment_time_str = db.getCouponAllotmentEpochTime(coupon);
        //Log.d(TAG, "allotment time of coupon is "  + allotment_time_str);

        String curr_time_str = Calendar.getInstance().getTimeInMillis() + "";
        //Log.d(TAG, "curr time in millis = " + curr_time_str);

        long allotment_time = Long.parseLong(allotment_time_str);
        long curr_time = Long.parseLong(curr_time_str);
        long diff = curr_time - allotment_time;

        long diff_hour = diff / (60 * 60 * 1000);
        Log.d(TAG, "diffHour = " + diff_hour);

        boolean isAllotable = false;
        if(diff_hour < validity){
            isAllotable = true;
        }
        return isAllotable;
    }

    public static int isCouponRedeemed(Context context, Coupon coupon){

        Log.d(TAG, "isCouponRedeemed(" + coupon.getCouponId() + " )");

        CouponDbHandler db = new CouponDbHandler(context);
        String redeem_status = db.getRedeemStatus(coupon);
        if(redeem_status.equals("0"))
            return 0;
        return 1;
    }

    public static void setCouponAsRedeemed(Context context, Coupon coupon){
        Log.d(TAG, "setCouponAsRedeemed(" + coupon.getCouponId() + " )");

        CouponDbHandler db = new CouponDbHandler(context);
        db.setRedeemStatus(coupon);
    }

    // Function to add coupons given to the user to shared preferences along with the date and
    // time of allotment
}
