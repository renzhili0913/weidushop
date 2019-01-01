package com.example.administrator.myapplication13.model;

import java.util.Map;

public interface IModel {
    void getRequeryData(String url, Map<String, String> params, Class clazz, MyCallBack myCallBack);
    void getRequeryData(String url, Class clazz, MyCallBack myCallBack);

}
