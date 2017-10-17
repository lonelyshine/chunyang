// pages/home/home.js
var util = require('../../utils/util.js');
//获取应用实例
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    banner: [],//首页轮播展示的banner
    indicatorDots: true,//是否出现焦点 也就是图片下方的焦点
    autoplay: true,//是否自动播放
    interval: 5000, //自动播放的间隔时间
    duration: 1000, //滑动动画时间

    goods:[]//首页商品内容
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
   
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    //加载banner
    this.getHomeBanner();
    //加载首页的商品内容
    this.getHomeProduct();
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
  
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
  
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
  
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
  
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
  
  },
  /**
   * 获取首页banner
   */
  getHomeBanner:function(){
    var me = this;
    console.log(me.data.banner);
    var serverUrl = app.globalData.serverUrl;
    // 请求服务器
    wx.request({
      url: serverUrl + '/home/banner',
      method: 'GET',
      data: {},
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        var banner = me.data.banner;
        var data = res.data;
        me.setData({
          banner: data
        });
      }
    })
  },

  getHomeProduct:function(){
    var me = this;
    console.log(me.data.banner);
    var serverUrl = app.globalData.serverUrl;
    // 请求服务器
    wx.request({
      url: serverUrl + '/home/product',
      method: 'GET',
      data: {},
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        var goods = me.data.goods;
        var data = res.data;
        me.setData({
          goods : data
        });
      }
    })
  },

  goodscontent: function (e){
    var params = e.currentTarget.dataset;
    wx.navigateTo({
      url: params.linkurl
    })
  }
})