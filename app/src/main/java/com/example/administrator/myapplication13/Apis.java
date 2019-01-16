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
    public static final String GET_URL_USER_FIND_USER_WALLET="user/verify/v1/findUserWallet?page=%d&count=%d";





    /** 圈子列表*/
    //public static final String GET_URL_USER_FIND_USER_CIRCLE="circle/v1/findCircleList";
    public static final String GET_URL_USER_FIND_USER_CIRCLE="circle/v1/findCircleList?userId=%d&sessionId=%s&page=%d&count=%d";
    /**
     * 圈子点赞 http://172.17.8.100/small/circle/verify/v1/addCircleGreat
     */
    public static final String POST_URL_ADD_CIRCLE_GREAT="circle/verify/v1/addCircleGreat";
    /**
     * 取消点赞 http://172.17.8.100/small/circle/verify/v1/cancelCircleGreat
     */
    public static final String DELETE_URL_CANCLE_CIRCLE_GREAT="circle/verify/v1/cancelCircleGreat?circleId=%d";
    /**
     * 我的圈子 http://172.17.8.100/small/circle/verify/v1/findMyCircleById
     */
    public static final String URL_FIND_MYCIRCLE_BYID_GET="circle/verify/v1/findMyCircleById?page=%d&count=%d";
    /**
     * 删除我发表过的圈子 http://172.17.8.100/small/circle/verify/v1/deleteCircle
     */
    public static final String URL_DELETE_CIRCLE_DELETE="circle/verify/v1/deleteCircle?circleId=%s";
    /**
     * 发布圈子 http://172.17.8.100/small/circle/verify/v1/releaseCircle
     */
    public static final String URL_RELEASE_CIRCLE_POST="circle/verify/v1/releaseCircle";




    /**首页商品列表*/
    public static final String GET_URL_USER_FIND_HOME_SHOP="commodity/v1/commodityList";
    /**首页轮播图*/
    public static final String GET_URL_USER_FIND_HOME_BANNER="commodity/v1/bannerShow";
    /**
     * 查询一级商品类目 http://172.17.8.100/small/commodity/v1/findFirstCategory
     */
    public static final String GET_URL_FIND_FIRST_CATEGORY="commodity/v1/findFirstCategory";
    /**
     * 查询二级商品类目 http://172.17.8.100/small/commodity/v1/findSecondCategory
     */
    public static final String GET_URL_FIND_SECOND_CATEGORY="commodity/v1/findSecondCategory?firstCategoryId=%s";
    /**
     * 根据二级类目查询商品信息 http://172.17.8.100/small/commodity/v1/findCommodityByCategory
     */
    public static final String GET_URL_FIND_COMMODITY_BYCATEGORY="commodity/v1/findCommodityByCategory?categoryId=%s&page=%d&count=%d";
    /**
     * 根据关键词查询商品信息 http://172.17.8.100/small/commodity/v1/findCommodityByKeyword
     */
    public static final String GET_URL_FIND_COMMODITY_BYKEYWORD_SHOP="commodity/v1/findCommodityByKeyword?keyword=%s&page=%d&count=%d";
    /**
     * 根据商品列表归属标签查询商品信息 http://172.17.8.100/small/commodity/v1/findCommodityListByLabel
     */
    public static final String GET_URL_FIND_COMMODITY_LIST_BYLABEL="commodity/v1/findCommodityListByLabel?labelId=%s&page=%d&count=%d";
    /**
     *  商品详情 http://172.17.8.100/small/commodity/v1/findCommodityDetailsById
     */
    public static final String GET_URL_FIND_COMMODITY_DETAILS_BYID="commodity/v1/findCommodityDetailsById?commodityId=%d";
    /**
     * 我的足迹 http://172.17.8.100/small/commodity/verify/v1/browseList
     */
    public static final String GET_URL_BROWSE_LIST="commodity/verify/v1/browseList?page=%d&count=%d";
    /**
     * 同步购物车数据 http://172.17.8.100/small/order/verify/v1/syncShoppingCart
     */
    public static final String URL_SYNC_SHOPPING_CART_PUT="order/verify/v1/syncShoppingCart";
    /**
     * 查询购物车 http://172.17.8.100/small/order/verify/v1/findShoppingCart
     */
    public static final String URL_FIND_SHOPPING_CART_GET="order/verify/v1/findShoppingCart";
    /**
     * 创建订单 http://172.17.8.100/small/order/verify/v1/createOrder
     */
    public static final String URL_CREATE_ORDER_POST="order/verify/v1/createOrder";
    /**
     * 根据订单状态查询订单信息 http://172.17.8.100/small/order/verify/v1/findOrderListByStatus
     */
    public static final String URL_FIND_ORDER_LIST_BYSTATUS_GET="order/verify/v1/findOrderListByStatus?status=%d&page=%d&count=%d";
    /**
     * 删除订单 http://172.17.8.100/small/order/verify/v1/deleteOrder
     */
    public static final String URL_DELETE_ORDER_DELETE="order/verify/v1/deleteOrder?orderId=%s";
    /**
     * 收货 http://172.17.8.100/small/order/verify/v1/confirmReceipt
     */
    public static final String URL_CONFIRM_RECEIPT_PUT="order/verify/v1/confirmReceipt";
    /**
     * 支付 http://172.17.8.100/small/order/verify/v1/pay
     */
    public static final String URL_PAY_POST="order/verify/v1/pay";
    /**
     * 商品评论 http://172.17.8.100/small/commodity/verify/v1/addCommodityComment
     */
    public static final String URL_ADD_COMMODITY_COMMENT_LIST_POST="commodity/verify/v1/addCommodityComment";
}
