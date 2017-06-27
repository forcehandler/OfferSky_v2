package com.example.abhiraj.offersky.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.abhiraj.offersky.Constants;
import com.example.abhiraj.offersky.model.Mall;
import com.example.abhiraj.offersky.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.DateTime;

/**
 * Created by Abhiraj on 17-04-2017.
 */

public class FirebaseUtils {

    private static final String TAG = FirebaseUtils.class.getSimpleName();

    public static Mall sMall;
    public static long visitor_number = -2;

    private static String FIREBASE_MALL_TAG = "malls";
    private static String FIREBASE_VISITOR_TAG = "visitors";
    private static String FIREBASE_USER_TAG = "users";
    private static String FIREBASE_VISITOR_NO_TAG = "vno";

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
                    sendDataReadyBroadcast(context, 0);     // 0 for mall data ready
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public static void addUser(User user){

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null){
            Log.d(TAG, firebaseUser.getUid() + " firebase user id");
            mRef.child(FIREBASE_USER_TAG).child(firebaseUser.getUid()).setValue(user);
        }

    }

    public static void getVisitorNumber(final Context context, String mall_id){

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference(FIREBASE_VISITOR_TAG
                + "/" + mall_id + "/" + FIREBASE_VISITOR_NO_TAG);
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "visitor data change encountered");
                //dataSnapshot.getValue();
                try {
                    visitor_number = (long) dataSnapshot.getValue();
                    Log.d(TAG, "visitor no obtained = " + visitor_number);
                }
                catch (Exception e){
                    Log.d(TAG, e.toString());
                }
                // TODO: Seems like a redundant check, test it and remove if unnecessary

                if(visitor_number != -2){
                    sendDataReadyBroadcast(context, 1);             // 1 for visitor number ready
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private static void udpateUserEntryRecord(Context context, String mall_id){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            DatabaseReference mRef = FirebaseDatabase.getInstance().getReference(FIREBASE_USER_TAG);
            mRef.child(user.getUid()).push().child(mall_id).child("in_time").setValue(DateTime.now());
        }
    }

    private static void sendDataReadyBroadcast(Context context, int type) {

        Intent intent = new Intent();
        switch (type){
            case 0:
                Log.d(TAG, "sending mall data ready broadcast");
                intent.setAction(Constants.Broadcast.MALL_DATA_READY);
                break;
            case 1:
                Log.d(TAG, "sending visitor data ready broadcast");
                intent.setAction(Constants.Broadcast.VISITOR_DATA_READY);
                break;
        }
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
