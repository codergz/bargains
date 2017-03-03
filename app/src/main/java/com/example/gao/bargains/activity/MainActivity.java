package com.example.gao.bargains.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;
import com.example.gao.bargains.R;
import com.example.gao.bargains.ui.IndicatorFragmentActivity;

import java.util.List;

/**
 * Created by gao on 2017/2/27.
 */

public class MainActivity extends IndicatorFragmentActivity {
    public static final int FRAGMENT_ONE = 0;
    public static final int FRAGMENT_TWO = 1;
    public static final int FRAGMENT_THREE = 2;

    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        SDKInitializer.initialize(getApplicationContext());

        Window window = getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(getResources().getColor(R.color.common_title));
    }




    @Override
   protected int supplyTabs(List<TabInfo> tabs) {
        tabs.add(new TabInfo(FRAGMENT_ONE, getString(R.string.fragment_homepage),
                ActHomePage.class));
        tabs.add(new TabInfo(FRAGMENT_TWO, getString(R.string.fragment_nearby),
                ActNearby.class));
        tabs.add(new TabInfo(FRAGMENT_THREE, getString(R.string.fragment_personal),
                ActPersonal.class));

        return FRAGMENT_TWO;
    }
}