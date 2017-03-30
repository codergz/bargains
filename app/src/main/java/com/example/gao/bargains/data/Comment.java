package com.example.gao.bargains.data;

/**
 * Created by gao on 2017/3/30.
 */

public class Comment {

    String shop_uid;
    int user_id;
    String comment_content;
    String user_name;
    String comment_time;

    public Comment(String shop_uid, int user_id, String comment_content, String user_name, String comment_time){
        this.shop_uid = shop_uid;
        this.user_id = user_id;
        this.comment_content = comment_content;
        this.user_name= user_name;
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

    public String getCommentContent(){
        return comment_content;
    }
    public void setCommentContent(String comment_content){
        this.comment_content= comment_content;
    }

    public String getUserName(){
        return user_name;
    }
    public void setUserName(String user_name){
        this.user_name= user_name;
    }

    public String getCommentTime(){
        return comment_time;
    }
    public void setComment_time(String comment_time){
        this.comment_time = comment_time;
    }


}
