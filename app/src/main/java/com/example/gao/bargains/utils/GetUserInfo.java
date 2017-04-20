package com.example.gao.bargains.utils;

/**
 * Created by gao on 2017/2/28.
 */

public class GetUserInfo {

    public static String user_id = "请点击登录";
    public static String user_account = "请点击登录";

    public static String user_password = "未登录";
    public static String user_name = "请点击登录";
    public static String user_phone = "未登陆";
    public static String login_state = "0";
    public static String user_address = "未登陆";
    public static Double user_money = 1000.0;

    public static String getUserId(){
        return user_id;
    }
    public static void setUserId(String id){
        user_id = id;
    }

    public static String getUserAccount(){
        return user_account;
    }
    public static void setUserAccount(String account){
        user_account = account;
    }

    public static String getUserPassword(){
        return user_password;
    }
    public static void setUserPassword(String password){
        user_password = password;
    }


    public static String getUserName(){
        return user_name;
    }
    public static void setUserName(String name){
        user_name = name;
    }


    public static String getUserPhone(){
        return user_phone;
    }
    public static void setUserPhone(String phone){
        user_phone = phone;
    }

    public static String getUserLoginState(){
        return login_state;
    }
    public static void setUserLoginState(String loginState){
        login_state = loginState;
    }

    public static String getUserAddress(){
        return user_address;
    }
    public static void setUserAddress(String address){
        user_address = address;
    }

    public static Double getUserMoney(){
        return user_money;
    }
    public static void setUserMoney(Double money){
        user_money = money;
    }


    public static void clear(){

        user_id = "请点击登录";
        user_account = "请点击登录";

        user_password = "未登录";
        user_name = "未登录";
        user_phone = "未登陆";
        login_state = "0";
        user_address = "未登陆";
        user_money = 1000.0;

    }

}
