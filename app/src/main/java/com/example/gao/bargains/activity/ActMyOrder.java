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
import com.example.gao.bargains.adapter.OrderAdapter;
import com.example.gao.bargains.data.Order;

import com.example.gao.bargains.data.Shop;
import com.example.gao.bargains.utils.GetOrderList;

import java.util.List;


/**
 * Created by gao on 2017/3/28.
 */

public class ActMyOrder extends Activity{

    public ListView listView;
    private String provider;
    private List<Order> list = GetOrderList.getList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_layout);


        listView = (ListView) findViewById(R.id.listview);







        //订单列表的点击逻辑
 //       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Order order = list.get(position);
//                String shop_uid = order.getShopUid();
//                String shop_name = order.getShopName();
//                String price = order.getShopPrice();
//                String order_time = order.getOrderTime();
//
//
//
//                //正常逻辑
//                Intent intent = new Intent(ActMyOrder.this,ActShopDetailPage.class);
//                intent.putExtra("shop_uid",shop_uid);
//                intent.putExtra("shop_name",shop_name);
//                intent.putExtra("price",price);
//
//                startActivity(intent);
//            }
        //});

        OrderAdapter myAdapter = new OrderAdapter(ActMyOrder.this, list);
        listView.setAdapter(myAdapter);

    }
}
