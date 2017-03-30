package com.example.gao.bargains.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gao.bargains.R;
import com.example.gao.bargains.data.Order;
import com.example.gao.bargains.data.Shop;

import java.util.List;

/**
 * Created by gao on 2017/3/27.
 */

public class OrderAdapter extends BaseAdapter {
    private List<Order> data;
    private LayoutInflater layoutInflater;
    private Context context;
    public OrderAdapter(Context context,List<Order> data){
        this.context=context;
        this.data=data;
        this.layoutInflater=LayoutInflater.from(context);
    }
    public class OrderItem{

        public TextView name,price,yuan,tvTime,time;
    }
    @Override
    public int getCount() {
        return data.size();
    }
    /**
     * 获得某一位置的数据
     */
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }
    /**
     * 获得唯一标识
     */
    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OrderAdapter.OrderItem myItem = null;
        if(convertView == null){
            myItem = new OrderAdapter.OrderItem();
            convertView = layoutInflater.inflate(R.layout.order_item_layout,null,false);

            myItem.name = (TextView) convertView.findViewById(R.id.name);
            myItem.price = (TextView) convertView.findViewById(R.id.price);
            myItem.yuan = (TextView) convertView.findViewById(R.id.yuan);
            myItem.tvTime = (TextView) convertView.findViewById(R.id.time_tv);
            myItem.time = (TextView) convertView.findViewById(R.id.time);

            convertView.setTag(myItem);
        }else{
            myItem = (OrderAdapter.OrderItem)convertView.getTag();
        }

        myItem.name.setText((String)data.get(position).getShopName());
        myItem.price.setText((String)data.get(position).getShopPrice());
        myItem.time.setText((String)data.get(position).getOrderTime());
        return convertView;
    }
}
