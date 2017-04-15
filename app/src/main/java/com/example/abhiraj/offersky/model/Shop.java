package com.example.abhiraj.offersky.model;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

/**
 * Created by Abhiraj on 14-04-2017.
 */

public class Shop implements SortedListAdapter.ViewModel{

    private final static String TAG = Shop.class.getSimpleName();

    private String shopId;
    private String name;
    private String address;
    private String brandImageURL;

    public Shop() {
    }

    public Shop(String shopId, String address, String name, String brandImageURL) {
        this.shopId = shopId;
        this.address = address;
        this.name = name;
        this.brandImageURL = brandImageURL;
    }


    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrandImageURL() {
        return brandImageURL;
    }

    public void setBrandImageURL(String brandImageURL) {
        this.brandImageURL = brandImageURL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shop model = (Shop) o;

        if (shopId != model.getShopId()) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = shopId.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

}
