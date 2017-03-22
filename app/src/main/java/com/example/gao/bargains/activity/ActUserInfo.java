package com.example.gao.bargains.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.gao.bargains.Config;
import com.example.gao.bargains.R;
import com.example.gao.bargains.utils.GetUserInfo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gao on 2017/3/15.
 */

public class ActUserInfo extends Activity {
    private EditText etName,etPhone,etAddress;
    private Button btn_submit;
    private String state_of_json;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        etName = (EditText) findViewById(R.id.user_info_name);
        etPhone = (EditText) findViewById(R.id.user_info_phone);
        etAddress = (EditText) findViewById(R.id.user_info_address);
        btn_submit = (Button) findViewById(R.id.user_info_submit);


        etName.setText(GetUserInfo.getUserName());
        etPhone.setText(GetUserInfo.getUserPhone());
        etAddress.setText(GetUserInfo.getUserAddress());


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue mQueue = Volley.newRequestQueue(ActUserInfo.this);
                JSONObject jsonObject = new JSONObject();
                try {

                    jsonObject.put("user_id", GetUserInfo.getUserId());
                    jsonObject.put("user_name", etName.getText().toString());
                    jsonObject.put("user_phone", etPhone.getText().toString());
                    jsonObject.put("user_address", etAddress.getText().toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Config.UPDATE_URL, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            state_of_json=jsonObject.getString("state");
                            if(state_of_json.equals("success")){
                                GetUserInfo.setUserName(etName.getText().toString());
                                GetUserInfo.setUserPhone(etPhone.getText().toString());
                                GetUserInfo.setUserAddress(etAddress.getText().toString());
                                new AlertDialog.Builder(ActUserInfo.this).setTitle("系统提示")//设置对话框标题

                                        .setMessage("修改成功！")//设置显示的内容

                                        .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮



                                            @Override

                                            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件

                                                // TODO Auto-generated method stub



                                            }

                                        }).setNegativeButton("返回", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(ActUserInfo.this,MainActivity.class);
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
                        //Toast.makeText(getApplicationContext(),volleyError.toString(),Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(),volleyError.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
                request.setTag("update");
                mQueue.add(request);
                mQueue.start();



            }
        });
    }
}
