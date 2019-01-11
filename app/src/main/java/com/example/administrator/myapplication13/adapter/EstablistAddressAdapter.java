package com.example.administrator.myapplication13.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.administrator.myapplication13.R;
import com.example.administrator.myapplication13.bean.AddressBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EstablistAddressAdapter extends RecyclerView.Adapter<EstablistAddressAdapter.ViewHolder> {

    private Context context;
    private List<AddressBean.ResultBean> list;
    private ViewHolder holder;
    public EstablistAddressAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setList(List<AddressBean.ResultBean> data) {
        list.clear();
        if (data != null) {
            list.addAll(data);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_activity_establist_address_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        holder=viewHolder;
        viewHolder.realName.setText(list.get(i).getRealName());
        viewHolder.phone.setText(list.get(i).getPhone());
        viewHolder.address.setText(list.get(i).getAddress());
        //选择地址
        viewHolder.butDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (updata!=null){
                    updata.onUpdata(list.get(i));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.realName)
        TextView realName;
        @BindView(R.id.phone)
        TextView phone;
        @BindView(R.id.address)
        TextView address;
        @BindView(R.id.but_delete)
        TextView butDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    Updata updata;
    public void setOnUpdataListener(Updata updata){
        this.updata=updata;
    }
    public interface Updata{
        void onUpdata(AddressBean.ResultBean resultBean);
    }

}
