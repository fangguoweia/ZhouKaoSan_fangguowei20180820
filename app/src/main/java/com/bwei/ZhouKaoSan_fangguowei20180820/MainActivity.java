package com.bwei.ZhouKaoSan_fangguowei20180820;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bwei.ZhouKaoSan_fangguowei20180820.Presenter.RecyPresenter;
import com.bwei.ZhouKaoSan_fangguowei20180820.adapter.ProductAdapter;
import com.bwei.ZhouKaoSan_fangguowei20180820.common.Api;
import com.bwei.ZhouKaoSan_fangguowei20180820.utils.OKHttpUtils;
import com.bwei.ZhouKaoSan_fangguowei20180820.utils.RequestCallback;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements XRecyclerView.LoadingListener {

    private UMShareAPI umShareAPI;

    private Button btnqq;
    private XRecyclerView xrecycler;
    private ProductBean productBean;
    private Handler handler=new Handler(){};
    private ProductAdapter productAdapter;
    private int page=1;
    private RecyPresenter presenter;
    private ImageView touxiang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        umShareAPI = UMShareAPI.get(this);
        //初始化控件
        initVIew();
        //QQ登入
        degnruQQ();
        //XRecyclerView请求数据
        requestProduct();
    }

    private void requestProduct() {
        HashMap<String,String> params = new HashMap<>();
        params.put("keywords","手机");
        params.put("page",page+"");

        OKHttpUtils.getInstaance().postData(Api.PRODUCT_URL, params, new RequestCallback() {

            private String result;

            @Override
            public void failure(Call call, IOException e) {
                //失败
            }

            @Override
            public void onResponse(Call call, Response response) {
                //成功
                if (response.code() == 200){
                    try {
                        result = response.body().string();
                        System.out.println("result:"+result);
                        parseProductBean(result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }


        });
    }
    private void parseProductBean(String result) {
        productBean = new Gson().fromJson(result, ProductBean.class);
        handler.post(new Runnable() {
            @Override
            public void run() {
                fillDatas();
            }
        });
    }

    /**
     * 绘制列表
     */
    private void fillDatas() {
        xrecycler.setLayoutManager(new LinearLayoutManager(this));
        if (page==1){
            productAdapter = new ProductAdapter(MainActivity.this,productBean.data);
            xrecycler.setAdapter(productAdapter);
            xrecycler.refreshComplete();
        }else {
            if (productAdapter !=null){
                productAdapter.loadData(productBean.data);
                xrecycler.loadMoreComplete();
            }
        }

    }

    private void initVIew() {
        btnqq = findViewById(R.id.btnqq);
        xrecycler = findViewById(R.id.xrecycler_view);
        //支持刷新加载
        xrecycler.setLoadingListener(this);
        xrecycler.setLoadingMoreEnabled(true);
        touxiang = findViewById(R.id.touxiang);
    }

    private void degnruQQ() {
        //点击QQ按钮
        btnqq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UMAuthListener authListener = new UMAuthListener() {
                    /**
                     * @desc 授权开始的回调
                     * @param platform 平台名称*/
                    @Override
                    public void onStart(SHARE_MEDIA platform) {}
                    /**
                     * @desc 授权成功的回调
                     * @param platform 平台名称
                     * @param action 行为序号，开发者用不上
                     * @param data 用户资料返回*/
                    @Override
                    public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
                        Toast.makeText(MainActivity.this, "成功了", Toast.LENGTH_LONG).show();
                        Toast.makeText(MainActivity.this,"回调的数据："+data,Toast.LENGTH_LONG).show();
                        //获取QQ名称，头像
                        final String name = data.get("name");
                        final String iconurl = data.get("iconurl");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ImageLoader.getInstance().displayImage(iconurl,touxiang);
                            }
                        });
                    }
                    /**
                     * @desc 授权失败的回调
                     * @param platform 平台名称
                     * @param action 行为序号，开发者用不上
                     * @param t 错误原因*/
                    @Override
                    public void onError(SHARE_MEDIA platform, int action, Throwable t) {
                        Toast.makeText(MainActivity.this, "失败：" + t.getMessage(),                                     Toast.LENGTH_LONG).show();
                    }
                    /**
                     * @desc 授权取消的回调
                     * @param platform 平台名称
                     * @param action 行为序号，开发者用不上*/
                    @Override
                    public void onCancel(SHARE_MEDIA platform, int action) {
                        Toast.makeText(MainActivity.this, "取消了", Toast.LENGTH_LONG).show();
                    }
                };
                umShareAPI.getPlatformInfo(MainActivity.this, SHARE_MEDIA.QQ, authListener);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRefresh() {
        page=1;
        requestProduct();
    }

    @Override
    public void onLoadMore() {
        page++;
        requestProduct();
    }

    /**
     * 解绑
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter!=null){
            presenter=null;
        }
    }
}
