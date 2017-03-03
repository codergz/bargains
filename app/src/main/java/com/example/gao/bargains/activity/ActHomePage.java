package com.example.gao.bargains.activity;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.Poi;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.example.gao.bargains.R;
import com.example.gao.bargains.adapter.MyAdapter;
import com.example.gao.bargains.utils.DistanceUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * Created by gao on 2017/2/27.
 */

public class ActHomePage extends Fragment {

    protected View mMainView;
    protected Context mContext;
    public EditText search_city,search_keyword;
    public Button btnSearch,btnDestory;
    public ListView listView;
    private PoiSearch mPoiSearch;
    public TextView textView;
    private LatLng mLatLng;
    private Location location;
    private LocationManager locationManager;
    private String provider;
    public List<Map<String, Object>> list;

    public int x = 0;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity.getApplicationContext();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.homepage_layout, container, false);
        search_city = (EditText) mMainView.findViewById(R.id.etSearchCity);
        search_keyword = (EditText) mMainView.findViewById(R.id.etSearchKeyword);
        btnSearch = (Button) mMainView.findViewById(R.id.btn_search);
        btnDestory = (Button) mMainView.findViewById(R.id.btn_destory);
        listView = (ListView) mMainView.findViewById(R.id.listview);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = search_city.getText().toString();
                String keyword = search_keyword.getText().toString();
                list = new ArrayList<Map<String, Object>>();
                //POI检索实例创建
                mPoiSearch = PoiSearch.newInstance();

                //创建POI检索监听者
                OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener(){
                    public void onGetPoiResult(PoiResult result){

                            try {
                                String[] name1 = new String[10];
                                String[] address1 = new String[10];
                                String[] phoneNum1 = new String[10];
                                String[] uid1 = new String[10];

                                for (int i = 0; i < result.getTotalPoiNum()&&i<10; i++) {
                                    name1[i] = result.getAllPoi().get(i).name;
                                    address1[i] = result.getAllPoi().get(i).address;
                                    phoneNum1[i] = result.getAllPoi().get(i).phoneNum;
                                    uid1[i] = result.getAllPoi().get(i).uid;
                                    mPoiSearch.searchPoiDetail((new PoiDetailSearchOption()).poiUid(uid1[i]));

                                }

                                //开启详情检索
                                //                      mPoiSearch.searchPoiDetail((new PoiDetailSearchOption()).poiUid(uid));

//                                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//                                for (int i = 0; i < result.getTotalPoiNum()&&i<10; i++) {
//
//                                    Map<String, Object> map = new HashMap<String, Object>();
//                                    map.put("image", R.mipmap.ic_launcher);
//                                    map.put("name", name1[i]);
//                                    map.put("distance", "500m" + i);
//                                    map.put("address", address1[i]);
//                                    map.put("price", "78" + i);
//                                    map.put("comment", "157" + i);
//
//                                    list.add(map);
//                                }
//                                MyAdapter myAdapter = new MyAdapter(getContext(), list);
//                                listView.setAdapter(myAdapter);



                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                    }
                    public void onGetPoiDetailResult(PoiDetailResult result){
                        if (result.error != SearchResult.ERRORNO.NO_ERROR) {
                            //详情检索失败
                            // result.error请参考SearchResult.ERRORNO
                        }else{


                                x++;
                                Map<String, Object> map = new HashMap<String, Object>();
                                map.put("image", R.mipmap.ic_launcher);
                                map.put("name", result.getName());
                                map.put("distance", Integer.toString(result.getImageNum()));
                                map.put("address", result.getAddress());
                                map.put("price", Double.toString(result.getPrice()) );
                                map.put("comment", Integer.toString(result.getCommentNum()) );


                                list.add(map);
                            if(x == 10){
                                MyAdapter myAdapter = new MyAdapter(getContext(), list);
                                listView.setAdapter(myAdapter);
                                x=0;
                            }


                        }


//                        if (result.error != SearchResult.ERRORNO.NO_ERROR) {
//                            //详情检索失败
//                            // result.error请参考SearchResult.ERRORNO
//                        }
//                        else {
//                            try {
//                                locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//                                provider = LocationManager.NETWORK_PROVIDER;
//
//                                location = locationManager.getLastKnownLocation(provider);
//                                 mLatLng = new LatLng(location.getLatitude(), location.getLongitude());
//                            }catch (SecurityException e){
//                                e.printStackTrace();
//                            }
//                            //检索成功
//                            //名称
//                            String name = result.getName();
//                            //地址
//                            String address = result.getAddress();
//                            //价位
//                            double price = result.getPrice();
//                            //评论数
//                            int commentNum = result.getCommentNum();
//                            //电话
//                            String phoneNumber = result.getTelephone();
//                            //
//                            LatLng latLng = result.getLocation();
//
//                           String distance = DistanceUtil.getDistance(mLatLng,latLng);
//
//                            textView.setText(
//                                    "名称：" + name
//                                    + "\n地址：" + address
//                                    + "\n价位：" + Double.toString(price)
//                                    + "\n评论数：" + Integer.toString(commentNum)
//                                    + "\n电话：" + phoneNumber
//                                    + "\n距离：" + distance
//                            );
//                        }


                    }

                    @Override
                    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

                    }
                };
                //设置POI检索监听者
                mPoiSearch.setOnGetPoiSearchResultListener(poiListener);

                // 发起检索请求
                mPoiSearch.searchInCity((new PoiCitySearchOption())
                        .city(city)
                        .keyword(keyword)
                        .pageNum(10));


//                MyAdapter myAdapter = new MyAdapter(getContext(), list);
//                listView.setAdapter(myAdapter);
            }
        });


        btnDestory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAdapter myAdapter = new MyAdapter(getContext(), list);
                listView.setAdapter(myAdapter);
                mPoiSearch.destroy();

            }
        });

        return mMainView;
    }


}