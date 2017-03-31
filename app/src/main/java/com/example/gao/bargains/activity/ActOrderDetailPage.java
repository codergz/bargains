package com.example.gao.bargains.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.example.gao.bargains.Config;
import com.example.gao.bargains.R;
import com.example.gao.bargains.SysApplication;
import com.example.gao.bargains.data.Order;
import com.example.gao.bargains.utils.GetOrderList;
import com.example.gao.bargains.utils.GetUserInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by gao on 2017/3/30.
 */

public class ActOrderDetailPage extends Activity {

    public TextView order_detail_order_id,order_detail_shop_name,order_detail_shop_price,order_detail_user_name,order_detail_order_time,order_detail_comment_state;
    public Button order_detail_backward,order_detail_add_comment,order_detail_delete;

    String shop_uid,shop_name,shop_price,order_time;
    int order_id,comment_state,user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail_page_layout);

        SysApplication.getInstance().addActivity(this);


        order_detail_order_id = (TextView) findViewById(R.id.order_detail_order_id);
        order_detail_shop_name = (TextView) findViewById(R.id.order_detail_shop_name);
        order_detail_shop_price = (TextView) findViewById(R.id.order_detail_shop_price);
        order_detail_user_name = (TextView) findViewById(R.id.order_detail_user_name);
        order_detail_order_time = (TextView) findViewById(R.id.order_detail_order_time);
        order_detail_comment_state = (TextView) findViewById(R.id.order_detail_comment_state);

        order_detail_backward = (Button) findViewById(R.id.order_detail_backward);
        order_detail_add_comment = (Button) findViewById(R.id.order_detail_add_comment);
        order_detail_delete = (Button) findViewById(R.id.order_detail_delete);


        Intent intent =getIntent();
        Bundle bundle = intent.getExtras();
        order_id = bundle.getInt("order_id");

        shop_uid = bundle.getString("shop_uid");
        user_id = bundle.getInt("user_id");
        shop_name = bundle.getString("shop_name");
        shop_price = bundle.getString("price");
        comment_state = bundle.getInt("comment_state");
        order_time = bundle.getString("order_time");

        order_detail_order_id.setText(String.valueOf(order_id));
        order_detail_shop_name.setText(shop_name);
        order_detail_shop_price.setText(shop_price);
        order_detail_user_name.setText(GetUserInfo.getUserName());
        order_detail_order_time.setText(order_time);
        //如果评论状态为1，则修改评论状态为已评论
        if(comment_state == 1 ) {
            order_detail_comment_state.setText("已评论");
        }

        //返回按钮逻辑
        order_detail_backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActOrderDetailPage.this,ActMyOrder.class);
                startActivity(intent);
            }
        });

        //增加评论按钮逻辑
        order_detail_add_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(ActOrderDetailPage.this,String.valueOf(order_id),Toast.LENGTH_LONG).show();
                //如果已评论，则弹出提示框提醒用户
                if(comment_state == 1){
                    new AlertDialog.Builder(ActOrderDetailPage.this).setTitle("系统提示")//设置对话框标题

                            .setMessage("抱歉！您已对该订单进行过评论")//设置显示的内容

                            .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮



                                @Override

                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件

                                    // TODO Auto-generated method stub



                                }

                            }).show();//在按键响应事件中显示此对话框
                }//if 已评论逻辑
                else{
                    Intent intent = new Intent(ActOrderDetailPage.this,ActAddComment.class);
                    intent.putExtra("order_id",order_id);
                    intent.putExtra("shop_uid",shop_uid);
                    intent.putExtra("user_id",user_id);
                    startActivity(intent);
                }



            }
        });

        //删除订单逻辑
        order_detail_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(ActOrderDetailPage.this,"Delete Order!",Toast.LENGTH_LONG).show();
                RequestQueue mQueue = Volley.newRequestQueue(ActOrderDetailPage.this);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("order_id", order_id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Config.DELETEORDER_URL, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            String state_of_json = jsonObject.getString("state");

                            if(state_of_json.equals("success")){

                                Intent intent = new Intent(ActOrderDetailPage.this,ActMyOrder.class);
                                startActivity(intent);


                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
                request.setTag("DeleteOrder");
                mQueue.add(request);
                mQueue.start();
            }
        });

    }

    public void onBackPressed() {
        Intent intent = new Intent(ActOrderDetailPage.this,ActMyOrder.class);
        startActivity(intent);
        super.onBackPressed();
    }
}
