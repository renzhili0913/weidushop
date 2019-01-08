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
import butterknife.OnClick;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    private Context context;
    private List<AddressBean.ResultBean> list;
    private ViewHolder holder;
    public AddressAdapter(Context context) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_activity_address_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        holder=viewHolder;
        viewHolder.realName.setText(list.get(i).getRealName());
        viewHolder.phone.setText(list.get(i).getPhone());
        viewHolder.address.setText(list.get(i).getAddress());
        if (list.get(i).getWhetherDefault()==1){
            viewHolder.whetherDefault.setChecked(true);
        }else{
            viewHolder.whetherDefault.setChecked(false);
        }
        //设置默认选择地址
        viewHolder.whetherDefault.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checked!=null){
                    checked.ononChecked(isChecked,list.get(i).getId());
                }
            }
        });
        //修改地址
        viewHolder.butModify.setOnClickListener(new View.OnClickListener() {
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

    public void setDefaultAddress(boolean bool){
        if (bool){
            holder.whetherDefault.setChecked(true);
        }else{
            holder.whetherDefault.setChecked(false);
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.realName)
        TextView realName;
        @BindView(R.id.phone)
        TextView phone;
        @BindView(R.id.address)
        TextView address;
        @BindView(R.id.whetherDefault)
        RadioButton whetherDefault;
        @BindView(R.id.but_modify)
        TextView butModify;
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
    /**设置收货地址*/
    Checked checked;
    public void setCheckedListener(Checked checked){
        this.checked=checked;
    }
    public interface Checked{
        void ononChecked(boolean falg,int id);
    }
}
