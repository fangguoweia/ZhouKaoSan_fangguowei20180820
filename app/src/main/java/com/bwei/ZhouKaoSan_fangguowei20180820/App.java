package com.bwei.ZhouKaoSan_fangguowei20180820;

import android.app.Application;

import com.bwei.imageloaderlibrary.utils.ImageLoaderUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

/**
 * Created by 房国伟 on 2018/8/20.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        UMConfigure.init(this,"5a12384aa40fa3551f0001d1"
                ,"umeng", UMConfigure.DEVICE_TYPE_PHONE,"");//58edcfeb310c93091c000be2 5965ee00734be40b580001a0


        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        UMConfigure.setLogEnabled(true);

        //图片
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(getApplicationContext()).build();
        ImageLoader.getInstance().init(configuration);
    }
}
