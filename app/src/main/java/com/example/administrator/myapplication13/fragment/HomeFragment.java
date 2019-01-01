package com.example.administrator.myapplication13.fragment;



import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.myapplication13.Apis;
import com.example.administrator.myapplication13.R;
import com.example.administrator.myapplication13.activity.ShowActivity;
import com.example.administrator.myapplication13.adapter.HomeFashionAdapter;
import com.example.administrator.myapplication13.adapter.HomeHeatAdapter;
import com.example.administrator.myapplication13.adapter.HomeQualityAdapter;
import com.example.administrator.myapplication13.bean.BannerBean;
import com.example.administrator.myapplication13.bean.ShopBean;
import com.example.administrator.myapplication13.presenter.IPresenterImpl;
import com.example.administrator.myapplication13.view.IView;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomeFragment extends BaseFragment implements IView {
    @BindView(R.id.image_classification)
    ImageView imageClassification;
    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.image_search)
    ImageButton imageSearch;
    @BindView(R.id.home_viewpager_image)
    XBanner homeViewpagerImage;
    @BindView(R.id.but_heat_new_products)
    ImageView butHeatNewProducts;
    @BindView(R.id.heat_new_products_view)
    RecyclerView heatNewProductsView;
    @BindView(R.id.but_magic_fashion)
    ImageView butMagicFashion;
    @BindView(R.id.magic_fashion_view)
    RecyclerView magicFashionView;
    @BindView(R.id.but_quality_life)
    ImageView butQualityLife;
    @BindView(R.id.quality_life_view)
    RecyclerView qualityLifeView;
    private IPresenterImpl iPresenter;
    Unbinder unbinder;
    private HomeHeatAdapter homeHeatAdapter;
    private HomeFashionAdapter homeFashionAdapter;
    private HomeQualityAdapter homeQualityAdapter;

    @Override
    protected void initData() {
        //请求轮播图数据
        iPresenter.getRequeryData(Apis.GET_URL_USER_FIND_HOME_BANNER,BannerBean.class);
        //请求商品列表
        iPresenter.getRequeryData(Apis.GET_URL_USER_FIND_HOME_SHOP,ShopBean.class);
    }

    @Override
    protected void initView(View view) {
        //创建IPresenterImpl
        iPresenter = new IPresenterImpl(this);
        //绑定ButterKnife
        unbinder = ButterKnife.bind(this, view);
        getbanner();
        getHeatNewProductsView();
        getMagicFashionView();
        getQualityLifeView();
    }
    /**
     *轮播图数据
     *@author Administrator
     *@time 2019/1/1 0001 14:59
     */
    private void getbanner() {
       homeViewpagerImage.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                BannerBean.ResultBean resultBean= (BannerBean.ResultBean) model;
                Glide.with(getActivity()).load(resultBean.getImageUrl()).into((ImageView) view);
            }
        });
        homeViewpagerImage.setPageChangeDuration(0);
    }

    /**
     *获取品质生活的布局
     *@author Administrator
     *@time 2018/12/31 0031 16:04
     */
    private void getQualityLifeView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        qualityLifeView.setLayoutManager(gridLayoutManager);
        //创建适配器
        homeQualityAdapter = new HomeQualityAdapter(getActivity());
        qualityLifeView.setAdapter(homeQualityAdapter);
    }

    /**
     *获取魔方时尚的布局
     *@author Administrator
     *@time 2018/12/31 0031 15:22
     */
    private void getMagicFashionView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        magicFashionView.setLayoutManager(linearLayoutManager);
        //创建热销商品适配器
        homeFashionAdapter = new HomeFashionAdapter(getActivity());
        magicFashionView.setAdapter(homeFashionAdapter);
    }

    /**
     *获取热销商品的布局
     *@author Administrator
     *@time 2018/12/31 0031 14:18
     */
    private void getHeatNewProductsView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        heatNewProductsView.setLayoutManager(linearLayoutManager);
        //创建热销商品适配器
        homeHeatAdapter = new HomeHeatAdapter(getActivity());
        heatNewProductsView.setAdapter(homeHeatAdapter);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home_item;
    }

    @Override
    public void showRequeryData(Object o) {
        if (o instanceof ShopBean){
            ShopBean shopBean = (ShopBean) o;
            if (shopBean==null||!shopBean.isSuccess()){
                ((ShowActivity)getActivity()).getToast(shopBean.getMessage());
            }else{
                homeHeatAdapter.setList(shopBean.getResult().getRxxp().get(0).getCommodityList());
                homeFashionAdapter.setList(shopBean.getResult().getMlss().get(0).getCommodityList());
                homeQualityAdapter.setList(shopBean.getResult().getPzsh().get(0).getCommodityList());
            }
        }else if(o instanceof BannerBean){
            BannerBean bannerBean = (BannerBean) o;
            if (bannerBean==null||!bannerBean.isSuccess()){
                ((ShowActivity)getActivity()).getToast(bannerBean.getMessage());
            }else{
               homeViewpagerImage.setData(bannerBean.getResult(),null);
            }
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder!=null){
            unbinder.unbind();
        }
        iPresenter.onDetach();
    }
    @OnClick({R.id.image_classification, R.id.image_search,R.id.but_heat_new_products, R.id.but_magic_fashion, R.id.but_quality_life})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_classification:

                break;
            case R.id.image_search:
                break;
            case R.id.but_heat_new_products:
                break;
            case R.id.but_magic_fashion:
                break;
            case R.id.but_quality_life:
                break;
                default:
                    break;
        }
    }
}
