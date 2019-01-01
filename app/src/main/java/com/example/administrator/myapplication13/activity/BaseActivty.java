package com.example.administrator.myapplication13.activity;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

public abstract class BaseActivty extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        //页面增加一个判断，因为4.4版本之前没有沉浸式可言
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        //动态网络权限
        stateNetWork();
        //初始化
        initView(savedInstanceState);
        //添加数据
        initData();
    }

    protected abstract void initData();


    protected abstract void initView(Bundle savedInstanceState);


    protected abstract int getLayoutResId();
    /**
     *添加动态网络权限
     *@author Administrator
     *@time 2018/12/29 0029 16:12
     */
    private void stateNetWork() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            String[] mStatenetwork = new String[]{
                    //写的权限
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    //读的权限
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    //入网权限
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    //WIFI权限
                    Manifest.permission.ACCESS_WIFI_STATE,
                    //读手机权限
                    Manifest.permission.READ_PHONE_STATE,
                    //网络权限
                    Manifest.permission.INTERNET,
            };
            ActivityCompat.requestPermissions(this,mStatenetwork,100);
        }
    }
    /**动态注册的回调*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @android.support.annotation.NonNull String[] permissions, @android.support.annotation.NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasPermission  = false;
        if(requestCode == 100){
            for (int i = 0;i<grantResults.length;i++){
                if(grantResults[i] == -1){
                    hasPermission = true;
                }
            }
        }
    }

}
