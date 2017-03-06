package com.example.gao.bargains.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gao.bargains.R;
import com.example.gao.bargains.data.Shop;

import java.util.List;
import java.util.Map;

/**
 * Created by gao on 2017/3/2.
 */

public class MyAdapter extends BaseAdapter {
    private List<Shop> data;
    private LayoutInflater layoutInflater;
    private Context context;
    public MyAdapter(Context context,List<Shop> data){
        this.context=context;
        this.data=data;
        this.layoutInflater=LayoutInflater.from(context);
    }
    public class MyItem{
        public ImageView image;
        public TextView name,distance,address,price,comment,pinglun,yuan;
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
        MyItem myItem = null;
        if(convertView == null){
            myItem = new MyItem();
            convertView = layoutInflater.inflate(R.layout.item_layout,null,false);
            myItem.image = (ImageView) convertView.findViewById(R.id.image);
            myItem.name = (TextView) convertView.findViewById(R.id.name);
            myItem.distance = (TextView) convertView.findViewById(R.id.distance);
            myItem.address = (TextView) convertView.findViewById(R.id.address);
            myItem.price = (TextView) convertView.findViewById(R.id.price);
            myItem.comment = (TextView) convertView.findViewById(R.id.comment);

            myItem.yuan = (TextView) convertView.findViewById(R.id.yuan);
            convertView.setTag(myItem);
        }else{
            myItem = (MyItem)convertView.getTag();
        }
        myItem.image.setBackgroundResource(R.mipmap.ic_launcher);
        myItem.name.setText((String)data.get(position).getShop_name());
        myItem.distance.setText((String)data.get(position).getDistance());
        myItem.address.setText((String)data.get(position).getAddress());
        myItem.price.setText((String)data.get(position).getPrice());
        myItem.comment.setText((String)data.get(position).getCommentNum());
        return convertView;
    }
}
