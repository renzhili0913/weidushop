package com.example.administrator.myapplication13.model;

import java.util.Map;

public interface IModel {
    void getRequeryData(String url, Map<String, String> params, Class clazz, MyCallBack myCallBack);
    void getRequeryData(String url, Class clazz, MyCallBack myCallBack);
    void deleteRequeryData(String url, Class clazz, MyCallBack myCallBack);
    void putRequeryData(String url, Map<String, String> params, Class clazz, MyCallBack myCallBack);
    void postFileRequeryData(String url, Map<String, String> params, Class clazz, MyCallBack myCallBack);
    void postFileMoreRequeryData(String url, Map<String, Object> params, Class clazz, MyCallBack myCallBack);

}
