package com.example.administrator.myapplication13.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication13.Apis;
import com.example.administrator.myapplication13.R;
import com.example.administrator.myapplication13.bean.LoginBean;
import com.example.administrator.myapplication13.presenter.IPresenterImpl;
import com.example.administrator.myapplication13.view.IView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivty implements IView {
    private static final int REQUEST_CODE = 100;
    private static final int RESULT_CODE = 200;
    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.edit_pass)
    EditText editPass;
    @BindView(R.id.image_eye)
    ImageButton imageEye;
    @BindView(R.id.remember_pass)
    CheckBox rememberPass;
    @BindView(R.id.text_register)
    TextView textRegister;
    @BindView(R.id.but_login)
    Button butLogin;
    private IPresenterImpl iPresenter;
    private String name;
    private String pass;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void initData() {

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initView(Bundle savedInstanceState) {
        //创建IPresenterImpl
        iPresenter = new IPresenterImpl(this);
        //绑定ButterKnife
        ButterKnife.bind(this);
        //创建SharedPreferences
        preferences = getSharedPreferences("text",MODE_PRIVATE);
        editor=preferences.edit();
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
        //判断是否记住密码
        boolean ischeck = preferences.getBoolean("ischeck", false);
        if (ischeck){
            rememberPass.setChecked(true);
            String names = preferences.getString("name", null);
            String passs = preferences.getString("pass", null);
            editName.setText(names);
            editPass.setText(passs);
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void showRequeryData(Object o) {
        if (o instanceof LoginBean){
            LoginBean loginBean = (LoginBean) o;
            if (loginBean==null||!loginBean.isSuccess()){
                getToast(loginBean.getMessage());
            }else{
                if (rememberPass.isChecked()){
                    editor.putString("name",name);
                    editor.putString("pass",pass);
                    editor.putBoolean("ischeck",true);
                    editor.commit();
                }else{
                    editor.clear();
                    editor.commit();
                }
                inEventBus(loginBean);
                getIntents();
            }

        }
    }
    /**
     *通过eventbus发送数据
     *@author Administrator
     *@time 2018/12/30 0030 8:27
     */
    private void inEventBus(LoginBean loginBean) {
        LoginBean.ResultBean resultBean = new LoginBean.ResultBean();
        resultBean.setHeadPic(loginBean.getResult().getHeadPic());
        resultBean.setNickName(loginBean.getResult().getNickName());
        resultBean.setPhone(loginBean.getResult().getPhone());
        resultBean.setSessionId(loginBean.getResult().getSessionId());
        resultBean.setUserId(loginBean.getResult().getUserId());
        resultBean.setSex(loginBean.getResult().getSex());
        EventBus.getDefault().postSticky(resultBean);
    }

    @OnClick({R.id.but_login,R.id.text_register})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.but_login:
                name = editName.getText().toString().trim();
                pass = editPass.getText().toString().trim();
                if (name.equals("")||pass.equals("")){
                    getToast("手机号和密码不能为空");
                }else{
                    Map<String,String> params  = new HashMap<>();
                    params.put("phone",name);
                    params.put("pwd",pass);
                    iPresenter.getRequeryData(Apis.POST_URL_USER_LOGIN,params,LoginBean.class);
                }
                break;
            case R.id.text_register:
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
                break;
                default:
                    break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onDetach();
    }
    /**吐司的方法*/
    public void getToast(String str){
        Toast.makeText(MainActivity.this,str,Toast.LENGTH_SHORT).show();
    }
    /**跳转到首页的方法*/
    public void getIntents(){
        Intent intent = new Intent(MainActivity.this,ShowActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE&&resultCode==RESULT_CODE){
            String name = data.getStringExtra("name");
            String pass = data.getStringExtra("pass");
            editName.setText(name);
            editPass.setText(pass);
        }
    }
}
