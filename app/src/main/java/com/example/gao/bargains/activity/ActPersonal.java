package com.example.gao.bargains.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gao.bargains.R;
import com.example.gao.bargains.utils.GetUserName;
import com.example.gao.bargains.utils.LoginStateUtil;


/**
 * Created by gao on 2017/2/27.
 */

public class ActPersonal extends Fragment {
    protected View mMainView;
    protected Context mContext;
    protected ImageButton per_picture;
    protected  TextView per_username;
    protected TextView per_money;
    protected TextView per_favorite;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity.getApplicationContext();
    }
    //更改用户名

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //未登录不能查看个人
//        int x = LoginStateUtil.getLoginState();
//        if(x == 0){
//            mMainView = inflater.inflate(R.layout.login_layout, container, false);
//            return mMainView;
//        }
        //非活动类，先得到布局
        mMainView = inflater.inflate(R.layout.personal_layout, container, false);
        //头像
        per_picture = (ImageButton) mMainView.findViewById(R.id.per_picture);
        //用户名（未登陆的时候默认显示请点击登陆）
         per_username = (TextView) mMainView.findViewById(R.id.text_view_username);
        //余额
         per_money = (TextView) mMainView.findViewById(R.id.per_money);
        //收藏
         per_favorite = (TextView) mMainView.findViewById(R.id.per_favorite);


        per_username.setText(GetUserName.getUsername());
        //头像点击事件逻辑
        per_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = LoginStateUtil.getLoginState();
                //未登陆则跳转到登陆界面
                if(x == 0){
                    MainActivity activity = (MainActivity)getActivity();
                    Intent intent = new Intent(activity,LoginActivity.class);
                    startActivity(intent);
                }else{
                    //已登录的正常逻辑
                    Toast.makeText(getContext(),"biubiubiu",Toast.LENGTH_LONG).show();
                }
            }
        });

        //用户名点击事件逻辑
        per_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                int x = LoginStateUtil.getLoginState();
//                //未登陆则跳转到登陆界面
//                if(x == 0){
//                    MainActivity activity = (MainActivity)getActivity();
//                    Intent intent = new Intent(activity,LoginActivity.class);
//                    startActivity(intent);
//                }else{
//                    //已登录的正常逻辑
//                    Toast.makeText(getContext(),"biubiubiu",Toast.LENGTH_LONG).show();
//                }
            }
        });

        //余额点击事件的逻辑
        per_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = LoginStateUtil.getLoginState();
                //未登陆则跳转到登陆界面
                if(x == 0){
//                    Intent intent = new Intent(getActivity(),ActCityChoose2.class);
//                    startActivity(intent);
                }else{
                    //已登录的正常逻辑
                    Toast.makeText(getContext(),"biubiubiu",Toast.LENGTH_LONG).show();
                }
            }
        });

        //收藏点击事件的逻辑
        per_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = LoginStateUtil.getLoginState();
                //未登陆则跳转到登陆界面
                if(x == 0){
                    MainActivity activity = (MainActivity)getActivity();
                    Intent intent = new Intent(activity,LoginActivity.class);
                    startActivity(intent);
                }else{
                    //已登录的正常逻辑
                    Toast.makeText(getContext(),"biubiubiu",Toast.LENGTH_LONG).show();
                }
            }
        });


        return mMainView;
    }




}
