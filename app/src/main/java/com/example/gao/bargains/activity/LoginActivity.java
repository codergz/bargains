package com.example.gao.bargains.activity;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.gao.bargains.utils.GetUserName;
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
/**
 * Created by gao on 2017/2/27.
 */

public class LoginActivity extends Activity {
    Button btnLogin, btnRegister;
    EditText etAccount = null, etPassword = null;
    String name = null;
    View mMainView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

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

//                String data = etAccount.getText().toString();
//                GetUserName.setUsername(data);
//                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                startActivity(intent);
//                LoginStateUtil.setLoginState(1);


                RequestQueue mQueue = Volley.newRequestQueue(LoginActivity.this);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("name", etAccount.getText().toString());
                    jsonObject.put("password", etPassword.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Config.LOGIN_URL, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {

                            name = jsonObject.getString("success");
                            if (name.equals("登录成功")) {

                            } else {
                                Toast.makeText(LoginActivity.this,"账户密码错误",Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(LoginActivity.this,name,Toast.LENGTH_SHORT).show();

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

