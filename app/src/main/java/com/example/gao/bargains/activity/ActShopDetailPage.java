package com.example.gao.bargains.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.example.gao.bargains.R;

/**
 * Created by gao on 2017/3/6.
 */

public class ActShopDetailPage extends Activity {

    TextView shop_detail_name,shop_detail_price,shop_detail_address;
    Button shop_detail_backward,shop_detail_favorite,shop_detail_order,shop_detail_location,shop_detail_phone;

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
        String shop_name = bundle.getString("shop_name");
        String price = bundle.getString("price");
        String address = bundle.getString("address");
        final String phoneNum = bundle.getString("phoneNum");
        final Double latitude = bundle.getDouble("latitude");
        final Double longitude = bundle.getDouble("longitude");

        shop_detail_name.setText(shop_name);
        shop_detail_price.setText(price);
        shop_detail_address.setText(address);



        shop_detail_backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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

                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
                    startActivity(intent);
                }catch (SecurityException e){
                    e.printStackTrace();
                }
            }
        });

    }


}
