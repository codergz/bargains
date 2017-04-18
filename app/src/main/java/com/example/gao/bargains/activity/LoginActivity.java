package com.example.gao.bargains.activity;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.gao.bargains.Config;
import com.example.gao.bargains.R;
import com.example.gao.bargains.SysApplication;
import com.example.gao.bargains.utils.GetUserInfo;

import com.example.gao.bargains.utils.LoginStateUtil;

import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gao on 2017/2/27.
 */

public class LoginActivity extends Activity {
    Button btnLogin, btnRegister;
    EditText etAccount = null, etPassword = null;
    String state_of_json = null;
    View mMainView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        //管理活动退出
        SysApplication.getInstance().addActivity(this);

        //状态栏颜色设置
        Window window = getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(getResources().getColor(R.color.common_white));

        btnLogin = (Button) findViewById(R.id.btn_login);
        btnRegister = (Button) findViewById(R.id.btn_register);

        etAccount = (EditText) findViewById(R.id.etAccount);
        etPassword = (EditText) findViewById(R.id.etPassword);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(etAccount.getText()) || TextUtils.isEmpty(etPassword.getText())) {
                    Toast.makeText(LoginActivity.this, "账户密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                RequestQueue mQueue = Volley.newRequestQueue(LoginActivity.this);




                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("account", etAccount.getText().toString());
                    jsonObject.put("password", etPassword.getText().toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Config.LOGIN_URL, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {

                            state_of_json = jsonObject.getString("state");
                            //success代表账户密码都正确
                            if (state_of_json.equals("success")) {
                                GetUserInfo.setUserId(jsonObject.getString("id"));
                                GetUserInfo.setUserAccount(jsonObject.getString("user_account"));
                                GetUserInfo.setUserPassword(jsonObject.getString("user_password"));
                                GetUserInfo.setUserName(jsonObject.getString("user_name"));
                                GetUserInfo.setUserPhone(jsonObject.getString("user_phone"));
                                GetUserInfo.setUserLoginState(jsonObject.getString("login_state"));
                                GetUserInfo.setUserAddress(jsonObject.getString("user_address"));
                                GetUserInfo.setUserMoney(jsonObject.getDouble("user_money"));

                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);

                                LoginStateUtil.setLoginState(1);
                                startActivity(intent);

                            }
                            //passwordWrong代表数据库中有该账户，但是密码输错了
                            else if(state_of_json.equals("passwordWrong")) {
                                Toast.makeText(LoginActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                            }
                            //最后一种情况是不存在该账户
                            else{
                                Toast.makeText(LoginActivity.this,"不存在该账户",Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), volleyError.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                request.setTag("login");
                mQueue.add(request);
                mQueue.start();

           }


        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}

