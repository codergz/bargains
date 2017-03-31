package com.example.gao.bargains.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.example.gao.bargains.Config;
import com.example.gao.bargains.R;
import com.example.gao.bargains.SysApplication;
import com.example.gao.bargains.adapter.OrderAdapter;
import com.example.gao.bargains.data.Order;
import com.example.gao.bargains.utils.GetOrderList;
import com.example.gao.bargains.utils.GetUserInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by gao on 2017/3/28.
 */

public class ActMyOrder extends Activity {

    public ListView listView;
    public Button order_list_backwrad;
    private String provider;
   // private List<Order> list = GetOrderList.getList();
    private List<Order> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_layout);

        SysApplication.getInstance().addActivity(this);

        order_list_backwrad = (Button) findViewById(R.id.order_list_backward);
        listView = (ListView) findViewById(R.id.listview);


        RequestQueue mQueue = Volley.newRequestQueue(ActMyOrder.this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", GetUserInfo.getUserId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Config.MYORDER_URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    String state_of_json = jsonObject.getString("state");
                    GetOrderList.getList().clear();
                    JSONArray jsonArray = new JSONArray(state_of_json);
                    for(int i = 0 ; i < jsonArray.length(); i++) {
                        JSONObject jsonObject_1  = new JSONObject();
                        jsonObject_1 = jsonArray.getJSONObject(i);
                        String order_id = jsonObject_1.getString("order_id");
                        String shop_uid = jsonObject_1.getString("shop_uid");
                        String user_id = jsonObject_1.getString("user_id");
                        String shop_name = jsonObject_1.getString("shop_name");
                        String shop_price = jsonObject_1.getString("shop_price");
                        String comment_state = jsonObject_1.getString("comment_state");
                        String order_time = jsonObject_1.getString("order_time");
                        Order order = new Order(Integer.parseInt(order_id),shop_uid,Integer.parseInt(user_id),shop_name,shop_price,Integer.parseInt(comment_state),order_time);
                        list.add(order);

                        //  Toast.makeText(getContext(),shop_name,Toast.LENGTH_LONG).show();


                    }
                    OrderAdapter myAdapter = new OrderAdapter(ActMyOrder.this, list);
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
        request.setTag("myOrder");
        mQueue.add(request);
        mQueue.start();


        order_list_backwrad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActMyOrder.this,MainActivity.class);
                startActivity(intent);
            }
        });


        //订单列表的点击逻辑
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Order order = list.get(position);
                int order_id = order.getOrderId();
                String shop_uid = order.getShopUid();
                int user_id = order.getUserId();
                String shop_name = order.getShopName();
                String price = order.getShopPrice();
                int comment_state = order.getCommentState();
                String order_time = order.getOrderTime();



                //正常逻辑
                Intent intent = new Intent(ActMyOrder.this,ActOrderDetailPage.class);
                intent.putExtra("order_id",order_id);
                intent.putExtra("shop_uid",shop_uid);
                intent.putExtra("user_id",user_id);
                intent.putExtra("shop_name",shop_name);
                intent.putExtra("price",price);
                intent.putExtra("comment_state",comment_state);
                intent.putExtra("order_time",order_time);
                startActivity(intent);
            }
        });

//        OrderAdapter myAdapter = new OrderAdapter(ActMyOrder.this, list);
//        listView.setAdapter(myAdapter);

    }

    public void onBackPressed() {
        Intent intent = new Intent(ActMyOrder.this,MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}
