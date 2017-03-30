package com.example.gao.bargains.utils;

import com.example.gao.bargains.data.Order;
import com.example.gao.bargains.data.Shop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gao on 2017/3/28.
 */

public class GetOrderList {
    public static List<Order> list = new ArrayList<>();
    public static List<Order> getList(){
        return list;
    }
}
