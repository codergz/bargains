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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.mapapi.model.LatLng;
import com.example.gao.bargains.Config;
import com.example.gao.bargains.R;
import com.example.gao.bargains.utils.GetUserInfo;
import com.example.gao.bargains.utils.LoginStateUtil;

import org.json.JSONException;
import org.json.JSONObject;



/**
 * Created by gao on 2017/3/6.
 */

public class ActShopDetailPage extends Activity {

    TextView shop_detail_name,shop_detail_price,shop_detail_address;
    Button shop_detail_backward,shop_detail_favorite,shop_detail_order,shop_detail_location,shop_detail_phone;
    String shop_name,shop_price,shop_address,shop_phone,state_of_json;
    String shop_uid,shop_comment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_detail_page);
        Window window = getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(getResources().getColor(R.color.common_title));

        shop_detail_name = (TextView) findViewById(R.id.shop_detail_name);
        shop_detail_price = (TextView) findViewById(R.id.shop_detail_price);
        shop_detail_address = (TextView) findViewById(R.id.shop_detail_address);


        shop_detail_backward =  (Button) findViewById(R.id.shop_detail_backward);
        shop_detail_favorite =  (Button) findViewById(R.id.shop_detail_favorite);
        shop_detail_order = (Button) findViewById(R.id.order);
        shop_detail_location = (Button) findViewById(R.id.location);
        shop_detail_phone = (Button) findViewById(R.id.phone);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
         shop_uid = bundle.getString("shop_uid");
         shop_name = bundle.getString("shop_name");
         shop_price = bundle.getString("price");
         shop_comment = bundle.getString("comment");
         shop_address = bundle.getString("address");
         shop_phone = bundle.getString("phoneNum");
        final Double latitude = bundle.getDouble("latitude");
        final Double longitude = bundle.getDouble("longitude");

        shop_detail_name.setText(shop_name);
        shop_detail_price.setText(shop_price);
        shop_detail_address.setText(shop_address);


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
                        jsonObject.put("shop_price", shop_price);
                        jsonObject.put("shop_comment", shop_comment);

                        jsonObject.put("shop_address", shop_address);
                        jsonObject.put("shop_phone", shop_phone);
                        jsonObject.put("shop_latitude", latitude);
                        jsonObject.put("shop_longitude", longitude);


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
                    request.setTag("reg");
                    mQueue.add(request);
                    mQueue.start();


                }
            }//onClick
        });//收藏点击事件


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
