package com.example.gao.bargains.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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
import com.example.gao.bargains.SysApplication;
import com.example.gao.bargains.adapter.MyAdapter;
import com.example.gao.bargains.data.Shop;
import com.example.gao.bargains.utils.DistanceUtil;
import com.example.gao.bargains.utils.GetFavoriteList;
import com.example.gao.bargains.utils.GetUserInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gao on 2017/3/21.
 */

public class ActFavorite extends Activity {

    public Button favorite_list_backward;
    public ListView listView;
    private String provider;
    private List<Shop> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        SysApplication.getInstance().addActivity(this);


        listView = (ListView) findViewById(R.id.listview);
        favorite_list_backward = (Button) findViewById(R.id.favorite_list_backward);

        favorite_list_backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActFavorite.this,MainActivity.class);
                startActivity(intent);
            }
        });


        RequestQueue mQueue = Volley.newRequestQueue(ActFavorite.this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", GetUserInfo.getUserId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Config.MYFAVORITE_URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    String state_of_json = jsonObject.getString("state");

                    JSONArray jsonArray = new JSONArray(state_of_json);
                    for(int i = 0 ; i < jsonArray.length(); i++) {
                        JSONObject jsonObject_1  = new JSONObject();
                        jsonObject_1 = jsonArray.getJSONObject(i);
                        String shop_uid = jsonObject_1.getString("shop_uid");
                        String shop_name = jsonObject_1.getString("shop_name");
                        String shop_image = jsonObject_1.getString("shop_image");
                        String shop_price = jsonObject_1.getString("shop_price");
                        String shop_comment = jsonObject_1.getString("shop_comment");
                        String shop_address = jsonObject_1.getString("shop_address");
                        String shop_phone = jsonObject_1.getString("shop_phone");
                        Double shop_latitude = jsonObject_1.getDouble("shop_latitude");
                        Double shop_longitude = jsonObject_1.getDouble("shop_longitude");
                        String shop_city = jsonObject_1.getString("shop_city");
                        String shop_keyword = jsonObject_1.getString("shop_keyword");
                        LatLng latLng = new LatLng(shop_latitude,shop_longitude);
                        String distance = DistanceUtil.getDistance(DistanceUtil.getLatlng(),latLng);
                        Shop shop = new Shop(shop_uid,shop_name,shop_image,distance,shop_address,shop_price,shop_comment,shop_phone,latLng,shop_city,shop_keyword);
                        list.add(shop);

                        //  Toast.makeText(getContext(),shop_name,Toast.LENGTH_LONG).show();


                    }
                    MyAdapter myAdapter = new MyAdapter(ActFavorite.this, list);
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
        request.setTag("myFavorite");
        mQueue.add(request);
        mQueue.start();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Shop shop = list.get(position);
                String shop_uid = shop.getUid();
                String shop_name = shop.getShop_name();
                String shop_image = shop.getImageId();
                String price = shop.getPrice();
                String commentNum = shop.getCommentNum();
                String address = shop.getAddress();
                String phoneNum = shop.getPhoneNum();
                LatLng latLng = shop.getLatLng();
                Double latitude = latLng.latitude;
                Double longitude = latLng.longitude;

                //正常逻辑
                Intent intent = new Intent(ActFavorite.this,ActShopDetailPage.class);
                intent.putExtra("shop_uid",shop_uid);
                intent.putExtra("shop_name",shop_name);
                intent.putExtra("shop_image",shop_image);
                intent.putExtra("price",price);
                intent.putExtra("commentNum",commentNum);
                intent.putExtra("address",address);
                intent.putExtra("phoneNum",phoneNum);
                intent.putExtra("latitude",latitude);
                intent.putExtra("longitude",longitude);

                startActivity(intent);


                //*************8爬取数据的逻辑****************8
//                RequestQueue mQueue = Volley.newRequestQueue(ActFavorite.this);
//                JSONObject jsonObject = new JSONObject();
//                try {
//                    jsonObject.put("shop_uid", shop_uid);
//                    jsonObject.put("shop_name", shop_name);
//                    jsonObject.put("shop_image","111");
//                    jsonObject.put("shop_price", price);
//                    jsonObject.put("shop_comment", commentNum);
//                    jsonObject.put("shop_address", address);
//                    jsonObject.put("shop_phone", phoneNum);
//                    jsonObject.put("shop_latitude", latitude);
//                    jsonObject.put("shop_longitude", longitude);
//                    jsonObject.put("shop_city", "西安");
//                    jsonObject.put("shop_keyword", "美食");
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Config.ADDSHOP_URL, jsonObject, new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject jsonObject) {
//                        try {
//                            String state_of_json=jsonObject.getString("state");
//                            if(state_of_json.equals("success")){
//
//                                new AlertDialog.Builder(ActFavorite.this).setTitle("系统提示")//设置对话框标题
//
//                                        .setMessage("注册成功！")//设置显示的内容
//
//                                        .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮
//
//
//
//                                            @Override
//
//                                            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
//
//                                                // TODO Auto-generated method stub
//
//
//
//                                            }
//
//                                        }).show();//在按键响应事件中显示此对话框
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError volleyError) {
//                        //Toast.makeText(getApplicationContext(),volleyError.toString(),Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getApplicationContext(),volleyError.toString(),Toast.LENGTH_SHORT).show();
//                    }
//                });
//                request.setTag("regshop");
//                mQueue.add(request);
//                mQueue.start();


            }
        });


//        MyAdapter myAdapter = new MyAdapter(ActFavorite.this, list);
//        listView.setAdapter(myAdapter);

    }

    public void onBackPressed() {
        Intent intent = new Intent(ActFavorite.this,MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }

}
