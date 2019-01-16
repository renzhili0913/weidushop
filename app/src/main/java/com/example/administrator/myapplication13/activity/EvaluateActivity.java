package com.example.administrator.myapplication13.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication13.Apis;
import com.example.administrator.myapplication13.R;
import com.example.administrator.myapplication13.bean.CommentBean;
import com.example.administrator.myapplication13.bean.HeadPicBean;
import com.example.administrator.myapplication13.bean.OrderShopBean;
import com.example.administrator.myapplication13.bean.ReleaseCircleBean;
import com.example.administrator.myapplication13.bean.UserDataBean;
import com.example.administrator.myapplication13.presenter.IPresenterImpl;
import com.example.administrator.myapplication13.utils.RetrofitManager;
import com.example.administrator.myapplication13.view.IView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EvaluateActivity extends BaseActivty implements IView {

    @BindView(R.id.shopcar_image)
    SimpleDraweeView shopcarImage;
    @BindView(R.id.shopcar_title)
    TextView shopcarTitle;
    @BindView(R.id.shopcar_price)
    TextView shopcarPrice;
    @BindView(R.id.content)
    EditText content;
    @BindView(R.id.synchronization_circle)
    RadioButton synchronizationCircle;
    @BindView(R.id.publish)
    TextView publish;
    @BindView(R.id.but_image)
    ImageView but_image;
    @BindView(R.id.image)
    ImageView image;
    private final String PATH_FILES=Environment.getExternalStorageDirectory()+"/files.png";
    private String orderId;
    private OrderShopBean.OrderListBean.DetailListBean detailListBean;
    private IPresenterImpl iPresenter;
    @Override
    protected void initData() {
        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");
        detailListBean = (OrderShopBean.OrderListBean.DetailListBean) intent.getSerializableExtra("detailListBean");
        String[] split = detailListBean.getCommodityPic().split("\\,");
        Uri uri = Uri.parse(split[0]);
        shopcarImage.setImageURI(uri);
        shopcarTitle.setText(detailListBean.getCommodityName());
        shopcarPrice.setText("¥"+detailListBean.getCommodityPrice());

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        iPresenter=new IPresenterImpl(this);
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_evaluate_view;
    }


    @OnClick({R.id.publish,R.id.but_image})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.publish:
                String trim = content.getText().toString().trim();
                if (trim.equals("")){
                    Toast.makeText(EvaluateActivity.this,"输入评论",Toast.LENGTH_SHORT).show();
                }else{
                    //TODO 评论商品请求  发表圈子
                    if(synchronizationCircle.isChecked()){
                        Map<String,String> params = new HashMap<>();
                        params.put("image",PATH_FILES);
                        params.put("content",trim);
                        params.put("commodityId",String.valueOf(detailListBean.getCommodityId()));
                        iPresenter.getRequeryData(Apis.URL_RELEASE_CIRCLE_POST,params,CommentBean.class);

                    }
                    Map<String,String> params = new HashMap<>();
                    params.put("orderId",orderId);
                    params.put("content",trim);
                    params.put("commodityId",String.valueOf(detailListBean.getCommodityId()));
                    params.put("image",PATH_FILES);
                    iPresenter.postFileRequeryData(Apis.URL_ADD_COMMODITY_COMMENT_LIST_POST,params,CommentBean.class);
                }

                break;
            case R.id.but_image:
                //打开相册
                Intent intent = new Intent(Intent.ACTION_PICK);
                //设置图片格式
                intent.setType("image/*");
                //qid
                startActivityForResult(intent, 300);
                break;
                default:
                    break;
        }

    }

    @Override
    public void showRequeryData(Object o) {
        if (o instanceof CommentBean){
            CommentBean commentBean = (CommentBean) o;
            Toast.makeText(EvaluateActivity.this,commentBean.getMessage(),Toast.LENGTH_SHORT).show();
        }else if(o instanceof ReleaseCircleBean){
            ReleaseCircleBean releaseCircleBean= (ReleaseCircleBean) o;
            Toast.makeText(EvaluateActivity.this,releaseCircleBean.getMessage(),Toast.LENGTH_SHORT).show();
        }else if (o instanceof String){
            String s= (String) o;
            Log.i("TAG",s);
            Toast.makeText(EvaluateActivity.this,s,Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==300&&resultCode==RESULT_OK) {
            //打开裁剪
            Intent intent = new Intent("com.android.camera.action.CROP");
            Uri uri=data.getData();
            //将图片设置给裁剪
            intent.setDataAndType(uri, "image/*");
            //设置是否可裁剪
            intent.putExtra("CROP", true);
            //设置宽高比
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //设置输出
            intent.putExtra("outputX", 100);
            intent.putExtra("outputY", 100);
            //返回data
            intent.putExtra("return-data", true);
            startActivityForResult(intent, 200);
        }
        if (requestCode==200&&resultCode==RESULT_OK) {

            Bitmap bitmap =data.getParcelableExtra("data");
            image.setVisibility(View.VISIBLE);
            image.setImageBitmap(bitmap);
            try {
                RetrofitManager.getmRetrofitManager().saveBitmap(bitmap,PATH_FILES,50);
            } catch (IOException e) {
                e.printStackTrace();
                Log.i("TAG",e.getMessage());
            }

        }
    }
}
