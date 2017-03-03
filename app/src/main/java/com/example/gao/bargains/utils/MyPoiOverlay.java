package com.example.gao.bargains.utils;

import com.baidu.mapapi.map.BaiduMap;
import com.example.gao.bargains.utils.overlayutil.PoiOverlay;

/**
 * Created by gao on 2017/3/3.
 */


public class MyPoiOverlay extends PoiOverlay {
        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }
        @Override
        public boolean onPoiClick(int index) {
            super.onPoiClick(index);
            return true;
        }
    }


