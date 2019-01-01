package com.example.administrator.myapplication13.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication13.Apis;
import com.example.administrator.myapplication13.R;
import com.example.administrator.myapplication13.bean.RegisterBean;
import com.example.administrator.myapplication13.presenter.IPresenterImpl;
import com.example.administrator.myapplication13.view.IView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivty implements IView {
    private static final int RESULT_CODE = 200;
    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.edit_yan)
    EditText editYan;
    @BindView(R.id.but_yan)
    TextView butYan;
    @BindView(R.id.edit_pass)
    EditText editPass;
    @BindView(R.id.image_eye)
    ImageButton imageEye;
    @BindView(R.id.text_register)
    TextView textRegister;
    @BindView(R.id.but_login)
    Button butLogin;
    private IPresenterImpl iPresenter;
    private String pass;
    private String name;
    @Override
    protected void initData() {

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initView(Bundle savedInstanceState) {
        iPresenter = new IPresenterImpl(this);
        //绑定ButterKnife
        ButterKnife.bind(this);
        //密码显示与隐藏
        imageEye.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    editPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    editPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                return false;
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login_item;
    }

    @Override
    public void showRequeryData(Object o) {
        if (o instanceof RegisterBean){
            RegisterBean registerBean = (RegisterBean) o;
            if (registerBean==null||!registerBean.isSuccess()){
                getToast(registerBean.getMessage());
            }else{
                getToast(registerBean.getMessage());
                //回传跳转
                Intent intent = new Intent();
                intent.putExtra("name",name);
                intent.putExtra("pass",pass);
                setResult(RESULT_CODE,intent);
                finish();
            }

        }
    }
    @OnClick({R.id.but_yan, R.id.text_register, R.id.but_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.but_yan:
                break;
            case R.id.text_register:
                getIntents();
                break;
            case R.id.but_login:
                name = editName.getText().toString().trim();
                pass = editPass.getText().toString().trim();
                //判断输入是否为空
                if (name.equals("")||pass.equals("")){
                    getToast("手机号和密码不能为空");
                }else{
                    Map<String,String> params  = new HashMap<>();
                    params.put("phone",name);
                    params.put("pwd",pass);
                    iPresenter.getRequeryData(Apis.POST_URL_USER_REGISTER,params,RegisterBean.class);

                }
                break;
                default:
                    break;
        }
    }
    /**
     *吐司的方法
     *@author Administrator
     *@time 2018/12/29 0029 15:03
     */
    public void getToast(String str){
        Toast.makeText(LoginActivity.this,str,Toast.LENGTH_SHORT).show();
    }
    /**
     *跳转的方法
     *@author Administrator
     *@time 2018/12/29 0029 14:59
     */
    public void getIntents(){
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onDetach();
    }
}
