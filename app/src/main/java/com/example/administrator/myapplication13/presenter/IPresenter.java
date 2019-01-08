package com.example.administrator.myapplication13.presenter;

import java.util.Map;

public interface IPresenter {
    void getRequeryData(String url, Map<String, String> params, Class clazz);
    void getRequeryData(String url, Class clazz);
    void deleteRequeryData(String url, Class clazz);
    void putRequeryData(String url, Map<String, String> params, Class clazz);
}
