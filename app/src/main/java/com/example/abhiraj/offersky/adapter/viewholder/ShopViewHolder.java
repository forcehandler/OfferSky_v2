package com.example.abhiraj.offersky.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.abhiraj.offersky.R;
import com.example.abhiraj.offersky.adapter.ShopAdapter;
import com.example.abhiraj.offersky.model.Shop;
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abhiraj on 14-04-2017.
 */

public class ShopViewHolder extends SortedListAdapter.ViewHolder<Shop> implements View.OnClickListener {

    private View mView;
    private ShopAdapter.ShopClickListener mShopClickListener;

    @BindView(R.id.shop_name)
    TextView shop_name_tv;
    @BindView(R.id.brand_image)
    ImageView brand_image_iv;
    public ShopViewHolder(View view, ShopAdapter.ShopClickListener shopClickListener) {
        super(view);
        ButterKnife.bind(this, view);
        mView = view;
        mShopClickListener = shopClickListener;
        mView.setOnClickListener(this);
    }

    @Override
    protected void performBind(Shop shop) {
        shop_name_tv.setText(shop.getName());
        Glide.with(mView.getContext())
                .load(shop.getBrandImageURL())
                .placeholder(R.drawable.ic_menu_cart)
                .crossFade()
                .into(brand_image_iv);
    }

    @Override
    public void onClick(View view) {
        mShopClickListener.onShopClick(getAdapterPosition());
    }
}
