package com.example.abhiraj.offersky.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abhiraj.offersky.R;
import com.example.abhiraj.offersky.adapter.viewholder.CouponViewHolder;
import com.example.abhiraj.offersky.model.Coupon;

import java.util.List;

/**
 * Created by Abhiraj on 24-04-2017.
 */

public class CouponAdapter extends RecyclerView.Adapter<CouponViewHolder> {

    private static final String TAG = CouponAdapter.class.getSimpleName();

    private static List<Coupon> mCoupons;

    public CouponAdapter(List<Coupon> coupons){
        mCoupons = coupons;
    }
    @Override
    public CouponViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coupon_card, parent,false);

        return new CouponViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CouponViewHolder holder, int position) {
            holder.bindViews(mCoupons.get(position));
    }

    @Override
    public int getItemCount() {
        return mCoupons.size();
    }
}
