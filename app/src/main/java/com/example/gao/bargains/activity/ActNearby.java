package com.example.gao.bargains.activity;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiAddrInfo;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.example.gao.bargains.R;
import com.example.gao.bargains.utils.DistanceUtil;
import com.example.gao.bargains.utils.MyPoiOverlay;
import com.example.gao.bargains.utils.overlayutil.PoiOverlay;

import java.util.List;

import static android.R.attr.radius;

/**
 * Created by gao on 2017/2/27.
 */

public class ActNearby extends Fragment {
    private Location location;
    private LocationManager locationManager;
    private BaiduMap baiduMap;
    //poi检索实例
    public PoiSearch mPoiSearch;
    private boolean isFirstLocate = true;
    private String provider;
    protected View mMainView;
    protected Context mContext;
    MapView mMapView = null;
    BaiduMap mBaiduMap;
    Button btn_nearby_mylocation, btn_nearby_search,btn_destory;
    EditText et_nearby_type;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity.getApplicationContext();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mMainView = inflater.inflate(R.layout.nearby_layout, container, false);
        mMapView = (MapView) mMainView.findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        //将定位点显示在地图上
        mBaiduMap.setMyLocationEnabled(true);
        btn_nearby_mylocation = (Button) mMainView.findViewById(R.id.btn_nearby_mylocation);
        btn_nearby_search = (Button) mMainView.findViewById(R.id.btn_nearby_search);
        btn_destory = (Button) mMainView.findViewById(R.id.btn_destory);
        et_nearby_type = (EditText) mMainView.findViewById(R.id.et_nearby_type);

        btn_nearby_mylocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavigateTo(location);
            }
        });

        btn_nearby_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //POI检索实例创建
                mPoiSearch = PoiSearch.newInstance();

                //创建POI检索监听者
                OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener(){
                    public void onGetPoiResult(PoiResult result){
                        try {
                            mBaiduMap.clear();
                            //创建PoiOverlay
                            PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
                            //设置overlay可以处理标注点击事件
                            mBaiduMap.setOnMarkerClickListener(overlay);
                            //设置PoiOverlay数据
                            overlay.setData(result);
                            //添加PoiOverlay到地图中
                            overlay.addToMap();
                            overlay.zoomToSpan();
                            return;

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    public void onGetPoiDetailResult(PoiDetailResult result){
                        //获取Place详情页检索结果
                    }

                    @Override
                    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

                    }
                };
                //设置POI检索监听者
                mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
                searchNearBy(et_nearby_type.getText().toString());
                // 发起检索请求
//                mPoiSearch.searchInCity((new PoiCitySearchOption())
//                        .city("西安")
//                        .keyword("美食")
//                        .pageNum(10));

            }
        });
                btn_destory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //释放POI检索实例
                        mPoiSearch.destroy();
                    }
                });


        //第一次启动时定位以及手动定位
        try {
            baiduMap = mMapView.getMap();
            //getSystemService不能在碎片中获取
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            provider = LocationManager.NETWORK_PROVIDER;

           location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                navigateTo(location);
            }
            locationManager.requestLocationUpdates(provider, 5000, 1, locationListener);
        }catch (SecurityException e){
            e.printStackTrace();
        }




        return mMainView;
    }

    private void searchNearBy(String type){
        PoiNearbySearchOption option = new PoiNearbySearchOption();
        option.keyword(type);
        option.sortType(PoiSortType.distance_from_near_to_far);
        option.location(new LatLng(location.getLatitude(), location.getLongitude()));
        if (radius != 0) {
            option.radius(radius);
        } else {
            option.radius(1000);
        }

        option.pageCapacity(20);
        mPoiSearch.searchNearby(option);
    }
    private void navigateTo(Location location) {
        if (isFirstLocate) {
            LatLng mLl = new LatLng(location.getLatitude(), location.getLongitude());
            DistanceUtil.setLatlng(mLl);
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(mLl);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(16f);
            baiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }
        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.latitude(location.getLatitude());
        locationBuilder.longitude(location.getLongitude());
        MyLocationData locationData = locationBuilder.build();
        mBaiduMap.setMyLocationData(locationData);
    }
    private void mNavigateTo(Location location) {

            LatLng mLl = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(mLl);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(16f);
            baiduMap.animateMapStatus(update);
        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.latitude(location.getLatitude());
        locationBuilder.longitude(location.getLongitude());
        MyLocationData locationData = locationBuilder.build();
        mBaiduMap.setMyLocationData(locationData);

    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                navigateTo(location);
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };





    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        mBaiduMap.setMyLocationEnabled(false);
        try{
        if(locationManager != null){
            locationManager.removeUpdates(locationListener);
        }}catch (SecurityException e){
            e.printStackTrace();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }



}




