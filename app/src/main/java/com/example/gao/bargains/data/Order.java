package com.example.gao.bargains.data;

/**
 * Created by gao on 2017/3/27.
 */

public class Order {
    int order_id;
    String shop_uid;
    int user_id;
    String shop_name;
    String shop_price;
    int comment_state = 0;
    String order_time;

    public Order(int order_id,String shop_uid, int user_id, String shop_name, String shop_price,int comment_state, String order_time){
        this.order_id = order_id;
        this.shop_uid = shop_uid;
        this.user_id = user_id;
        this.shop_name= shop_name;
        this.shop_price = shop_price;
        this.comment_state = comment_state;
        this.order_time = order_time;

    }

    public int getOrderId(){
        return order_id;
    }
    public void setOrderId(int order_id){
        this.order_id = order_id;
    }

    public String getShopUid(){
        return shop_uid;
    }
    public void setShopUid(String shop_uid){
        this.shop_uid = shop_uid;
    }

    public int getUserId(){
        return user_id;
    }
    public void setUserId(int user_id){
        this.user_id = user_id;
    }

    public String getShopName(){
        return shop_name;
    }
    public void setShopName(String shop_name){
        this.shop_name= shop_name;
    }


    public String getShopPrice(){
        return shop_price;
    }
    public void setShopPrice(String shop_price){
        this.shop_price = shop_price;
    }

    public int getCommentState(){
        return comment_state;
    }
    public void setCommentState(int comment_state){
        this.comment_state = comment_state;

    }

    public String getOrderTime(){
        return order_time;
    }
    public void setOrder_time(String order_time){
        this.order_time = order_time;
    }

}
