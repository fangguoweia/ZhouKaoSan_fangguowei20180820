package com.bwei.ZhouKaoSan_fangguowei20180820.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwei.ZhouKaoSan_fangguowei20180820.MainActivity;
import com.bwei.ZhouKaoSan_fangguowei20180820.ProductBean;
import com.bwei.ZhouKaoSan_fangguowei20180820.R;

import java.util.List;

/**
 * Created by 房国伟 on 2018/8/20.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private Context context;
    private List<ProductBean.Producet> list;

    public ProductAdapter(Context context, List<ProductBean.Producet> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ayout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    //展示数据
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ProductBean.Producet producet = list.get(position);
        String[] imageUrls = producet.images.split("\\|");
        if (imageUrls != null && imageUrls.length >0){
            Glide.with(context).load(imageUrls[0]).into(holder.tu);
        }
        holder.zi.setText(producet.title);
    }

    @Override
    public int getItemCount() {
        return list.size() == 0 ? 0 :list.size();
    }
    public void loadData(List<ProductBean.Producet> data){
        if (this.list!=null){
            this.list.addAll(data);
            notifyDataSetChanged();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private  ImageView tu;
        private  TextView zi;

        public MyViewHolder(View itemView) {
            super(itemView);
            tu = itemView.findViewById(R.id.tu);
            zi = itemView.findViewById(R.id.zi);
        }
    }
}
