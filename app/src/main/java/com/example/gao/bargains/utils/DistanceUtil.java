package com.example.gao.bargains.utils;

import com.baidu.mapapi.model.LatLng;

import java.text.NumberFormat;

/**
 * Created by gao on 2017/3/2.
 */

public class DistanceUtil {
    public static String getDistance(LatLng start, LatLng end) {
        //自己实现距离算法：
        /**
         * 计算两点之间距离
         * @param start
         * @param end
         * @return String  多少m ,  多少km
         */

        double lat1 = (Math.PI/180)*start.latitude;
        double lat2 = (Math.PI/180)*end.latitude;

        double lon1 = (Math.PI/180)*start.longitude;
        double lon2 = (Math.PI/180)*end.longitude;

        //地球半径
        double R = 6371.004;

        //两点间距离 m，如果想要米的话，结果*1000就可以了
        double dis =  Math.acos(Math.sin(lat1)*Math.sin(lat2)+Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon2-lon1))*R;
        NumberFormat nFormat = NumberFormat.getNumberInstance();  //数字格式化对象
        if(dis < 1){               //当小于1千米的时候用,用米做单位保留一位小数

            nFormat.setMaximumFractionDigits(1);    //已可以设置为0，这样跟百度地图APP中计算的一样
            dis *= 1000;

            return nFormat.format(dis)+"m";
        }else{
            nFormat.setMaximumFractionDigits(2);
            return nFormat.format(dis)+"km";
        }
    }
}
