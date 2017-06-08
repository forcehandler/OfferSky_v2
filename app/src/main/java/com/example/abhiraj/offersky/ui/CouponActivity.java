package com.example.abhiraj.offersky.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.abhiraj.offersky.R;
import com.example.abhiraj.offersky.adapter.EventAdapter;
import com.example.abhiraj.offersky.model.Event;
import com.example.abhiraj.offersky.utils.FirebaseUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CouponActivity extends AppCompatActivity {

    private static final String TAG = CouponActivity.class.getSimpleName();

    private EventAdapter mCouponAdapter;
    private List<Event> mModels;

    @BindView(R.id.rv_coupon)RecyclerView coupon_rv;
    @BindView(R.id.empty_view)TextView empty_msg_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);

        ButterKnife.bind(this);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle("Coupons");

            setupRecyclerView();
        }
    }

    private void setupRecyclerView() {

        Log.d(TAG, "insetupRecyclerView()");
        if(coupon_rv != null){
            Log.d(TAG, "setupRecyclerView()");

            mModels = new ArrayList<>();

            try {
                mModels.addAll(FirebaseUtils.sMall.getEvents().values());
                Log.d(TAG, "mall id when fetching events = " + FirebaseUtils.sMall.getMallId());
            }catch (Exception e){
                Log.e(TAG, e.toString());
            }

            if(mModels.size() == 0)
            {
                coupon_rv.setVisibility(View.GONE);
                empty_msg_tv.setVisibility(View.VISIBLE);
            }

            else{
                Log.d(TAG, "no of events = " + mModels.size());
                mCouponAdapter = new EventAdapter(mModels);
                coupon_rv.setLayoutManager(new LinearLayoutManager(this));
                coupon_rv.setAdapter(mCouponAdapter);
            }

        }
    }
}
