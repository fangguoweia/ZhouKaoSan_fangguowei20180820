package com.bwei.ZhouKaoSan_fangguowei20180820;

import java.util.List;

/**
 * Created by 房国伟 on 2018/8/20.
 */
public class ProductBean {

    public String msg;
    public String code;
    public List<Producet> data;
    public class Producet{
        public String title;
        public String images;
        public String pid;
    }

}
