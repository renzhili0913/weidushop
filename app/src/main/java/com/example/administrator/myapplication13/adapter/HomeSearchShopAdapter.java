package com.example.administrator.myapplication13.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.myapplication13.R;
import com.example.administrator.myapplication13.bean.SearchShopBean;
import com.example.administrator.myapplication13.bean.TwoCategoryShopBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeSearchShopAdapter extends RecyclerView.Adapter<HomeSearchShopAdapter.ViewHolder> {
    private Context context;
    private List<SearchShopBean.ResultBean> list;

    public HomeSearchShopAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setList(List<SearchShopBean.ResultBean> data) {
        list.clear();
        if (data != null) {
            list.addAll(data);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_fragment_home_two_category_shop_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.commodityName.setText(list.get(i).getCommodityName());
        viewHolder.price.setText("¥"+list.get(i).getPrice());
        viewHolder.saleNum.setText("已售"+list.get(i).getSaleNum()+"件");
        Uri uri = Uri.parse(list.get(i).getMasterPic());
        viewHolder.iamge.setImageURI(uri);
      viewHolder.constrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click!=null){
                    click.onClick(list.get(i).getCommodityId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iamge)
        SimpleDraweeView iamge;
        @BindView(R.id.commodityName)
        TextView commodityName;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.saleNum)
        TextView saleNum;
        @BindView(R.id.constrain)
        ConstraintLayout constrain;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    Click click;

    public void setOnClickListener(Click click) {
        this.click = click;
    }

    public interface Click {
        void onClick(int commodityId);
    }
}
