package com.example.gao.bargains.data;

import com.baidu.mapapi.model.LatLng;

/**
 * Created by gao on 2017/3/6.
 */

public class Shop {
    private String uid;
    private String shop_name;
    private int imageId;
    private String distance;
    private String address;
    private String price;
    private String commentNum;
    private String phoneNum;
    private LatLng latLng;

    public LatLng getLatLng() {
        return latLng;
    }

    public Shop(String uid){
        this.uid = uid;
    }

    public Shop(String uid,String shop_name, int imageId, String distance, String address, String price, String commentNum, String phoneNum, LatLng latLng){
        this.uid = uid;
        this.shop_name = shop_name;
        this.imageId = imageId;
        this.distance = distance;
        this.address = address;
        this.price = price;
        this.commentNum = commentNum;
        this.phoneNum = phoneNum;
        this.latLng = latLng;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
