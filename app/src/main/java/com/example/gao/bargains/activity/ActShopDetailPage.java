package com.example.gao.bargains.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.mapapi.model.LatLng;

import com.bumptech.glide.Glide;
import com.example.gao.bargains.Config;
import com.example.gao.bargains.R;
import com.example.gao.bargains.SysApplication;
import com.example.gao.bargains.adapter.CommentAdapter;
import com.example.gao.bargains.adapter.OrderAdapter;
import com.example.gao.bargains.data.Comment;
import com.example.gao.bargains.data.Order;
import com.example.gao.bargains.utils.GetOrderList;
import com.example.gao.bargains.utils.GetUserInfo;
import com.example.gao.bargains.utils.LoginStateUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by gao on 2017/3/6.
 */

public class ActShopDetailPage extends Activity {

    ImageView shop_detail_image_show;
    TextView shop_detail_name,shop_detail_price,shop_detail_address;
    Button shop_detail_backward,shop_detail_favorite,shop_detail_order,shop_detail_location,shop_detail_phone,getComment;
    String shop_name,shop_price,shop_address,shop_phone,state_of_json;
    String shop_uid,shop_comment,shop_image,shop_city,shop_keyword;


    public ListView listView;
    private String provider;
    private List<Comment> list =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_detail_page);

        SysApplication.getInstance().addActivity(this);



        Window window = getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(getResources().getColor(R.color.common_title));

        shop_detail_image_show = (ImageView) findViewById(R.id.image);
        shop_detail_name = (TextView) findViewById(R.id.shop_detail_name);
        shop_detail_price = (TextView) findViewById(R.id.shop_detail_price);
        shop_detail_address = (TextView) findViewById(R.id.shop_detail_address);


        shop_detail_backward =  (Button) findViewById(R.id.shop_detail_backward);
        shop_detail_favorite =  (Button) findViewById(R.id.shop_detail_favorite);
        shop_detail_order = (Button) findViewById(R.id.order);
        shop_detail_location = (Button) findViewById(R.id.location);
        shop_detail_phone = (Button) findViewById(R.id.phone);
//        getComment = (Button)findViewById(R.id.getComment);

        //得到基本信息逻辑
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        shop_uid = bundle.getString("shop_uid");
        shop_name = bundle.getString("shop_name");
        shop_image = bundle.getString("shop_image");
        shop_price = bundle.getString("price");
        shop_comment = bundle.getString("comment");
        shop_address = bundle.getString("address");
        shop_phone = bundle.getString("phoneNum");
        shop_city = bundle.getString("shop_city");
        shop_keyword = bundle.getString("shop_keyword");
        final Double latitude = bundle.getDouble("latitude");
        final Double longitude = bundle.getDouble("longitude");

        String url = Config.GETPICTURE_URL + shop_image;

        Glide.with(ActShopDetailPage.this)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.loading)
                .crossFade()
                .error(R.drawable.error)
                .into(shop_detail_image_show);
        shop_detail_name.setText(shop_name);
        shop_detail_price.setText(shop_price);
        shop_detail_address.setText(shop_address);

        //得到评论逻辑
        listView = (ListView) findViewById(R.id.comment_list);

        RequestQueue mQueue = Volley.newRequestQueue(ActShopDetailPage.this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("shop_uid", shop_uid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Config.GETCOMMENT_URL, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            String state_of_json = jsonObject.getString("state");

                            JSONArray jsonArray = new JSONArray(state_of_json);
                            for(int i = 0 ; i < jsonArray.length(); i++) {
                                JSONObject jsonObject_1  = new JSONObject();
                                jsonObject_1 = jsonArray.getJSONObject(i);
                                String shop_uid = jsonObject_1.getString("shop_uid");
                                String user_id = jsonObject_1.getString("user_id");
                                String order_id = jsonObject_1.getString("order_id");
                                String user_name = jsonObject_1.getString("user_name");
                                String comment_content = jsonObject_1.getString("comment_content");
                                String comment_grade = jsonObject_1.getString("comment_grade");
                                String comment_time = jsonObject_1.getString("comment_time");
                                Comment comment = new Comment(shop_uid,Integer.parseInt(user_id),Integer.parseInt(order_id),user_name,comment_content,comment_grade,comment_time);

                                list.add(comment);

                                 // Toast.makeText(ActShopDetailPage.this,shop_name,Toast.LENGTH_LONG).show();


                            }

                            CommentAdapter myAdapter = new CommentAdapter(ActShopDetailPage.this, list);
                            listView.setAdapter(myAdapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
                request.setTag("getComment");
                mQueue.add(request);
                mQueue.start();
//            }
//        });






        //回退按钮点击事件
        shop_detail_backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //收藏按钮点击事件
        shop_detail_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //未登录不可以收藏
                if(LoginStateUtil.getLoginState() == 0){
                     Intent intent = new Intent(ActShopDetailPage.this,LoginActivity.class);
                        startActivity(intent);
                    }
                //如果登陆了，进行存储数据库操作
                else {

                    RequestQueue mQueue = Volley.newRequestQueue(ActShopDetailPage.this);
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("shop_uid",shop_uid);
                        jsonObject.put("user_id", GetUserInfo.getUserId());
                        jsonObject.put("shop_name", shop_name);
                        jsonObject.put("shop_image", shop_image);
                        jsonObject.put("shop_price", shop_price);
                        jsonObject.put("shop_comment", shop_comment);

                        jsonObject.put("shop_address", shop_address);
                        jsonObject.put("shop_phone", shop_phone);
                        jsonObject.put("shop_latitude", latitude);
                        jsonObject.put("shop_longitude", longitude);
                        jsonObject.put("shop_city", shop_city);
                        jsonObject.put("shop_keyword", shop_keyword);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Config.FAVORITE_URL, jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try {
                                state_of_json = jsonObject.getString("state");
                                if (state_of_json.equals("success")) {
                                    //Toast.makeText(ActShopDetailPage.this, "已成功加入收藏夹", Toast.LENGTH_SHORT).show();

                                    new AlertDialog.Builder(ActShopDetailPage.this).setTitle("系统提示")//设置对话框标题

                                            .setMessage("收藏成功！")//设置显示的内容

                                            .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮



                                                @Override

                                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件

                                                    // TODO Auto-generated method stub



                                                }

                                            }).show();//在按键响应事件中显示此对话框




                                } else if (state_of_json.equals("exist")) {
                                    new AlertDialog.Builder(ActShopDetailPage.this).setTitle("系统提示")

                                            .setMessage("收藏失败！您的收藏列表里已存在该商铺")

                                            .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮



                                                @Override

                                                public void onClick(DialogInterface dialog, int which) {

                                                    // TODO Auto-generated method stub



                                                }

                                            }).show();//在按键响应事件中显示此对话框
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            //Toast.makeText(getApplicationContext(),volleyError.toString(),Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), volleyError.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    request.setTag("collect");
                    mQueue.add(request);
                    mQueue.start();


                }
            }//onClick
        });//收藏点击事件


        //抢购按钮点击事件
        shop_detail_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //未登录不可以下单
                if(LoginStateUtil.getLoginState() == 0){
                    Intent intent = new Intent(ActShopDetailPage.this,LoginActivity.class);
                    startActivity(intent);
                }
                //如果登陆了，进行下单逻辑
                else{
                    RequestQueue mQueue = Volley.newRequestQueue(ActShopDetailPage.this);
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("shop_uid",shop_uid);
                        jsonObject.put("user_id", GetUserInfo.getUserId());
                        jsonObject.put("shop_name",shop_name);
                        jsonObject.put("shop_price",shop_price);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Config.USERORDER_URL, jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try {
                                state_of_json = jsonObject.getString("state");
                                if (state_of_json.equals("success")) {
                                    //更改用户的钱数
                                    GetUserInfo.setUserMoney(GetUserInfo.getUserMoney()-Double.parseDouble(shop_price));
                                    new AlertDialog.Builder(ActShopDetailPage.this).setTitle("系统提示")//设置对话框标题

                                            .setMessage("下单成功！")//设置显示的内容

                                            .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮



                                                @Override

                                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件

                                                    // TODO Auto-generated method stub



                                                }

                                            }).show();//在按键响应事件中显示此对话框




                                } else if (state_of_json.equals("not_enough")) {
                                    new AlertDialog.Builder(ActShopDetailPage.this).setTitle("系统提示")

                                            .setMessage("下单失败！您的余额不足")

                                            .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮



                                                @Override

                                                public void onClick(DialogInterface dialog, int which) {

                                                    // TODO Auto-generated method stub



                                                }

                                            }).show();//在按键响应事件中显示此对话框
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            //Toast.makeText(getApplicationContext(),volleyError.toString(),Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), volleyError.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    request.setTag("order");
                    mQueue.add(request);
                    mQueue.start();
                }
            }
        });




        //定位按钮点击事件
        shop_detail_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActShopDetailPage.this,ActMap.class);
                intent.putExtra("latitude",latitude);
                intent.putExtra("longitude",longitude);
                startActivity(intent);

            }
        });

        shop_detail_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + shop_phone));
                    startActivity(intent);
                }catch (SecurityException e){
                    e.printStackTrace();
                }
            }
        });

    }


}
