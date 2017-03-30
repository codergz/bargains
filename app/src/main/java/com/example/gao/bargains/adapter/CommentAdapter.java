package com.example.gao.bargains.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gao.bargains.R;
import com.example.gao.bargains.data.Comment;
import com.example.gao.bargains.data.Shop;

import java.util.List;

/**
 * Created by gao on 2017/3/30.
 */

public class CommentAdapter extends BaseAdapter {
    private List<Comment> data;
    private LayoutInflater layoutInflater;
    private Context context;
    public CommentAdapter(Context context,List<Comment> data){
        this.context=context;
        this.data=data;
        this.layoutInflater=LayoutInflater.from(context);
    }
    public class CommentItem{

        public TextView comment_content,user_name,comment_time;
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
        CommentAdapter.CommentItem myItem = null;
        if(convertView == null){
            myItem = new CommentAdapter.CommentItem();
            convertView = layoutInflater.inflate(R.layout.comment_item_layout,null,false);

            myItem.comment_content = (TextView) convertView.findViewById(R.id.comment_content);
            myItem.user_name = (TextView) convertView.findViewById(R.id.user_name);
            myItem.comment_time = (TextView) convertView.findViewById(R.id.comment_time);


            convertView.setTag(myItem);
        }else{
            myItem = (CommentAdapter.CommentItem)convertView.getTag();
        }
        myItem.comment_content.setText((String)data.get(position).getCommentContent());
        myItem.user_name.setText((String)data.get(position).getUserName());
        myItem.comment_time.setText((String)data.get(position).getCommentTime());


        return convertView;
    }
}