package com.example.administrator.myapplication13.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.administrator.myapplication13.R;
import com.example.administrator.myapplication13.fragment.CartFragment;
import com.example.administrator.myapplication13.fragment.CircleFragment;
import com.example.administrator.myapplication13.fragment.HomeFragment;
import com.example.administrator.myapplication13.fragment.MyFragment;
import com.example.administrator.myapplication13.fragment.OrderFragment;
import com.example.administrator.myapplication13.presenter.IPresenterImpl;
import com.example.administrator.myapplication13.view.IView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowActivity extends BaseActivty {
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.image_background)
    ImageView imageBackground;
    @BindView(R.id.but_home)
    RadioButton butHome;
    @BindView(R.id.but_circle)
    RadioButton butCircle;
    @BindView(R.id.but_Cart)
    RadioButton butCart;
    @BindView(R.id.but_Order)
    RadioButton butOrder;
    @BindView(R.id.but_my)
    RadioButton butMy;
    @BindView(R.id.radiogroup)
    RadioGroup radiogroup;
    private List<Fragment> list;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        list=new ArrayList<>();
        list.add(new HomeFragment());
        list.add(new CircleFragment());
        list.add(new CartFragment());
        list.add(new OrderFragment());
        list.add(new MyFragment());
        //创建适配器
        viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return list.get(i);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }
            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        radiogroup.check(R.id.but_home);
                        break;
                    case 1:
                        radiogroup.check(R.id.but_circle);
                        break;
                    case 2:
                        radiogroup.check(R.id.but_Cart);
                        break;
                    case 3:
                        radiogroup.check(R.id.but_Order);
                        break;
                    case 4:
                        radiogroup.check(R.id.but_my);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.but_home:
                        viewpager.setCurrentItem(0);
                        break;
                    case R.id.but_circle:
                        viewpager.setCurrentItem(1);
                        break;
                    case R.id.but_Cart:
                        viewpager.setCurrentItem(2);
                        break;
                    case R.id.but_Order:
                        viewpager.setCurrentItem(3);
                        break;
                    case R.id.but_my:
                        viewpager.setCurrentItem(4);
                        break;
                }
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_show_item;
    }
    /**吐司的方法*/
    public void getToast(String str){
        Toast.makeText(ShowActivity.this,str,Toast.LENGTH_SHORT).show();
    }
}
