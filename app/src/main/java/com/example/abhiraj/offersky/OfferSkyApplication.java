package com.example.abhiraj.offersky;

import android.app.Application;
import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Abhiraj on 18-04-2017.
 */

public class OfferSkyApplication extends Application {

    private static final String TAG = OfferSkyApplication.class.getSimpleName();
    @Override
    public void onCreate(){
        Log.v(TAG, "onCreate()");
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        super.onCreate();
    }
}
