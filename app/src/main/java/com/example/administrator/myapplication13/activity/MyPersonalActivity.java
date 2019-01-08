package com.example.administrator.myapplication13.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication13.Apis;
import com.example.administrator.myapplication13.R;
import com.example.administrator.myapplication13.bean.ModifyNameBean;
import com.example.administrator.myapplication13.bean.ModifyPassBean;
import com.example.administrator.myapplication13.bean.UserDataBean;
import com.example.administrator.myapplication13.presenter.IPresenterImpl;
import com.example.administrator.myapplication13.view.IView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyPersonalActivity extends BaseActivty implements IView {

    private static final int RESULT_NAME_CODE =200 ;
    private IPresenterImpl iPresenter;
    @BindView(R.id.image_hander)
    SimpleDraweeView imageHander;
    @BindView(R.id.my_name)
    TextView myName;
    @BindView(R.id.my_pass)
    TextView myPass;
    private EditText newEditName,newEditPass,oldEditPass;
    private String name,oldPass,newPass;

    @Override
    protected void initData() {
        Intent intent = getIntent();
        UserDataBean userDataBean = (UserDataBean) intent.getSerializableExtra("userDataBean");
        myName.setText(userDataBean.getResult().getNickName());
        Uri uri = Uri.parse(userDataBean.getResult().getHeadPic());
        imageHander.setImageURI(uri);
        myPass.setText(userDataBean.getResult().getPassword());
        setResult(RESULT_NAME_CODE,intent);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        iPresenter = new IPresenterImpl(this);
        //绑定ButterKnife
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_my_personal_view;
    }

    @Override
    public void showRequeryData(Object o) {
       if (o instanceof ModifyNameBean){
            ModifyNameBean modifyNameBean = (ModifyNameBean) o;
            myName.setText(name);
            Toast.makeText(MyPersonalActivity.this,modifyNameBean.getMessage(),Toast.LENGTH_SHORT).show();
        }else if (o instanceof ModifyPassBean){
           ModifyPassBean modifyPassBean = (ModifyPassBean) o;
           myPass.setText(newPass);
           Toast.makeText(MyPersonalActivity.this,modifyPassBean.getMessage(),Toast.LENGTH_SHORT).show();
       }else if(o instanceof String){
           String s = (String) o;
           Toast.makeText(MyPersonalActivity.this,s,Toast.LENGTH_SHORT).show();
       }
    }


    @OnClick({R.id.image_hander, R.id.my_name, R.id.my_pass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_hander:
                break;
            case R.id.my_name:
                //修改昵称
                getNameAlertDialog();
                break;
            case R.id.my_pass:
                //修改密码
                getPassAlertDialog();
                break;
            default:
                break;
        }
    }
    /**
     *弹框修改用户密码
     *@author Administrator
     *@time 2019/1/5 0005 8:43
     */
    private void getPassAlertDialog() {
        View view = View.inflate(this, R.layout.activity_my_personal_pass_alertdialog_item, null);
        newEditPass=view.findViewById(R.id.new_edit_pass);
        oldEditPass=view.findViewById(R.id.old_edit_pass);
        AlertDialog.Builder builder = new AlertDialog.Builder(MyPersonalActivity.this);
        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                oldPass = oldEditPass.getText().toString().trim();
                newPass = newEditPass.getText().toString().trim();
                if (oldPass.equals("")){
                    Toast.makeText(MyPersonalActivity.this,"请输入旧密码",Toast.LENGTH_SHORT).show();
                }else if(newPass.equals("")){
                    Toast.makeText(MyPersonalActivity.this,"请输入新密码",Toast.LENGTH_SHORT).show();
                }else {
                    Map<String, String> params = new HashMap<>();
                    params.put("oldPwd", oldPass);
                    params.put("newPwd", newPass);
                    iPresenter.putRequeryData(Apis.PUT_URL_USER_MODIFY_USER_PWD, params,ModifyPassBean.class);
                }

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    /**
     * 弹框修改昵称
     *
     * @author Administrator
     * @time 2019/1/4 0004 18:52
     */
    private void getNameAlertDialog() {
        View view = View.inflate(this, R.layout.activity_my_personal_name_alertdialog_item, null);
        newEditName=view.findViewById(R.id.new_edit_name);
        AlertDialog.Builder builder = new AlertDialog.Builder(MyPersonalActivity.this);
        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                name = newEditName.getText().toString().trim();
                if (name.equals("")){
                    Toast.makeText(MyPersonalActivity.this,"请输入要修改的名称",Toast.LENGTH_SHORT).show();
                }else{
                    Map<String, String> params = new HashMap<>();
                    params.put("nickName", name);
                    iPresenter.putRequeryData(Apis.PUT_URL_USER_MODIFY_USER_NICK, params,ModifyNameBean.class);
                }

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();

    }

}
