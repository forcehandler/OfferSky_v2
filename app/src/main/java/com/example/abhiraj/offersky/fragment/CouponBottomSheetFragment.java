package com.example.abhiraj.offersky.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abhiraj.offersky.R;
import com.example.abhiraj.offersky.model.Coupon;
import com.squareup.picasso.Picasso;

/**
 * Created by Abhiraj on 22-07-2017.
 */

public class CouponBottomSheetFragment extends BottomSheetDialogFragment{

    private static final String TAG = CouponBottomSheetFragment.class.getSimpleName();

    private Coupon mCoupon;
    private TextView brand_tv, description_tv, tnc_tv, expiration_tv, code_tv;
    private ImageView coupon_iv;
    public static CouponBottomSheetFragment newInstance(){
        Log.d(TAG, "newInstance()");
        CouponBottomSheetFragment f = new CouponBottomSheetFragment();
        return f;
    }

    public void setCoupon(Coupon coupon){
        mCoupon = coupon;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.coupon_detail_bottom_sheet, container, false);
        coupon_iv = (ImageView) view.findViewById(R.id.coupon_iv);
        brand_tv = (TextView) view.findViewById(R.id.brand_tv);
        description_tv = (TextView) view.findViewById(R.id.description_tv);
        tnc_tv = (TextView) view.findViewById(R.id.terms_tv);
        expiration_tv = (TextView) view.findViewById(R.id.expiration_tv);
        code_tv = (TextView) view.findViewById(R.id.coupon_code_tv);

        if(mCoupon != null){
            Picasso.with(view.getContext()).load(mCoupon.getCouponImageURL()).into(coupon_iv);
            brand_tv.setText(mCoupon.getBrand());
            description_tv.setText(mCoupon.getDescription());
            tnc_tv.setText(mCoupon.getTnc());
            code_tv.setText(mCoupon.getCode());
        }
        return view;
    }
}
