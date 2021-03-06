package com.example.administrator.myapplication13.utils;




import java.util.Map;
import java.util.Observer;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

public interface BaseApis<T> {
    @GET
    Observable<ResponseBody> get(@Url String url);
    @POST
    Observable<ResponseBody> post(@Url String url, @QueryMap Map<String, String> map);
    @Multipart
    @POST
    Observable<ResponseBody> postFormBody(@Url String url, @PartMap Map<String, RequestBody> requestBodyMap);
    @DELETE
    Observable<ResponseBody> delete(@Url String url);
    @PUT
    Observable<ResponseBody> put(@Url String url,@QueryMap Map<String, String> map);
    /**单张上传*/
    @POST
    Observable<ResponseBody> postFile(@Url String url,@Body MultipartBody multipartBody);
    /** 图文上传*//*
    @POST
    @Multipart
    Observable<RequestBody> upLoadImg(@Part MultipartBody.Part[] parts, @Part("APP_KEY") RequestBody APP_KEY, @Part("APP_TOKEN") RequestBody APP_TOKEN);
*/


}
