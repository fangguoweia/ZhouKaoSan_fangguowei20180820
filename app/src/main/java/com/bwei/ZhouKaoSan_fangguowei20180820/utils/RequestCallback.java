package com.bwei.ZhouKaoSan_fangguowei20180820.utils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by 房国伟 on 2018/8/20.
 */
public interface RequestCallback {
    /**
     * Created by 房国伟 on 2018/8/20.
     */
    void failure(Call call, IOException e);
    void onResponse(Call call, Response response);
}
