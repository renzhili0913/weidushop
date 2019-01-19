package com.example.administrator.myapplication13.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication13.Apis;
import com.example.administrator.myapplication13.R;
import com.example.administrator.myapplication13.adapter.imagesadapter.GridViewAdapter;
import com.example.administrator.myapplication13.bean.CommentBean;
import com.example.administrator.myapplication13.bean.HeadPicBean;
import com.example.administrator.myapplication13.bean.OrderShopBean;
import com.example.administrator.myapplication13.bean.ReleaseCircleBean;
import com.example.administrator.myapplication13.bean.UserDataBean;
import com.example.administrator.myapplication13.iamgesview.MainConstant;
import com.example.administrator.myapplication13.iamgesview.PictureSelectorConfig;
import com.example.administrator.myapplication13.iamgesview.PlusImageActivity;
import com.example.administrator.myapplication13.presenter.IPresenterImpl;
import com.example.administrator.myapplication13.utils.RetrofitManager;
import com.example.administrator.myapplication13.view.IView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    @BindView(R.id.gridView)
    GridView gridView;

    private static final String TAG = "EvaluateActivity";
    private Context mContext;
    /**上传的图片凭证的数据源*/
    private ArrayList<String> mPicList = new ArrayList<>();
    /**展示上传的图片的适配器*/
    private GridViewAdapter mGridViewAddImgAdapter;
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
        mContext = this;
        initGridView();
    }
    //初始化展示上传图片的GridView
    private void initGridView() {
        mGridViewAddImgAdapter = new GridViewAdapter(mContext, mPicList);
        gridView.setAdapter(mGridViewAddImgAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == parent.getChildCount() - 1) {
                    //如果“增加按钮形状的”图片的位置是最后一张，且添加了的图片的数量不超过9张，才能点击
                    if (mPicList.size() == MainConstant.MAX_SELECT_PIC_NUM) {
                        //最多添加9张图片
                        viewPluImg(position);
                    } else {
                        //添加凭证图片
                        selectPic(MainConstant.MAX_SELECT_PIC_NUM - mPicList.size());
                    }
                } else {
                    viewPluImg(position);
                }
            }
        });
    }
    //查看大图
    private void viewPluImg(int position) {
        Intent intent = new Intent(mContext, PlusImageActivity.class);
        intent.putStringArrayListExtra(MainConstant.IMG_LIST, mPicList);
        intent.putExtra(MainConstant.POSITION, position);
        startActivityForResult(intent, MainConstant.REQUEST_CODE_MAIN);
    }
    /**
     * 打开相册或者照相机选择凭证图片，最多5张
     *
     * @param maxTotal 最多选择的图片的数量
     */
    private void selectPic(int maxTotal) {
        PictureSelectorConfig.initMultiConfig(this, maxTotal);
    }
    // 处理选择的照片的地址
    private void refreshAdapter(List<LocalMedia> picList) {
        for (LocalMedia localMedia : picList) {
            //被压缩后的图片路径
            if (localMedia.isCompressed()) {
                //压缩后的图片路径
                String compressPath = localMedia.getCompressPath();
                //把图片添加到将要上传的图片数组中
                mPicList.add(compressPath);
                mGridViewAddImgAdapter.notifyDataSetChanged();
            }
        }
    }
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_evaluate_view;
    }


    @OnClick({R.id.publish})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.publish:
                String trim = content.getText().toString().trim();
                if (trim.equals("")){
                    Toast.makeText(EvaluateActivity.this,"输入评论",Toast.LENGTH_SHORT).show();
                }else{
                    //TODO 评论商品请求  发表圈子
                    if(synchronizationCircle.isChecked()){
                        Map<String,Object> params = new HashMap<>();
                        params.put("image",mPicList);
                        params.put("content",trim);
                        params.put("commodityId",String.valueOf(detailListBean.getCommodityId()));
                        iPresenter.postFileMoreRequeryData(Apis.URL_RELEASE_CIRCLE_POST,params,ReleaseCircleBean.class);
                    }
                    Map<String,Object> params = new HashMap<>();
                    params.put("orderId",orderId);
                    params.put("content",trim);
                    params.put("commodityId",String.valueOf(detailListBean.getCommodityId()));
                    params.put("image",mPicList);
                    iPresenter.postFileMoreRequeryData(Apis.URL_ADD_COMMODITY_COMMENT_LIST_POST,params,CommentBean.class);

                }

                break;

                default:
                    break;
        }

    }

    @Override
    public void showRequeryData(Object o) {
        if (o instanceof CommentBean){
            CommentBean commentBean = (CommentBean) o;
            if (commentBean.isSuccess()&&commentBean!=null){
                finish();
            }
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    refreshAdapter(PictureSelector.obtainMultipleResult(data));
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    break;
            }
        }
        if (requestCode == MainConstant.REQUEST_CODE_MAIN && resultCode == MainConstant.RESULT_CODE_VIEW_IMG) {
            //查看大图页面删除了图片
            //要删除的图片的集合
            ArrayList<String> toDeletePicList = data.getStringArrayListExtra(MainConstant.IMG_LIST);
            mPicList.clear();
            mPicList.addAll(toDeletePicList);
            mGridViewAddImgAdapter.notifyDataSetChanged();
        }
    }

}
