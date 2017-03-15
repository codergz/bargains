package com.example.gao.bargains.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.gao.bargains.R;
import com.example.gao.bargains.utils.GetUserInfo;

/**
 * Created by gao on 2017/3/15.
 */

public class ActUserInfo extends Activity {
    private EditText name,phone,address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        name = (EditText) findViewById(R.id.user_info_name);
        phone = (EditText) findViewById(R.id.user_info_phone);
        address = (EditText) findViewById(R.id.user_info_address);


        name.setText(GetUserInfo.getUserName());
        phone.setText(GetUserInfo.getUserPhone());
        address.setText(GetUserInfo.getUserAddress());

    }
}
