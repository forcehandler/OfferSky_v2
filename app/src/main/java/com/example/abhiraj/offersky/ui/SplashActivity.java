package com.example.abhiraj.offersky.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.abhiraj.offersky.signup.SignupActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(this, MallSelectActivity.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);
        }
        finish();
    }
}
