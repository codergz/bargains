package com.example.gao.bargains.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


import com.example.gao.bargains.R;
import com.example.gao.bargains.adapter.OrderAdapter;
import com.example.gao.bargains.data.Order;
import com.example.gao.bargains.utils.GetOrderList;
import java.util.List;


/**
 * Created by gao on 2017/3/28.
 */

public class ActMyOrder extends Activity{

    public ListView listView;
    public Button order_list_backwrad;
    private String provider;
    private List<Order> list = GetOrderList.getList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_layout);

        order_list_backwrad = (Button) findViewById(R.id.order_list_backward);
        listView = (ListView) findViewById(R.id.listview);




        order_list_backwrad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

        OrderAdapter myAdapter = new OrderAdapter(ActMyOrder.this, list);
        listView.setAdapter(myAdapter);

    }
}
