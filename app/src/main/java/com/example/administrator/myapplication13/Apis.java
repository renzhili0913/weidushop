package com.example.administrator.myapplication13;

public class Apis {
    /**用户注册*/
    public static final String POST_URL_USER_REGISTER="user/v1/register";
    /**用户登录*/
    public static final String POST_URL_USER_LOGIN="user/v1/login";
    /**用户修改昵称*/
    public static final String PUT_URL_USER_MODIFY_USER_NICK="user/verify/v1/modifyUserNick";
    /**用户修改用户密码*/
    public static final String PUT_URL_USER_MODIFY_USER_PWD="user/verify/v1/modifyUserPwd";
    /**用户上传头像*/
    public static final String POST_URL_USER_MODIFY_HEAD_PIC="user/verify/v1/modifyHeadPic";
    /**根据用户ID查询用户信息*/
    public static final String GET_URL_USER_GET_USER_BYID="user/verify/v1/getUserById";
    /**收货地址列表*/
    public static final String GET_URL_USER_RECYCLE_ADDRESS_LIST="user/verify/v1/receiveAddressList";
    /**新增收货地址*/
    public static final String POST_URL_USER_ADD_RECYCLE_ADDRESS_LIST="user/verify/v1/addReceiveAddress";
    /**设置默认收货地址*/
    public static final String POST_URL_USER_SET_DEFAULT_RECYCLE_ADDRESS_LIST="user/verify/v1/setDefaultReceiveAddress";
    /**修改收货信息*/
    public static final String PUT_URL_USER_CHANGE_RECYCLE_ADDRESS="user/verify/v1/changeReceiveAddress";
    /**查询用户钱包*/
    public static final String GET_URL_USER_FIND_USER_WALLET="user/verify/v1/changeReceiveAddress";
    /** 圈子列表*/
    //public static final String GET_URL_USER_FIND_USER_CIRCLE="circle/v1/findCircleList";
    public static final String GET_URL_USER_FIND_USER_CIRCLE="circle/v1/findCircleList?userId=%d&sessionId=%s&page=%d&count=%d";


    /**首页商品列表*/
    public static final String GET_URL_USER_FIND_HOME_SHOP="commodity/v1/commodityList";
    /**首页轮播图*/
    public static final String GET_URL_USER_FIND_HOME_BANNER="commodity/v1/bannerShow";


}
