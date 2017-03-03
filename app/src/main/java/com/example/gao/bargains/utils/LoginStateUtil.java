package com.example.gao.bargains.utils;

/**
 * Created by gao on 2017/2/27.
 */
//用于判断用户是否登陆的工具类
public class LoginStateUtil {
    static int loginState = 0;//初始未登陆,如果登陆上就设为1
    public static int getLoginState(){
        return loginState;
    }
    public static void setLoginState(int a){
        loginState = a;
    }
}
