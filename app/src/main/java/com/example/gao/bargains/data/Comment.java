package com.example.gao.bargains.data;

/**
 * Created by gao on 2017/3/30.
 */

public class Comment {

    String shop_uid;
    int user_id;
    int order_id;
    String user_name;
    String comment_content;
    String comment_grade;
    String comment_time;

    public Comment(String shop_uid, int user_id, int order_id, String user_name, String comment_content, String comment_grade, String comment_time){
        this.shop_uid = shop_uid;
        this.user_id = user_id;
        this.order_id = order_id;
        this.user_name= user_name;
        this.comment_content = comment_content;
        this.comment_grade = comment_grade;

        this.comment_time = comment_time;

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

    public int getOrderId(){
        return order_id;
    }
    public void setOrderId(int order_id){
        this.order_id = order_id;
    }

    public String getUserName(){
        return user_name;
    }
    public void setUserName(String user_name){
        this.user_name= user_name;
    }

    public String getCommentContent(){
        return comment_content;
    }
    public void setCommentContent(String comment_content){
        this.comment_content= comment_content;
    }

    public String getCommentGrade(){
        return comment_grade;
    }
    public void setCommentGrade(String comment_grade){
        this.comment_grade= comment_grade;
    }

    public String getCommentTime(){
        return comment_time;
    }
    public void setComment_time(String comment_time){
        this.comment_time = comment_time;
    }


}
