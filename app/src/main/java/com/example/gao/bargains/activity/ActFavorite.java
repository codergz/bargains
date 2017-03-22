package com.example.gao.bargains.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.baidu.mapapi.model.LatLng;
import com.example.gao.bargains.R;
import com.example.gao.bargains.adapter.MyAdapter;
import com.example.gao.bargains.data.Shop;
import com.example.gao.bargains.utils.GetFavoriteList;

import java.util.List;

/**
 * Created by gao on 2017/3/21.
 */

public class ActFavorite extends Activity {
    public ListView listView;
    private String provider;
    private List<Shop> list = GetFavoriteList.getList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        listView = (ListView) findViewById(R.id.listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Shop shop = list.get(position);
                String shop_uid = shop.getUid();
                String shop_name = shop.getShop_name();
                String price = shop.getPrice();
                String commentNum = shop.getCommentNum();
                String address = shop.getAddress();
                String phoneNum = shop.getPhoneNum();
                LatLng latLng = shop.getLatLng();
                Double latitude = latLng.latitude;
                Double longitude = latLng.longitude;
//                Toast.makeText(getContext(),shop.getAddress(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ActFavorite.this,ActShopDetailPage.class);
                intent.putExtra("shop_uid",shop_uid);
                intent.putExtra("shop_name",shop_name);
                intent.putExtra("price",price);
                intent.putExtra("commentNum",commentNum);
                intent.putExtra("address",address);
                intent.putExtra("phoneNum",phoneNum);
                intent.putExtra("latitude",latitude);
                intent.putExtra("longitude",longitude);

                startActivity(intent);
            }
        });


        MyAdapter myAdapter = new MyAdapter(ActFavorite.this, list);
        listView.setAdapter(myAdapter);

    }
}
