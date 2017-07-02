package com.example.abhiraj.offersky.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.abhiraj.offersky.Constants;

/**
 * Created by Abhiraj on 30-06-2017.
 */

public class OfferSkyUtils {

    public static String getCurrentMallId(Context context){
        // get the mall object
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SharedPreferences.USER_PREF_FILE,
                Context.MODE_PRIVATE);
        String mallId = sharedPreferences.getString(Constants.SharedPreferences.MALL_ID, "MH_0253_CCM");
        return mallId;
    }
}
