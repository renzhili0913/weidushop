package com.example.administrator.myapplication13.model;


import com.example.administrator.myapplication13.utils.RetrofitManager;
import com.google.gson.Gson;

import java.util.Map;


public class IModelImpl implements IModel {
    @Override
    public void getRequeryData(String url, Map<String, String> params, final Class clazz, final MyCallBack myCallBack) {
        RetrofitManager.getmRetrofitManager().post(url, params, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Object o = new Gson().fromJson(data, clazz);
                myCallBack.setData(o);
            }

            @Override
            public void onFail(String error) {
                myCallBack.setData(error);
            }
        });
    }

    @Override
    public void getRequeryData(String url, Class clazz, MyCallBack myCallBack) {
        RetrofitManager.getmRetrofitManager().get(url, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Object o = new Gson().fromJson(data, clazz);
                myCallBack.setData(o);
            }

            @Override
            public void onFail(String error) {
                myCallBack.setData(error);
            }
        });
    }

    @Override
    public void deleteRequeryData(String url, Class clazz, MyCallBack myCallBack) {
        RetrofitManager.getmRetrofitManager().delete(url, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Object o = new Gson().fromJson(data, clazz);
                myCallBack.setData(o);
            }

            @Override
            public void onFail(String error) {
                myCallBack.setData(error);
            }
        });
    }

    @Override
    public void putRequeryData(String url, Map<String, String> params, Class clazz, MyCallBack myCallBack) {
        RetrofitManager.getmRetrofitManager().put(url, params, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Object o = new Gson().fromJson(data, clazz);
                myCallBack.setData(o);
            }

            @Override
            public void onFail(String error) {
                myCallBack.setData(error);
            }
        });
    }
}
