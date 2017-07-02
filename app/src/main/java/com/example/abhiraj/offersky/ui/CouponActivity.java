package com.example.abhiraj.offersky.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.abhiraj.offersky.DbHandler.CouponDbHandler;
import com.example.abhiraj.offersky.R;
import com.example.abhiraj.offersky.adapter.CouponAdapter;
import com.example.abhiraj.offersky.model.Coupon;
import com.example.abhiraj.offersky.model.Mall;
import com.example.abhiraj.offersky.utils.CouponUtils;
import com.example.abhiraj.offersky.utils.FirebaseUtils;
import com.example.abhiraj.offersky.utils.OfferSkyUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CouponActivity extends AppCompatActivity {

    private static final String TAG = CouponActivity.class.getSimpleName();

    private CouponAdapter mCouponAdapter;
    private List<Coupon> mModels;
    private List<String> allottedCouponIds;
    private CouponDbHandler db;

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
            db = new CouponDbHandler(this);
            setupRecyclerView();
        }
    }

    private void setupRecyclerView() {

        Log.d(TAG, "insetupRecyclerView()");
        if(coupon_rv != null){
            Log.d(TAG, "setupRecyclerView()");
            mModels = new ArrayList<>();

            // get all the coupons from the local coupon database
            String mallId = OfferSkyUtils.getCurrentMallId(this);
            allottedCouponIds = db.getAllAllottedCouponIds(mallId);

            if(allottedCouponIds.size() == 0)
            {
                Log.d(TAG, "No coupons have been allotted yet");
                coupon_rv.setVisibility(View.GONE);
                empty_msg_tv.setVisibility(View.VISIBLE);
            }

            else{
                // grab hold of the mall object and get all the coupons which have been allotted
                // and store them in mModels
                try{
                    Mall mall = FirebaseUtils.sMall;
                    Map<String, Coupon> couponMap = mall.getCoupons();
                    for(String allottedCouponId : allottedCouponIds){
                        Coupon coupon = couponMap.get(allottedCouponId);
                        if(coupon != null){
                            // check if the coupon is still valid
                            if(CouponUtils.isCouponAllotable(this, coupon)){
                                mModels.add(coupon);
                            }
                            else{
                                db.deleteCouponFromAllottedList(coupon, mallId);
                            }
                        }
                    }

                } catch (Exception e){
                    Log.e(TAG, e.toString());
                    Log.e(TAG, "error in obtaining mall maybe?");
                }
                Log.d(TAG, "no of coupons = " + mModels.size());
                mCouponAdapter = new CouponAdapter(mModels);
                coupon_rv.setLayoutManager(new LinearLayoutManager(this));
                coupon_rv.setAdapter(mCouponAdapter);
            }

        }
    }

}
