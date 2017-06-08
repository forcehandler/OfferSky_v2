package com.example.abhiraj.offersky.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abhiraj.offersky.R;
import com.example.abhiraj.offersky.model.Coupon;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abhiraj on 24-04-2017.
 */

public class CouponViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = EventViewHolder.class.getSimpleName();

    private View mView;

    @BindView(R.id.iv_coupon)
    ImageView coupon_iv;
    @BindView(R.id.tv_coupon)
    TextView coupon_tv;
    @BindView(R.id.btn_get_code)
    Button code_btn;
    public CouponViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mView = itemView;
    }


    // TODO: Add listener for get code button for coupon
    public void bindViews(Coupon coupon){

        Picasso.with(mView.getContext())
                .load(coupon.getCouponImageURL())
                .into(coupon_iv);

        coupon_tv.setText(coupon.getDescription());
    }
}
