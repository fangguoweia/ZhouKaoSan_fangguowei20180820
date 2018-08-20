package com.bwei.ZhouKaoSan_fangguowei20180820.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 房国伟 on 2018/8/20.
 */
public class OKHttpUtils {

    private static OKHttpUtils okHttpUtils;
    private OkHttpClient okHttpClient1;

    //构造方法
    private OKHttpUtils(){
        okHttpClient1 = new OkHttpClient().newBuilder()
                .writeTimeout(2000, TimeUnit.MICROSECONDS)
                .build();
    }
    /**
     * 暴露方法
     */
    public static OKHttpUtils getInstaance(){
        if (okHttpUtils == null){
            synchronized (OKHttpUtils.class){
                okHttpUtils = new OKHttpUtils();
            }
        }
        return okHttpUtils;
    }
    /**
     * get封装
     */
    public void getData(String url, HashMap<String,String> params,final RequestCallback requestCallback){
        StringBuilder urlsb = new StringBuilder();
        String allurl = "";
        for (Map.Entry<String,String> stringStringEntry : params.entrySet()){
            urlsb.append("?").append(stringStringEntry.getKey())
                    .append("=").append(stringStringEntry.getValue()).append("&");
        }
        allurl = url+urlsb.toString().substring(0,urlsb.length()-1);
        System.out.println("url:"+allurl);
        Request request = new Request.Builder()
                .url(allurl).get().build();
        okHttpClient1.newCall(request).enqueue(new Callback() {
            //失败
            @Override
            public void onFailure(Call call, IOException e) {
                if (requestCallback!=null){
                    requestCallback.failure(call,e);
                }
            }
            //成功
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (requestCallback!=null){
                    requestCallback.onResponse(call,response);
                }
            }
        });
    }
    /**
     * post请求
     */
    public void postData(final String url, HashMap<String ,String> params, final RequestCallback requestCallback){
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if (params!=null&&params.size()>0){
            for (Map.Entry<String,String> stringStringEntry: params.entrySet()){
                formBodyBuilder.add(stringStringEntry.getKey(),stringStringEntry.getValue());
            }
        }
        Request request = new Request.Builder()
                .url(url).post(formBodyBuilder.build()).build();
        okHttpClient1.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (requestCallback!=null){
                    requestCallback.failure(call,e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (requestCallback!= null){
                    requestCallback.onResponse(call,response);
                }
            }
        });

    }

}
