package com.example.administrator.myapplication13.presenter;



import com.example.administrator.myapplication13.model.IModelImpl;
import com.example.administrator.myapplication13.model.MyCallBack;
import com.example.administrator.myapplication13.view.IView;

import java.util.Map;

public class IPresenterImpl implements IPresenter {
    private IView iView;
    private IModelImpl iModel;

    public IPresenterImpl(IView iView) {
        this.iView = iView;
        iModel=new IModelImpl();
    }

    @Override
    public void getRequeryData(String url, Map<String, String> params, Class clazz) {
        iModel.getRequeryData(url, params, clazz, new MyCallBack() {
            @Override
            public void setData(Object o) {
                iView.showRequeryData(o);
            }
        });
    }

    @Override
    public void getRequeryData(String url, Class clazz) {
        iModel.getRequeryData(url, clazz, new MyCallBack() {
            @Override
            public void setData(Object o) {
                iView.showRequeryData(o);
            }
        });
    }

    @Override
    public void deleteRequeryData(String url, Class clazz) {
        iModel.deleteRequeryData(url, clazz, new MyCallBack() {
            @Override
            public void setData(Object o) {
                iView.showRequeryData(o);
            }
        });
    }

    @Override
    public void putRequeryData(String url, Map<String, String> params, Class clazz) {
        iModel.putRequeryData(url, params, clazz, new MyCallBack() {
            @Override
            public void setData(Object o) {
                iView.showRequeryData(o);
            }
        });
    }

    @Override
    public void postFileRequeryData(String url, Map<String, String> params, Class clazz) {
        iModel.postFileRequeryData(url, params, clazz, new MyCallBack() {
            @Override
            public void setData(Object o) {
                iView.showRequeryData(o);
            }
        });
    }

    @Override
    public void postFileMoreRequeryData(String url, Map<String, Object> params, Class clazz) {
        iModel.postFileMoreRequeryData(url, params, clazz, new MyCallBack() {
            @Override
            public void setData(Object o) {
                iView.showRequeryData(o);
            }
        });
    }

    public void onDetach(){
        if (iModel!=null){
         iModel=null;
        }
        if (iView!=null){
            iView=null;
        }
    }
}
