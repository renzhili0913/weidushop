package com.example.administrator.myapplication13.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.myapplication13.R;
import com.example.administrator.myapplication13.bean.CircleBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CircleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<CircleBean.ResultBean> list;
    private MyViewHolder mViewHolder;

    public CircleAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    /**
     * 下拉刷新
     *
     * @author Administrator
     * @time 2018/12/29 0029 20:40
     */
    public void setList(List<CircleBean.ResultBean> data) {
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
    public void addList(List<CircleBean.ResultBean> data) {
        if (data != null) {
            list.addAll(data);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.circle_recycler_item_view, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        ButterKnife.bind(myViewHolder,view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        mViewHolder = (MyViewHolder) viewHolder;
       //设置头像
        Uri uri = Uri.parse(list.get(i).getHeadPic());
        mViewHolder.imageHander.setImageURI(uri);
        //转换为时间格式并赋值
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
                new java.util.Date(list.get(i).getCreateTime()));
        mViewHolder.createTime.setText(date);
        //设置名字
        mViewHolder.nickName.setText(list.get(i).getNickName());
        //设置内容
        mViewHolder.content.setText(list.get(i).getContent());
        Glide.with(context).load(list.get(i).getImage()).into(mViewHolder.image);
        mViewHolder.text_num.setText(list.get(i).getGreatNum()+"");
      if (list.get(i).getWhetherGreat()==1){
            mViewHolder.fabulous.setBackgroundResource(R.mipmap.common_btn_prise_s);
        }else{
          mViewHolder.fabulous.setBackgroundResource(R.mipmap.common_btn_prise_n);
      }
        mViewHolder.fabulous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click!=null) {
                    click.onClick(list.get(i).getWhetherGreat(),i,list.get(i).getId());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    /**
     *设置点赞
     *@author Administrator
     *@time 2019/1/1 0001 18:14
     */
    public void addWhetherGreat(int position) {
        list.get(position).setWhetherGreat(1);
        list.get(position).setGreatNum(list.get(position).getGreatNum()+1);
        notifyDataSetChanged();
    }
    /**
     *取消点赞
     *@author Administrator
     *@time 2019/1/2 0002 19:32
     */
    public void cancleWhetherGreat(int position) {
        list.get(position).setWhetherGreat(2);
        list.get(position).setGreatNum(list.get(position).getGreatNum()-1);
        notifyDataSetChanged();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_hander)
        SimpleDraweeView imageHander;
        @BindView(R.id.nickName)
        TextView nickName;
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
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    /**
     *点击监听
     *@author Administrator
     *@time 2018/12/30 0030 14:15
     */
    Click click;
    public void setOnClickListener(Click click){
        this.click=click;
    }
    public interface Click{
        void onClick(int i,int position,int circleId);
    }
}
