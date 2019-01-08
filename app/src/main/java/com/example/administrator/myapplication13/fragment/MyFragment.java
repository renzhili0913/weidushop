package com.example.administrator.myapplication13.fragment;



import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication13.Apis;
import com.example.administrator.myapplication13.R;
import com.example.administrator.myapplication13.activity.LoginActivity;
import com.example.administrator.myapplication13.activity.MainActivity;
import com.example.administrator.myapplication13.activity.MyAddressActivity;
import com.example.administrator.myapplication13.activity.MyCircleActivity;
import com.example.administrator.myapplication13.activity.MyPersonalActivity;
import com.example.administrator.myapplication13.activity.MyTracksActivity;
import com.example.administrator.myapplication13.activity.MyWalletActivity;
import com.example.administrator.myapplication13.bean.HeadPicBean;
import com.example.administrator.myapplication13.bean.UserDataBean;
import com.example.administrator.myapplication13.presenter.IPresenterImpl;
import com.example.administrator.myapplication13.view.IView;
import com.example.administrator.myapplication13.view.PhotoPopupWindows;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

public class MyFragment extends BaseFragment implements IView {
    private static final int REQUEST_CODE =100 ;
    private static final int RESULT_NAME_CODE =200 ;
    @BindView(R.id.my_name)
    TextView myName;
    @BindView(R.id.my_personal_data)
    LinearLayout myPersonalData;
    @BindView(R.id.my_circle)
    LinearLayout myCircle;
    @BindView(R.id.my_tracks)
    LinearLayout myTracks;
    @BindView(R.id.my_wallet)
    LinearLayout myWallet;
    @BindView(R.id.my_address)
    LinearLayout myAddress;
    @BindView(R.id.my_image_hander)
    SimpleDraweeView myImageHander;
    @BindView(R.id.my_backgroud_image_layout)
    ConstraintLayout myBackgroudImageLayout;
    Unbinder unbinder;
    private IPresenterImpl iPresenter;
    private String headPic;
    private String nickName;
    private UserDataBean userDataBean;
    private PopupWindow popupWindow;
    private String path=Environment.getExternalStorageDirectory()+"/image.png";
    @Override
    protected void initData() {
        //请求个人资料
        iPresenter.getRequeryData(Apis.GET_URL_USER_GET_USER_BYID,UserDataBean.class);
    }

    @Override
    protected void initView(View view) {
        iPresenter=new IPresenterImpl(this);
        //绑定ButterKnife
        unbinder = ButterKnife.bind(this, view);

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_my_item;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder!=null) {
            unbinder.unbind();
        }
    }

    @OnClick({R.id.my_personal_data, R.id.my_circle, R.id.my_tracks, R.id.my_wallet, R.id.my_address, R.id.my_image_hander, R.id.my_backgroud_image_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_personal_data:
                Intent intent = new Intent(getActivity(),MyPersonalActivity.class);
                intent.putExtra("userDataBean",userDataBean);
                startActivityForResult(intent,REQUEST_CODE);
                break;
            case R.id.my_circle:
                getIntents(MyCircleActivity.class);
                break;
            case R.id.my_tracks:
                getIntents(MyTracksActivity.class);
                break;
            case R.id.my_wallet:
                getIntents(MyWalletActivity.class);
                break;
            case R.id.my_address:
                getIntents(MyAddressActivity.class);
                break;
           case R.id.my_image_hander:
               //修改头像,创建popupwindow
               getPopupWindow();
               popupWindow.showAtLocation(getActivity().findViewById(R.id.my_backgroud_image_layout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.my_backgroud_image_layout:
                break;
                default:
                    break;
        }
    }

    /**
     *弹框修改头像
     *@author Administrator
     *@time 2019/1/5 0005 9:36
     */
    private void getPopupWindow() {
        View view =View.inflate(getActivity(),R.layout.fragment_my_personal_hanger_image_popupwindow_item,null);
        TextView open_camera = view.findViewById(R.id.open_camera);
        TextView open_album = view.findViewById(R.id.open_album);
        TextView cancal = view.findViewById(R.id.cancal);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
       //设置焦点
        popupWindow.setFocusable(true);
        //设置可触摸
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        open_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开系统相机
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //把图片存入sd卡
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path)));
                //启动
                startActivityForResult(intent, 400);
                popupWindow.dismiss();
            }
        });
        open_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开相册
                Intent intent = new Intent(Intent.ACTION_PICK);
                //设置图片格式
                intent.setType("image/*");
                //qid
                startActivityForResult(intent, 300);
                popupWindow.dismiss();
            }
        });
        cancal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

    }

    /**
     *跳转的方法
     *@author Administrator
     *@time 2018/12/29 0029 14:59
     */
    public void getIntents(Class activity){
        Intent intent = new Intent(getActivity(),activity);
        startActivity(intent);
    }

    @Override
    public void showRequeryData(Object o) {
        if (o instanceof UserDataBean){
            userDataBean = (UserDataBean) o;
            if (userDataBean ==null||!userDataBean.isSuccess()){
                Toast.makeText(getActivity(), userDataBean.getMessage(),Toast.LENGTH_SHORT).show();
            }else{
                nickName = userDataBean.getResult().getNickName();
                headPic = userDataBean.getResult().getHeadPic();
                myName.setText(nickName);
                Uri uri = Uri.parse(headPic);
                myImageHander.setImageURI(uri);
            }
        }else if (o instanceof HeadPicBean){
            HeadPicBean headPicBean = (HeadPicBean) o;
            Uri uri = Uri.parse(headPicBean.getHeadPath());
            myImageHander.setImageURI(uri);

        }else if (o instanceof String){
            String s = (String) o;
            Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE&&resultCode==RESULT_NAME_CODE){
            iPresenter.getRequeryData(Apis.GET_URL_USER_GET_USER_BYID,UserDataBean.class);
        }
        if (requestCode==400&&resultCode==RESULT_OK) {
            //打开裁剪
            Intent intent = new Intent("com.android.camera.action.CROP");
            //将图片设置给裁剪
            intent.setDataAndType(Uri.fromFile(new File(path)), "image/*");
            //设置是否支持裁剪
            intent.putExtra("CROP", true);
            //设置宽高比
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //设置输出后图片大小
            intent.putExtra("outputX", 100);
            intent.putExtra("outputY", 100);
            //返回到data
            intent.putExtra("return-data", true);
            //启动
            startActivityForResult(intent, 200);

        }
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

            Map<String,String> params = new HashMap<>();
            //TODO  头像路径
            params.put("image",path);
            iPresenter.getRequeryData(Apis.POST_URL_USER_MODIFY_HEAD_PIC,params,HeadPicBean.class);
            //myImageHander.setImageBitmap(bitmap);
        }
    }
   /* File file = new File(path);
    public void saveBitmapFile(Bitmap bitmap) {
        //将要保存图片的路径

        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/


}
