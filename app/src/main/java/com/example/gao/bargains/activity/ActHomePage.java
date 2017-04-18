package com.example.gao.bargains.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
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
import com.example.gao.bargains.Config;
import com.example.gao.bargains.R;

import com.example.gao.bargains.adapter.ShopAdapter;
import com.example.gao.bargains.data.Shop;
import com.example.gao.bargains.utils.DistanceUtil;
import com.example.gao.bargains.utils.GetFavoriteList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    public List<Shop> list;

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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Shop shop = list.get(position);
                String shop_uid = shop.getUid();
                String shop_name = shop.getShop_name();
                String shop_image = shop.getImageId();
                String price = shop.getPrice();
                String comment = shop.getCommentNum();
                String address = shop.getAddress();
                String phoneNum = shop.getPhoneNum();
                LatLng latLng = shop.getLatLng();
                Double latitude = latLng.latitude;
                Double longitude = latLng.longitude;
                String shop_city = shop.getShop_city();
                String shop_keyword = shop.getShop_keyword();

//                Toast.makeText(getContext(),shop.getAddress(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(),ActShopDetailPage.class);
                intent.putExtra("shop_uid",shop_uid);
                intent.putExtra("shop_name",shop_name);
                intent.putExtra("shop_image",shop_image);

                intent.putExtra("price",price);
                intent.putExtra("comment",comment);
                intent.putExtra("address",address);
                intent.putExtra("phoneNum",phoneNum);
                intent.putExtra("latitude",latitude);
                intent.putExtra("longitude",longitude);
                intent.putExtra("shop_city", shop_city);
                intent.putExtra("shop_keyword", shop_keyword);

                startActivity(intent);
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shop_city = search_city.getText().toString();
                String shop_keyword = search_keyword.getText().toString();
                list = new ArrayList<Shop>();


                //利用自己搭建的数据库获得数据
                RequestQueue mQueue = Volley.newRequestQueue(getActivity());
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("shop_city", shop_city);
                    jsonObject.put("shop_keyword", shop_keyword);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Config.SEARCHCITYKEYWORD_URL, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            String state_of_json = jsonObject.getString("state");
                            GetFavoriteList.getList().clear();
                            JSONArray jsonArray = new JSONArray(state_of_json);
                            for(int i = 0 ; i < jsonArray.length(); i++) {
                                JSONObject jsonObject_1  = new JSONObject();
                                jsonObject_1 = jsonArray.getJSONObject(i);
                                String shop_uid = jsonObject_1.getString("shop_uid");
                                String shop_name = jsonObject_1.getString("shop_name");
                                String shop_image = jsonObject_1.getString("shop_image");
                                String shop_price = jsonObject_1.getString("shop_price");
                                String shop_comment = jsonObject_1.getString("shop_comment");
                                String shop_address = jsonObject_1.getString("shop_address");
                                String shop_phone = jsonObject_1.getString("shop_phone");
                                Double shop_latitude = jsonObject_1.getDouble("shop_latitude");
                                Double shop_longitude = jsonObject_1.getDouble("shop_longitude");
                                String shop_city = jsonObject_1.getString("shop_city");
                                String shop_keyword = jsonObject_1.getString("shop_keyword");
                                LatLng latLng = new LatLng(shop_latitude,shop_longitude);
                                String distance = DistanceUtil.getDistance(DistanceUtil.getLatlng(),latLng);
                                Shop shop = new Shop(shop_uid,shop_name,shop_image,distance,shop_address,shop_price,shop_comment,shop_phone,latLng,shop_city,shop_keyword);
                                list.add(shop);

                                //  Toast.makeText(getContext(),shop_name,Toast.LENGTH_LONG).show();


                            }

                            ShopAdapter myAdapter = new ShopAdapter(getContext(), list);
                            listView.setAdapter(myAdapter);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
                request.setTag("searchcitykeyword");
                mQueue.add(request);
                mQueue.start();



                //****************利用百度地图api获得数据方式*****************8

//                //POI检索实例创建
//                mPoiSearch = PoiSearch.newInstance();
//
//                //创建POI检索监听者
//                OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener(){
//                    public void onGetPoiResult(PoiResult result){
//
//                            try {
//                                String[] name1 = new String[10];
//                                String[] address1 = new String[10];
//                                String[] phoneNum1 = new String[10];
//                                String[] uid1 = new String[10];
//
//                                for (int i = 0; i < result.getTotalPoiNum()&&i<10; i++) {
//                                    name1[i] = result.getAllPoi().get(i).name;
//                                    address1[i] = result.getAllPoi().get(i).address;
//                                    phoneNum1[i] = result.getAllPoi().get(i).phoneNum;
//                                    uid1[i] = result.getAllPoi().get(i).uid;
//                                    mPoiSearch.searchPoiDetail((new PoiDetailSearchOption()).poiUid(uid1[i]));
//
//                                }
//
//
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//
//                    }
//                    public void onGetPoiDetailResult(PoiDetailResult result){
//                        if (result.error != SearchResult.ERRORNO.NO_ERROR) {
//                            //详情检索失败
//                            // result.error请参考SearchResult.ERRORNO
//                        }else{
//
//                                //由于网络请求延迟，当第十个数据保存完毕再显示列表
//                                x++;
//
//
//                                list.add(new Shop(result.getUid(),result.getName(),"111",DistanceUtil.getDistance(DistanceUtil.getLatlng(),result.getLocation()),result.getAddress(),Double.toString(result.getPrice()),Integer.toString(result.getCommentNum()),result.getTelephone(),result.getLocation(),"西安","美食"));
//
//
//
//
//                            if(x == 10){
//                                ShopAdapter myAdapter = new ShopAdapter(getContext(), list);
//                               listView.setAdapter(myAdapter);
//                                x=0;
//                            }
//
//
//                        }
//
//                    }
//
//                    @Override
//                    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
//
//                    }
//                };
//                //设置POI检索监听者
//                mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
//
//                // 发起检索请求
//                mPoiSearch.searchInCity((new PoiCitySearchOption())
//                        .city(shop_city)
//                        .keyword(shop_keyword)
//                        .pageNum(10));

//

            }
        });


        btnDestory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopAdapter myAdapter = new ShopAdapter(getContext(), list);
                listView.setAdapter(myAdapter);
                mPoiSearch.destroy();

            }
        });

        return mMainView;
    }


}