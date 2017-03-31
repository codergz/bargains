package com.example.gao.bargains;

/**
 * Created by gao on 2017/2/27.
 */

public class Config {
    public static final int LOGIN_STATE = 0;//初始未登陆

    public static final String NET_ADDRESS = "http://172.17.188.1:8080";//无线网地址

    public static final String LOGIN_URL = NET_ADDRESS + "/bargains/Login";//登陆查询的url

    public static final String REG_URL = NET_ADDRESS + "/bargains/Register";//注册url

    public static final String UPDATE_URL = NET_ADDRESS + "/bargains/UpdateUserInfo";//更改个人信息的url

    public static final String FAVORITE_URL = NET_ADDRESS + "/bargains/Favorite";//收藏的url

    public static final String MYFAVORITE_URL = NET_ADDRESS + "/bargains/MyFavorite";//我的收藏的url

    public static final String ADDSHOP_URL = NET_ADDRESS + "/bargains/AddShop";//我的收藏的url

    public static final String SEARCHCITYKEYWORD_URL = NET_ADDRESS + "/bargains/SearchCityKeyword";//我的收藏的url

    public static final String USERORDER_URL = NET_ADDRESS + "/bargains/UserOrder";//下订单的url

    public static final String MYORDER_URL = NET_ADDRESS + "/bargains/MyOrder";//我的订单的url

    public static final String GETCOMMENT_URL = NET_ADDRESS + "/bargains/GetComment";//得到某个商店评论的url

    public static final String ADDCOMMENT_URL = NET_ADDRESS + "/bargains/AddComment";//对某个订单评论的URL

    public static final String DELETEORDER_URL = NET_ADDRESS + "/bargains/DeleteOrder";//删除某个订单的URL


}


