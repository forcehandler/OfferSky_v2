package com.example.abhiraj.offersky;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import static com.example.abhiraj.offersky.utils.LogUtils.*;

/**
 * Created by Abhiraj on 14-04-2017.
 */

public abstract class BaseActivity extends AppCompatActivity{

    private static final String TAG = BaseActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
    }

}
