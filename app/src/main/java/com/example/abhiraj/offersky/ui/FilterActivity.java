package com.example.abhiraj.offersky.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.abhiraj.offersky.R;

public class FilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        // Ensure that the theme for the activity provides action bar
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            String title = getIntent().getStringExtra("Title");
            setTitle(title);
        }
    }
}
