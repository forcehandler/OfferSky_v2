package com.example.abhiraj.offersky.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.abhiraj.offersky.R;
import com.example.abhiraj.offersky.adapter.ChipAdapter;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopDetailsActivity extends AppCompatActivity {

    @BindView(R.id.rv_chip)
    RecyclerView chip_rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // TODO: Properly Implement setting Display Title and add ImageView of the Shop/Brand
        // for the collapsible toolbar
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle("Adidas");
        }

        setupTestChipRv();
    }

    private void setupTestChipRv() {

        String[] categoriesarr = {"Formals", "Ethnic", "Jeans", "Trousers"};
        ArrayList<String> categories= new ArrayList<>(Arrays.asList(categoriesarr));
        chip_rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        ChipAdapter adapter = new ChipAdapter(categories);
        chip_rv.setAdapter(adapter);
    }
}
