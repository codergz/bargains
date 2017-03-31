package com.example.gao.bargains.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.gao.bargains.Config;
import com.example.gao.bargains.R;
import com.example.gao.bargains.data.Order;
import com.example.gao.bargains.utils.GetOrderList;
import com.example.gao.bargains.utils.GetUserInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gao on 2017/3/30.
 */

public class ActAddComment extends Activity implements AdapterView.OnItemSelectedListener{
    public Button add_comment_backward,btn_add_comment;
    public TextView tv_comment_grade;
    public EditText et_comment_content;

    private Spinner spinner;
    private List<String> list;
    private ArrayAdapter<String> adapter;

    int user_id,order_id;
    String shop_uid,user_name;
    String comment_grade = "5.0",comment_content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment_layout);

        add_comment_backward = (Button) findViewById(R.id.add_comment_backward);
        btn_add_comment = (Button) findViewById(R.id.btn_add_comment);

        tv_comment_grade = (TextView) findViewById(R.id.tv_comment_grade);

        et_comment_content = (EditText)findViewById(R.id.et_comment_content);

        //获取我的订单传过来的intent数据
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        order_id = bundle.getInt("order_id");
        user_id = bundle.getInt("user_id");
        shop_uid = bundle.getString("shop_uid");
        user_name = GetUserInfo.getUserName();
        //回退事件
        add_comment_backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        spinner = (Spinner) findViewById(R.id.spinner);
        list = new ArrayList<>();
        list.add("5.0分");
        list.add("4.0分");
        list.add("3.0分");
        list.add("2.0分");
        list.add("1.0分");

        //2.定义适配器
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);

        //3.adapter设置下拉样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //4.spinner加载适配器
        spinner.setAdapter(adapter);

        //5.spinner设置监听器
        spinner.setOnItemSelectedListener(this);



        //提交评论事件
        btn_add_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                comment_content = et_comment_content.getText().toString();

                if (TextUtils.isEmpty(comment_content)) {
                    new AlertDialog.Builder(ActAddComment.this).setTitle("系统提示")//设置对话框标题

                            .setMessage("评论不能为空！")//设置显示的内容

                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮

                                @Override
                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                    // TODO Auto-generated method stub
                                }

                            }).show();//在按键响应事件中显示此对话框
                }

                else{
                RequestQueue mQueue = Volley.newRequestQueue(ActAddComment.this);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("shop_uid", shop_uid);
                    jsonObject.put("user_id", user_id);
                    jsonObject.put("order_id", order_id);
                    jsonObject.put("user_name", user_name);
                    jsonObject.put("comment_content", comment_content);
                    jsonObject.put("comment_grade", comment_grade);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Config.ADDCOMMENT_URL, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            String state_of_json = jsonObject.getString("state");
                            if (state_of_json.equals("success")) {

                                new AlertDialog.Builder(ActAddComment.this).setTitle("系统提示")//设置对话框标题

                                        .setMessage("评论成功！")//设置显示的内容

                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                                // TODO Auto-generated method stub
                                                Intent intent = new Intent(ActAddComment.this, ActMyOrder.class);
                                                startActivity(intent);
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

                    }
                });
                request.setTag("add_comment");
                mQueue.add(request);
                mQueue.start();
                }
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        comment_grade = adapter.getItem(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


}
