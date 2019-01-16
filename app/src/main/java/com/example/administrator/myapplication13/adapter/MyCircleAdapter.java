package com.example.administrator.myapplication13.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.myapplication13.R;
import com.example.administrator.myapplication13.bean.CircleBean;
import com.example.administrator.myapplication13.bean.MyCircleBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCircleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<MyCircleBean.ResultBean> list;
    private MyViewHolder mViewHolder;
    private boolean falg;
    public MyCircleAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    /**
     * 下拉刷新
     *
     * @author Administrator
     * @time 2018/12/29 0029 20:40
     */
    public void setList(List<MyCircleBean.ResultBean> data) {
        list.clear();
        if (data != null) {
            list.addAll(data);
        }
        notifyDataSetChanged();
    }

    /**
     * 上拉加载
     *
     * @author Administrator
     * @time 2018/12/29 0029 20:40
     */
    public void addList(List<MyCircleBean.ResultBean> data) {
        if (data != null) {
            list.addAll(data);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_my_circle_recycler_item_view, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        ButterKnife.bind(myViewHolder,view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        mViewHolder = (MyViewHolder) viewHolder;
        //转换为时间格式并赋值
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
                new java.util.Date(list.get(i).getCreateTime()));
        mViewHolder.createTime.setText(date);
        //设置内容
        mViewHolder.content.setText(list.get(i).getContent());
        Glide.with(context).load(list.get(i).getImage()).into(mViewHolder.image);
        mViewHolder.text_num.setText(list.get(i).getGreatNum()+"");
        //判断是否有点赞
          if (list.get(i).getGreatNum()>=1){
                mViewHolder.fabulous.setBackgroundResource(R.mipmap.common_btn_prise_s);
            }else{
              mViewHolder.fabulous.setBackgroundResource(R.mipmap.common_btn_prise_n);
          }
          //判断是否显示复选框
          if (falg){
              mViewHolder.check_box.setVisibility(View.VISIBLE);
              //判断复学框是否选择
              if (list.get(i).isSelect){
                  mViewHolder.check_box.setChecked(true);
              }else{
                  mViewHolder.check_box.setChecked(false);
              }
          }else{
              mViewHolder.check_box.setVisibility(View.GONE);
          }

        //选择状态
        mViewHolder.check_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    list.get(i).setSelect(true);
                   // notifyDataSetChanged();

                }
            }
        });
    }
    //存入复选框显示与隐藏的状态值
    public void setCheckbox(boolean falg){
       this.falg=falg;
        notifyDataSetChanged();
    }

    public List<MyCircleBean.ResultBean> getList() {
        return list;
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.createTime)
        TextView createTime;
        @BindView(R.id.content)
        TextView content;
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.fabulous)
        ImageView fabulous;
        @BindView(R.id.text_num)
        TextView text_num;
        @BindView(R.id.check_box)
        CheckBox check_box;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }



}
