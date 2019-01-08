package com.example.administrator.myapplication13.fragment;



import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.myapplication13.Apis;
import com.example.administrator.myapplication13.R;
import com.example.administrator.myapplication13.activity.ShowActivity;
import com.example.administrator.myapplication13.adapter.HomeFashionAdapter;
import com.example.administrator.myapplication13.adapter.HomeHeatAdapter;
import com.example.administrator.myapplication13.adapter.HomeQualityAdapter;
import com.example.administrator.myapplication13.adapter.HomeSearchShopAdapter;
import com.example.administrator.myapplication13.adapter.LabelSearchShopAdapter;
import com.example.administrator.myapplication13.adapter.OneCategoryAdapter;
import com.example.administrator.myapplication13.adapter.TwoCategoryAdapter;
import com.example.administrator.myapplication13.adapter.TwoCategoryShopAdapter;
import com.example.administrator.myapplication13.bean.BannerBean;
import com.example.administrator.myapplication13.bean.CartBean;
import com.example.administrator.myapplication13.bean.LabelStopBean;
import com.example.administrator.myapplication13.bean.LoginBean;
import com.example.administrator.myapplication13.bean.OneCategoryBean;
import com.example.administrator.myapplication13.bean.SearchShopBean;
import com.example.administrator.myapplication13.bean.ShopBean;
import com.example.administrator.myapplication13.bean.ShopDetailsBean;
import com.example.administrator.myapplication13.bean.ShopResultBean;
import com.example.administrator.myapplication13.bean.SyncShopBean;
import com.example.administrator.myapplication13.bean.TwoCategoryBean;
import com.example.administrator.myapplication13.bean.TwoCategoryShopBean;
import com.example.administrator.myapplication13.presenter.IPresenterImpl;
import com.example.administrator.myapplication13.utils.EditTextUtils;
import com.example.administrator.myapplication13.view.IView;
import com.stx.xhb.xbanner.XBanner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @BindView(R.id.category_one_item)
    RecyclerView category_one_item;
    @BindView(R.id.category_two_item)
    RecyclerView category_two_item;
    @BindView(R.id.category_two_shop)
    RecyclerView category_two_shop;
    @BindView(R.id.scrollview)
    ScrollView scrollView;
    @BindView(R.id.backgroud_image)
    ImageView backgroud_image;
    @BindView(R.id.backgroud_text)
    TextView backgroud_text;
    @BindView(R.id.products_background_image)
    ImageView products_background_image;
    @BindView(R.id.products_text)
    TextView products_text;

    @BindView(R.id.home_fragment_layout)
    ConstraintLayout home_fragment_layout;
    @BindView(R.id.shop_details_layout)
    ConstraintLayout shop_details_layout;
    @BindView(R.id.image_back)
    ImageView image_back;
    @BindView(R.id.shop_xbanner)
    XBanner xBanner;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.add_cart)
    ImageView add_cart;
    @BindView(R.id.buy_shop)
    ImageView buy_shop;
    private boolean falg =true;
    private IPresenterImpl iPresenter;
    Unbinder unbinder;
    private HomeHeatAdapter homeHeatAdapter;
    private HomeFashionAdapter homeFashionAdapter;
    private HomeQualityAdapter homeQualityAdapter;
    private OneCategoryAdapter oneCategoryAdapter;
    private TwoCategoryAdapter twoCategoryAdapter;
    private TwoCategoryShopAdapter twoCategoryShopAdapter;
    private final int COUNT=5;
    private int mpage=1;
    private HomeSearchShopAdapter homeSearchShopAdapter;
    private String rxxplabelId;
    private String mlsslabelId;
    private String pzshlabelId;
    private LabelSearchShopAdapter labelSearchShopAdapter;
    private String sessionId;
    private int userId;

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
        //绑定EventBus
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        //设置输入框失去焦点
        EditTextUtils.losePoint(getActivity(),editName);
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
        //点击
        homeQualityAdapter.setOnClickListener(new HomeQualityAdapter.Click() {
            @Override
            public void onClick(int commodityId) {
                shop_details_layout.setVisibility(View.VISIBLE);
                home_fragment_layout.setVisibility(View.GONE);
                //加载布局
                initShopDetailsView();
                //请求商品详细
                getShopDetails(commodityId);
            }
        });
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
        //点击详情
        homeFashionAdapter.setOnClickListener(new HomeFashionAdapter.Click() {
            @Override
            public void onClick(int commodityId) {
                shop_details_layout.setVisibility(View.VISIBLE);
                home_fragment_layout.setVisibility(View.GONE);
                //加载布局
                initShopDetailsView();
                //请求商品详细
                getShopDetails(commodityId);
            }
        });
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
        //点击
        homeHeatAdapter.setOnClickListener(new HomeHeatAdapter.Click() {
            @Override
            public void onClick(int commodityId) {
                shop_details_layout.setVisibility(View.VISIBLE);
                home_fragment_layout.setVisibility(View.GONE);
                //加载布局
                initShopDetailsView();
                //请求商品详细
                getShopDetails(commodityId);
            }
        });
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
                rxxplabelId = String.valueOf(shopBean.getResult().getRxxp().get(0).getId());
                mlsslabelId = String.valueOf(shopBean.getResult().getMlss().get(0).getId());
                pzshlabelId = String.valueOf(shopBean.getResult().getPzsh().get(0).getId());
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
        }else if (o instanceof OneCategoryBean){
            OneCategoryBean oneCategoryBean = (OneCategoryBean) o;
            if (oneCategoryBean==null||!oneCategoryBean.isSuccess()){
                ((ShowActivity)getActivity()).getToast(oneCategoryBean.getMessage());
            }else{
                oneCategoryAdapter.setList(oneCategoryBean.getResult());
            }
        }else if (o instanceof TwoCategoryBean){
            TwoCategoryBean twoCategoryBean = (TwoCategoryBean) o;
            if (twoCategoryBean==null||!twoCategoryBean.isSuccess()){
                ((ShowActivity)getActivity()).getToast(twoCategoryBean.getMessage());
            }else{
                twoCategoryAdapter.setList(twoCategoryBean.getResult());
            }
        }else if (o instanceof TwoCategoryShopBean){
            TwoCategoryShopBean twoCategoryShopBean = (TwoCategoryShopBean) o;
            if (twoCategoryShopBean==null||!twoCategoryShopBean.isSuccess()){
                ((ShowActivity)getActivity()).getToast(twoCategoryShopBean.getMessage());
            }else{
                //加载数据的时候隐藏
                products_text.setVisibility(View.GONE);
                products_background_image.setVisibility(View.GONE);
                // 传值到适配器
                twoCategoryShopAdapter.setList(twoCategoryShopBean.getResult());
            }
        }else if (o instanceof SearchShopBean){
            SearchShopBean searchShopBean = (SearchShopBean) o;
            if (searchShopBean==null||!searchShopBean.isSuccess()){
                ((ShowActivity)getActivity()).getToast(searchShopBean.getMessage());
            }else{
                if (searchShopBean.getResult().size()==0){
                    //提示商品没有找到
                    backgroud_image.setVisibility(View.VISIBLE);
                    backgroud_text.setVisibility(View.VISIBLE);
                }else {
                    backgroud_image.setVisibility(View.GONE);
                    backgroud_text.setVisibility(View.GONE);
                    // 传值到适配器
                    homeSearchShopAdapter.setList(searchShopBean.getResult());
                }
            }
        }else if (o instanceof LabelStopBean){
            LabelStopBean labelStopBean = (LabelStopBean) o;
            if (labelStopBean==null||!labelStopBean.isSuccess()){
                ((ShowActivity)getActivity()).getToast(labelStopBean.getMessage());
            }else{
                // 传值到适配器
                labelSearchShopAdapter.setList(labelStopBean.getResult());
            }
        }else if (o instanceof ShopDetailsBean){
            ShopDetailsBean shopDetailsBean = (ShopDetailsBean) o;
            if (shopDetailsBean==null||!shopDetailsBean.isSuccess()){
                ((ShowActivity)getActivity()).getToast(shopDetailsBean.getMessage());
            }else{
                // 传值到xbanner
                String[] split = shopDetailsBean.getResult().getPicture().split("\\,");
                List<String> image=new ArrayList<>();
               for (int i =0;i<split.length;i++){
                   image.add(split[i]);
               }
               xBanner.setData(image,null);
               title.setText(shopDetailsBean.getResult().getCommodityName());
               price.setText("¥"+shopDetailsBean.getResult().getPrice());
               webview.loadDataWithBaseURL(null,shopDetailsBean.getResult().getDetails(),"text/html","utf-8",null);

                //支持javascript
                webview.getSettings().setJavaScriptEnabled(true);
                // 设置可以支持缩放
                webview.getSettings().setSupportZoom(true);
                // 设置出现缩放工具
                webview.getSettings().setBuiltInZoomControls(true);
                //扩大比例的缩放
                webview.getSettings().setUseWideViewPort(true);

                //自适应屏幕
                webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                webview.getSettings().setLoadWithOverviewMode(true);
            }
        }else if (o instanceof SyncShopBean){
            SyncShopBean syncShopBean = (SyncShopBean) o;
            ((ShowActivity) getActivity()).getToast(syncShopBean.getMessage());

        }else if (o instanceof CartBean){
            CartBean cartBean = (CartBean) o;
            if (cartBean!=null&&cartBean.isSuccess()){
                List<ShopResultBean> list=new ArrayList<>();
                List<CartBean.ResultBean> result = cartBean.getResult();
                for(CartBean.ResultBean results:result){
                    list.add(new ShopResultBean(results.getCommodityId(),results.getCount()));
                }
                addShopCar(list);

            }
            ((ShowActivity)getActivity()).getToast(cartBean.getMessage());
        }else if (o instanceof String){
            String s = (String) o;
            ((ShowActivity)getActivity()).getToast(s);
        }
    }
    /**
     *添加到购物车
     *@author Administrator
     *@time 2019/1/8 0008 14:53
     */
    private void addShopCar(List<ShopResultBean> list) {
        String string="[";
        for (int i=0;i<list.size();i++){
            if(Integer.valueOf(commodityId)==list.get(i).getCommodityId()){
                int count = list.get(i).getCount();
                count++;
                list.get(i).setCount(count);
                break;
            }else if(i==list.size()-1){
                list.add(new ShopResultBean(Integer.valueOf(commodityId),1));
                break;
            }
        }
        for (ShopResultBean resultBean:list){
            string+="{\"commodityId\":"+resultBean.getCommodityId()+",\"count\":"+resultBean.getCount()+"},";
        }
        String substring = string.substring(0, string.length() - 1);
        substring+="]";
        Log.i("TAG",substring);
        //请求添加购物车
        Map<String,String> params=new HashMap<>();
        params.put("data",substring);
        iPresenter.putRequeryData(Apis.URL_SYNC_SHOPPING_CART_PUT,params,SyncShopBean.class);
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onMessage(Object o){
        if (o instanceof LoginBean.ResultBean){
            LoginBean.ResultBean resultBean= (LoginBean.ResultBean) o;
            sessionId = resultBean.getSessionId();
            userId = resultBean.getUserId();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder!=null){
            unbinder.unbind();
        }
        iPresenter.onDetach();
        EventBus.getDefault().isRegistered(this);
        EventBus.getDefault().removeStickyEvent(LoginBean.ResultBean.class);
    }
    @OnClick({R.id.image_classification, R.id.image_search,R.id.but_heat_new_products, R.id.but_magic_fashion, R.id.but_quality_life,R.id.image_back,R.id.edit_name,R.id.add_cart,R.id.buy_shop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_classification:
                if (falg){
                    category_one_item.setVisibility(View.VISIBLE);
                    category_two_item.setVisibility(View.VISIBLE);
                    getOneCategoryView();
                    getTwoCategoryView();
                    getOneCategoryData();
                }else{
                    category_one_item.setVisibility(View.GONE);
                    category_two_item.setVisibility(View.GONE);
                }
               falg=!falg;
                break;
            case R.id.image_search:
                products_text.setVisibility(View.GONE);
                products_background_image.setVisibility(View.GONE);
                //搜索显示布局
                showView();
                searchShopData();
                break;
            case R.id.but_heat_new_products:
                products_text.setVisibility(View.VISIBLE);
                products_background_image.setVisibility(View.VISIBLE);
                products_text.setText("热销新品");
                products_text.setTextColor(Color.parseColor("#ff7f57"));
                products_background_image.setBackgroundResource(R.mipmap.bg_rxxp_syf);
                showView();
                //创建布局
                getTwoCategoryShopView();
                //创建适配器
                setLabelAdapter();
                //请求数据
                getRequeryData(rxxplabelId);
                break;
            case R.id.but_magic_fashion:
                products_text.setVisibility(View.VISIBLE);
                products_background_image.setVisibility(View.VISIBLE);
                products_text.setText("魔力时代");
                products_text.setTextColor(Color.parseColor("#6699ff"));
                products_background_image.setBackgroundResource(R.mipmap.bg_mlss_syf);
                showView();
                //创建布局
                getTwoCategoryShopView();
                //创建适配器
                setLabelAdapter();
                //请求数据
                getRequeryData(mlsslabelId);
                break;
            case R.id.but_quality_life:
                products_text.setVisibility(View.VISIBLE);
                products_background_image.setVisibility(View.VISIBLE);
                products_text.setText("品质生活");
                products_text.setTextColor(Color.parseColor("#ff6600"));
                products_background_image.setBackgroundResource(R.mipmap.bg_pzsh_syf);
                showView();
                //创建布局
                getTwoCategoryShopView();
                //创建适配器
                setLabelAdapter();
                //请求数据
                getRequeryData(pzshlabelId);
                break;
            case R.id.image_back:
                shop_details_layout.setVisibility(View.GONE);
                home_fragment_layout.setVisibility(View.VISIBLE);
                break;
                case R.id.edit_name:
                    //获取焦点
                    EditTextUtils.searchPoint(getActivity(),editName);
                    break;
            case R.id.add_cart:
                //添加时先查询购物车，如果查询成功后购物车有数据就重新拼接添加
                iPresenter.getRequeryData(Apis.URL_FIND_SHOPPING_CART_GET,CartBean.class);
                break;
            case R.id.buy_shop:

                break;
                default:
                    break;
        }
    }
    /**
     *创建商品归类适配器
     *@author Administrator
     *@time 2019/1/3 0003 17:46
     */
    private void setLabelAdapter() {
        labelSearchShopAdapter = new LabelSearchShopAdapter(getActivity());
        category_two_shop.setAdapter(labelSearchShopAdapter);
        labelSearchShopAdapter.setOnClickListener(new LabelSearchShopAdapter.Click() {
            @Override
            public void onClick(int commodityId) {
                shop_details_layout.setVisibility(View.VISIBLE);
                home_fragment_layout.setVisibility(View.GONE);
                //加载布局
                initShopDetailsView();
                //请求商品详细
                getShopDetails(commodityId);
            }
        });

    }
    /**
     *加载商品详细布局
     *@author Administrator
     *@time 2019/1/3 0003 19:16
     */
    private void initShopDetailsView() {
         xBanner.loadImage(new XBanner.XBannerAdapter() {
             @Override
             public void loadBanner(XBanner banner, Object model, View view, int position) {
                 String s = (String) model;
                 Glide.with(getActivity()).load(s).into((ImageView) view);
             }
         });
    }

    /**
     *请求商品详细
     *@author Administrator
     *@time 2019/1/3 0003 17:52
     */
    private int commodityId;
    private void getShopDetails(int commodityId) {
        this.commodityId=commodityId;
        // 商品详情
        iPresenter.getRequeryData(String.format(Apis.GET_URL_FIND_COMMODITY_DETAILS_BYID,commodityId),ShopDetailsBean.class);
    }

    /**
     *根据商品归类请求网络数据
     *@author Administrator
     *@time 2019/1/3 0003 17:46
     */
    private void getRequeryData(String labelId) {
        iPresenter.getRequeryData(String.format(Apis.GET_URL_FIND_COMMODITY_LIST_BYLABEL,labelId,mpage,COUNT),LabelStopBean.class);
    }


    /**
     *搜索查询商品
     *@author Administrator
     *@time 2019/1/3 0003 13:40
     */
    private void searchShopData() {
        //创建布局
        getTwoCategoryShopView();
        //创建适配器
        homeSearchShopAdapter = new HomeSearchShopAdapter(getActivity());
        category_two_shop.setAdapter(homeSearchShopAdapter);
        //获取输入框的数据
        String name = editName.getText().toString().trim();
        if (name.equals("")){
            ((ShowActivity)getActivity()).getToast("请输入要搜索的商品");
        }else {
            //搜索查询商品
            iPresenter.getRequeryData(String.format(Apis.GET_URL_FIND_COMMODITY_BYKEYWORD_SHOP, name, mpage, COUNT), SearchShopBean.class);
        }
        //点击事件
        homeSearchShopAdapter.setOnClickListener(new HomeSearchShopAdapter.Click() {
            @Override
            public void onClick(int commodityId) {
                shop_details_layout.setVisibility(View.VISIBLE);
                home_fragment_layout.setVisibility(View.GONE);
                //加载布局
                initShopDetailsView();
                //请求商品详细
                getShopDetails(commodityId);
            }
        });
    }

    /**
     *请求首页一级商品数据
     *@author Administrator
     *@time 2019/1/3 0003 13:36
     */
    private void getOneCategoryData() {

        iPresenter.getRequeryData(Apis.GET_URL_FIND_FIRST_CATEGORY,OneCategoryBean.class);
        iPresenter.getRequeryData(String.format(Apis.GET_URL_FIND_SECOND_CATEGORY,"1001002"),TwoCategoryBean.class);
    }

    /**
     *加载二级商品类目
     *@author Administrator
     *@time 2019/1/2 0002 20:05
     */
    private void getTwoCategoryView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        category_two_item.setLayoutManager(linearLayoutManager);
        //创建适配器
        twoCategoryAdapter = new TwoCategoryAdapter(getActivity());
        category_two_item.setAdapter(twoCategoryAdapter);
        twoCategoryAdapter.setOnClickListener(new TwoCategoryAdapter.Click() {
            @Override
            public void onClick(String id) {
                showView();
                //创建二级商品布局
                getTwoCategoryShopView();
                //添加适配器
                getTwoCategoryShopAdapter();
                //请求二级商品条目详细商品
                iPresenter.getRequeryData(String.format(Apis.GET_URL_FIND_COMMODITY_BYCATEGORY,id,mpage,COUNT),TwoCategoryShopBean.class);
            }
        });
    }
    /**
     *显示布局
     *@author Administrator
     *@time 2019/1/3 0003 14:20
     */
    private void showView() {
        category_two_shop.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
        category_one_item.setVisibility(View.GONE);
        category_two_item.setVisibility(View.GONE);
        backgroud_image.setVisibility(View.GONE);
        backgroud_text.setVisibility(View.GONE);
    }

    /**
     *加载二级商品条目详细商品
     *@author Administrator
     *@time 2019/1/3 0003 11:07
     */
    private void getTwoCategoryShopView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        category_two_shop.setLayoutManager(gridLayoutManager);
    }
    /**
     *二级商品条目详细商品适配器
     *@author Administrator
     *@time 2019/1/3 0003 13:45
     */
    private void getTwoCategoryShopAdapter() {
        //创建适配器
        twoCategoryShopAdapter = new TwoCategoryShopAdapter(getActivity());
        category_two_shop.setAdapter(twoCategoryShopAdapter);
        //点击详情事件
        twoCategoryShopAdapter.setOnClickListener(new TwoCategoryShopAdapter.Click() {
            @Override
            public void onClick(int commodityId) {
                shop_details_layout.setVisibility(View.VISIBLE);
                home_fragment_layout.setVisibility(View.GONE);
                //加载布局
                initShopDetailsView();
                //请求商品详细
                getShopDetails(commodityId);
            }
        });

    }

    /**
     *加载一级商品类目
     *@author Administrator
     *@time 2019/1/2 0002 20:05
     */
    private void getOneCategoryView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        category_one_item.setLayoutManager(linearLayoutManager);
        //创建适配器
        oneCategoryAdapter = new OneCategoryAdapter(getActivity());
        category_one_item.setAdapter(oneCategoryAdapter);
        oneCategoryAdapter.setOnClickListener(new OneCategoryAdapter.Click() {
            @Override
            public void onClick(String firstCategoryId) {
                iPresenter.getRequeryData(String.format(Apis.GET_URL_FIND_SECOND_CATEGORY,firstCategoryId),TwoCategoryBean.class);
            }
        });
    }
    /**
     *返回键的监听
     *@author Administrator
     *@time 2019/1/3 0003 12:03
     */
    public void getBackData(boolean back){
        Log.i("TAG",back+"");
        if(back){
           shop_details_layout.setVisibility(View.GONE);
            home_fragment_layout.setVisibility(View.VISIBLE);
            category_two_shop.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            category_one_item.setVisibility(View.GONE);
            category_two_item.setVisibility(View.GONE);
            products_text.setVisibility(View.GONE);
            products_background_image.setVisibility(View.GONE);
        }
    }
}
