package com.example.abhiraj.offersky.model;

import java.util.HashMap;

/**
 * Created by Abhiraj on 17-04-2017.
 */

public class Mall {

    private String mallId;
    private String name;
    private HashMap<String, Shop> shops;
    private double latitude;
    private double longitude;
    private float radius;
    private String address;
    private String phone;
    private String email;
    private Long m1, m2, m3, m4, m5;


    public Mall() {}

    public String getMallId() {
        return mallId;
    }

    public void setMallId(String mallId) {
        this.mallId = mallId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Shop> getShops() {
        return shops;
    }

    public void setShops(HashMap<String, Shop> shops) {
        this.shops = shops;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getM1() {
        return m1;
    }

    public void setM1(Long m1) {
        this.m1 = m1;
    }

    public Long getM2() {
        return m2;
    }

    public void setM2(Long m2) {
        this.m2 = m2;
    }

    public Long getM3() {
        return m3;
    }

    public void setM3(Long m3) {
        this.m3 = m3;
    }

    public Long getM4() {
        return m4;
    }

    public void setM4(Long m4) {
        this.m4 = m4;
    }

    public Long getM5() {
        return m5;
    }

    public void setM5(Long m5) {
        this.m5 = m5;
    }


    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }

        if(obj instanceof Mall) {
            if (this.getMallId().equals(((Mall) obj).getMallId())){
                return true;
            }
        }
        return false;
    }
}