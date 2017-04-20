package com.example.gao.bargains.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.mapapi.model.LatLng;
import com.example.gao.bargains.Config;
import com.example.gao.bargains.R;
import com.example.gao.bargains.data.Order;
import com.example.gao.bargains.data.Shop;
import com.example.gao.bargains.utils.DistanceUtil;
import com.example.gao.bargains.utils.GetFavoriteList;
import com.example.gao.bargains.utils.GetOrderList;
import com.example.gao.bargains.utils.GetUserInfo;
import com.example.gao.bargains.utils.LoginStateUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by gao on 2017/2/27.
 */

public class ActPersonal extends Fragment {
    protected View mMainView;
    protected Context mContext;
    protected ImageButton per_picture;
    protected  TextView per_username;
    protected TextView per_order;
    protected TextView per_favorite;
    protected Button per_log_out;

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
         per_order = (TextView) mMainView.findViewById(R.id.per_order);
        //收藏
         per_favorite = (TextView) mMainView.findViewById(R.id.per_favorite);

        per_log_out = (Button) mMainView.findViewById(R.id.per_log_out);

        per_username.setText(GetUserInfo.getUserName());
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

                int x = LoginStateUtil.getLoginState();
                //未登陆则跳转到登陆界面
                if(x == 0){
                    MainActivity activity = (MainActivity)getActivity();
                    Intent intent = new Intent(activity,LoginActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getActivity(),ActUserInfo.class);
                    startActivity(intent);
                }
            }
        });

        //订单点击事件的逻辑
        per_order.setOnClickListener(new View.OnClickListener() {
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

                    Intent intent = new Intent(getActivity(),ActMyOrder.class);
                    startActivity(intent);

//                    RequestQueue mQueue = Volley.newRequestQueue(getActivity());
//                    JSONObject jsonObject = new JSONObject();
//                    try {
//                        jsonObject.put("user_id", GetUserInfo.getUserId());
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Config.MYORDER_URL, jsonObject, new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject jsonObject) {
//                            try {
//                                String state_of_json = jsonObject.getString("state");
//                                GetOrderList.getList().clear();
//                                JSONArray jsonArray = new JSONArray(state_of_json);
//                                for(int i = 0 ; i < jsonArray.length(); i++) {
//                                    JSONObject jsonObject_1  = new JSONObject();
//                                    jsonObject_1 = jsonArray.getJSONObject(i);
//                                    String order_id = jsonObject_1.getString("order_id");
//                                    String shop_uid = jsonObject_1.getString("shop_uid");
//                                    String user_id = jsonObject_1.getString("user_id");
//                                    String shop_name = jsonObject_1.getString("shop_name");
//                                    String shop_price = jsonObject_1.getString("shop_price");
//                                    String comment_state = jsonObject_1.getString("comment_state");
//                                    String order_time = jsonObject_1.getString("order_time");
//                                    Order order = new Order(Integer.parseInt(order_id),shop_uid,Integer.parseInt(user_id),shop_name,shop_price,Integer.parseInt(comment_state),order_time);
//                                     GetOrderList.getList().add(order);
//
//                                    //  Toast.makeText(getContext(),shop_name,Toast.LENGTH_LONG).show();
//
//
//                                }
//                                Intent intent = new Intent(getActivity(),ActMyOrder.class);
//                                startActivity(intent);
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError volleyError) {
//
//                        }
//                    });
//                    request.setTag("myFavorite");
//                    mQueue.add(request);
//                    mQueue.start();



                    // Toast.makeText(getContext(),"biubiubiu",Toast.LENGTH_LONG).show();
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
                    Intent intent = new Intent(getActivity(),ActFavorite.class);
                    startActivity(intent);



//                    RequestQueue mQueue = Volley.newRequestQueue(getActivity());
//                    JSONObject jsonObject = new JSONObject();
//                    try {
//                        jsonObject.put("user_id", GetUserInfo.getUserId());
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Config.MYFAVORITE_URL, jsonObject, new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject jsonObject) {
//                            try {
//                                String state_of_json = jsonObject.getString("state");
//                                GetFavoriteList.getList().clear();
//                                JSONArray jsonArray = new JSONArray(state_of_json);
//                                for(int i = 0 ; i < jsonArray.length(); i++) {
//                                    JSONObject jsonObject_1  = new JSONObject();
//                                    jsonObject_1 = jsonArray.getJSONObject(i);
//                                    String shop_uid = jsonObject_1.getString("shop_uid");
//                                    String shop_name = jsonObject_1.getString("shop_name");
//                                    String shop_image = jsonObject_1.getString("shop_image");
//                                    String shop_price = jsonObject_1.getString("shop_price");
//                                    String shop_comment = jsonObject_1.getString("shop_comment");
//                                    String shop_address = jsonObject_1.getString("shop_address");
//                                    String shop_phone = jsonObject_1.getString("shop_phone");
//                                    Double shop_latitude = jsonObject_1.getDouble("shop_latitude");
//                                    Double shop_longitude = jsonObject_1.getDouble("shop_longitude");
//                                    String shop_city = jsonObject_1.getString("shop_city");
//                                    String shop_keyword = jsonObject_1.getString("shop_keyword");
//                                    LatLng latLng = new LatLng(shop_latitude,shop_longitude);
//                                    String distance = DistanceUtil.getDistance(DistanceUtil.getLatlng(),latLng);
//                                    Shop shop = new Shop(shop_uid,shop_name,shop_image,distance,shop_address,shop_price,shop_comment,shop_phone,latLng,shop_city,shop_keyword);
//                                    GetFavoriteList.getList().add(shop);
//
//                                  //  Toast.makeText(getContext(),shop_name,Toast.LENGTH_LONG).show();
//
//
//                                }
//                                    Intent intent = new Intent(getActivity(),ActFavorite.class);
//                                    startActivity(intent);
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError volleyError) {
//
//                        }
//                    });
//                    request.setTag("myFavorite");
//                    mQueue.add(request);
//                    mQueue.start();






                }
            }
        });



        per_log_out.setOnClickListener(new View.OnClickListener() {
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
                    GetUserInfo.setUserName("请点击登录");
                    LoginStateUtil.setLoginState(0);
                    GetUserInfo.clear();
                    Intent intent = new Intent(getActivity(),MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        return mMainView;
    }




}
