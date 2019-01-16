package com.example.administrator.myapplication13.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;

import com.example.administrator.myapplication13.MyApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitManager <T>{
    private final String BASE_URL = "http://mobile.bwstudent.com/small/";
    private static RetrofitManager mRetrofitManager;
    private BaseApis baseApis;

    public static RetrofitManager getmRetrofitManager() {
        if (mRetrofitManager==null){
            synchronized (RetrofitManager.class){
                mRetrofitManager=new RetrofitManager();
            }
        }
        return mRetrofitManager;
    }

    public RetrofitManager() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                //取出保存的userid,sessionid
                SharedPreferences sharedPreferences =MyApplication.getApplication().getSharedPreferences("UserID",Context.MODE_PRIVATE);
                String userId = sharedPreferences.getString("userId", "");
                String sessionId = sharedPreferences.getString("sessionId", "");
                //重新构造请求
                Request.Builder builder1 = request.newBuilder();
                //把原来的请求参数原样放进去
                builder1.method(request.method(),request.body());
                //添加自己的参数
                if(!TextUtils.isEmpty(userId)&&!TextUtils.isEmpty(sessionId)){
                    builder1.addHeader("userId",userId);
                    builder1.addHeader("sessionId",sessionId);
                }
                Request build = builder1.build();
                return chain.proceed(build);
            }
        });

        builder.connectTimeout(10,TimeUnit.SECONDS);
        builder.readTimeout(10,TimeUnit.SECONDS);
        builder.writeTimeout(10,TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);
        OkHttpClient client = builder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .client(client)
                .build();
        baseApis = retrofit.create(BaseApis.class);
    }

    /**
     * 可以这样生成Map<String, RequestBody> requestBodyMap
     * Map<String, String> requestDataMap这里面放置上传数据的键值对。
     */
    public Map<String, RequestBody> generateRequestBody(Map<String, String> requestDataMap) {
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        for (String key : requestDataMap.keySet()) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),
                    requestDataMap.get(key) == null ? "" : requestDataMap.get(key));
            requestBodyMap.put(key, requestBody);
        }
        return requestBodyMap;
    }
    /**
     * get请求，下面的每一个请求都改了一下
     */
    public void get(String url, HttpListener listener) {

        baseApis.get(url)
                //后台执行在哪个线程中
                .subscribeOn(Schedulers.io())
                //最终完成后执行在哪个线程
                .observeOn(AndroidSchedulers.mainThread())
                //设置我们的rxJava
                .subscribe(getObserver(listener));
    }

    /**
     * 表单post请求
     */
    public void postFormBody(String url, Map<String, RequestBody> map, HttpListener listener) {
        if (map == null) {
            map = new HashMap<>();
        }

        baseApis.postFormBody(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }


    /**
     * 普通post请求
     */
    public void post(String url, Map<String, String> map, HttpListener listener) {
        if (map == null) {
            map = new HashMap<>();
        }
        baseApis.post(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }
    /**
     * 普通delete请求
     */
    public void delete(String url, HttpListener listener) {
        baseApis.delete(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }
    /**
     * 普通put请求
     */
    public void put(String url, Map<String, String> map, HttpListener listener) {
        if (map == null) {
            map = new HashMap<>();
        }
        baseApis.put(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }
    /**
     *上传头像
     *@author Administrator
     *@time 2019/1/16 0016 11:25
     */
    public void postFile(String url, Map<String, String> map,HttpListener listener) {
        if (map == null) {
            map = new HashMap<>();
        }
        MultipartBody multipartBody = filesToMultipartBody(map);

        baseApis.postFile(url, multipartBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }

    public static MultipartBody filesToMultipartBody(Map<String,String> map) {
        MultipartBody.Builder builder = new MultipartBody.Builder();

        for (Map.Entry<String, String> entry : map.entrySet()) {
            File file = new File(entry.getValue());
            builder.addFormDataPart(entry.getKey(), "图片1.png",
                    RequestBody.create(MediaType.parse("multipart/form-data"), file));
        }

        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();
        return multipartBody;
    }


    private Observer getObserver(final HttpListener listener){
        Observer observer = new Observer<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String data = responseBody.string();
                    if (listener != null) {
                        listener.onSuccess(data);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if (listener != null) {
                        listener.onFail(e.getMessage());
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                if (listener != null) {
                    listener.onFail(e.getMessage());
                }
            }

            @Override
            public void onCompleted() {

            }
        };

        return observer;
    }

    public interface HttpListener {
        void onSuccess(String data);

        void onFail(String error);
    }

    //判断是否有网络
    public boolean hasNetWork(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        return activeNetworkInfo!=null&&activeNetworkInfo.isAvailable();
    }
    //转换bitmap为file
    public  void saveBitmap(Bitmap bitmap, String path, int quality) throws IOException {
        String dir = path.substring(0, path.lastIndexOf("/"));
        File dirFile = new File(dir);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            try {
                if (!dirFile.mkdirs()) {
                    return;
                }
            } catch (Exception e) {
                Log.e("TAG", e.getMessage());
            }

        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(path);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, quality, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e("TAG", e.getMessage());
        }
    }
}
