package com.example.abhiraj.offersky.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.abhiraj.offersky.Constants;
import com.example.abhiraj.offersky.model.Mall;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Abhiraj on 17-04-2017.
 */

public class FirebaseUtils {

    private static final String TAG = FirebaseUtils.class.getSimpleName();

    public static Mall sMall;

    private static String FIREBASE_MALL_TAG = "malls";

    public static void getMall(final Context context, String mall_id){

        Log.d(TAG, mall_id);
        String uid;

        // TODO: Add firebase user access;

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference(FIREBASE_MALL_TAG
         + "/" + mall_id);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onMallDataChange()");

                Log.d(TAG, dataSnapshot.getKey());
                Log.d(TAG, dataSnapshot.getChildrenCount()+ "");

                try {
                    sMall = dataSnapshot.getValue(Mall.class);
                }
                catch (Exception e){
                    Log.e(TAG, e.toString());
                }
                if(sMall != null) {
                    sendDataReadyBroadcast(context);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private static void sendDataReadyBroadcast(Context context) {

        Intent intent = new Intent();
        intent.setAction(Constants.Broadcast.MALL_DATA_READY);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
