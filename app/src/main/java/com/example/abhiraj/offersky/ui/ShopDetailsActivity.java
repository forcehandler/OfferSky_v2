package com.example.abhiraj.offersky.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abhiraj.offersky.Constants;
import com.example.abhiraj.offersky.R;
import com.example.abhiraj.offersky.adapter.ChipAdapter;
import com.example.abhiraj.offersky.adapter.OfferAdapter;
import com.example.abhiraj.offersky.model.Offer;
import com.example.abhiraj.offersky.model.Shop;
import com.example.abhiraj.offersky.utils.FirebaseUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopDetailsActivity extends AppCompatActivity {

    private static final String TAG = ShopDetailsActivity.class.getSimpleName();

    private Shop shop;

    @BindView(R.id.rv_chip)
    RecyclerView chip_rv;
    @BindView(R.id.rv_offer_image1)
    RecyclerView offer_image_rv;
    @BindView(R.id.iv_backdrop)ImageView backdrop_iv;
    @BindView(R.id.tv_offer_description)
    TextView offer_description_tv;
    @BindView(R.id.tv_location) TextView location_tv;
    @BindView(R.id.tv_shop_description) TextView shop_description_tv;
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

        // for the collapsible toolbar
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle("Adidas");
        }

        prepareUI();

    }

    private void prepareUI(){
        String shopId = getIntent().getStringExtra(Constants.IntentKeys.SHOP_ID);

        // Get the clicked shop from the shop id and set the title, backdrop image
        // category chips and the offers.
        try {
           shop = FirebaseUtils.sMall.getShops().get(shopId);
            Log.d(TAG, "shopId = " + shopId);

            // Set title
            if(getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                setTitle(shop.getName());
            }

            // Set backdrop
            String brandImageURL = shop.getBrandImageURL();
            Picasso.with(this)
                    .load(brandImageURL)
                    .into(backdrop_iv);

            // Set offers
            setupOfferRecyclerUI();

            // Set category chips
            setupTestChipRv();

            // Set location, phone and email
            location_tv.setText(shop.getLocation());

            // Set description
            shop_description_tv.setText(shop.getGender());
        }
        catch (Exception e){
            Log.e(TAG, e.toString());
        }

    }

    private void setupOfferRecyclerUI() {

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        offer_image_rv.setLayoutManager(linearLayoutManager);

        List<String> offer_image_urls = new ArrayList<>();
        final List<Offer> offers = new ArrayList<>();
        offers.addAll(shop.getOffers().values());

        for(Offer offer : offers){
            offer_image_urls.add(offer.getOfferImageURL());
            Log.d(TAG, "offer image urls = " + offer.getOfferImageURL());
        }

        OfferAdapter adapter = new OfferAdapter(offer_image_urls);
        offer_image_rv.setAdapter(adapter);

        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(offer_image_rv);

        // set text for the first offer since the text changes only on scroll
        offer_description_tv.setText(offers.get(0).getDescription());
        offer_image_rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                Log.d(TAG, " onScroll state changes new state = " + newState);
                if(newState == 0) {
                    int position = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                    Log.d(TAG, "first Completely Visible Position = " + position);

                    // Set the offer text
                    // sometimes during transition position returned is -1
                    // so test for position
                    if(position >= 0) {
                        offer_description_tv.setText(offers.get(position).getDescription());
                    }

                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    private void setupTestChipRv() {

        ArrayList<String> categories= new ArrayList<>();
        categories.addAll(shop.getCategories().values());
        chip_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ChipAdapter adapter = new ChipAdapter(categories);
        chip_rv.setAdapter(adapter);
    }
}
