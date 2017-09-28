//index.js
//获取应用实例
var app = getApp()
Page({
  data: {
    motto: 'Hello World',
    userInfo: {}
  },
  //事件处理函数
  bindViewTap: function() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  onLoad: function (opts) {
    var me = this;
    console.log('onLoad')
    /*var that = this
    //调用应用实例的方法获取全局数据
    app.getUserInfo(function(userInfo){
      //更新数据
      that.setData({
        userInfo:userInfo
      })
    })*/
    var scene = decodeURIComponent(opts.scene);
    app.getSessionInfo(function (userInfo) {
      console.log(userInfo);
      //更新数据
      me.setData({
        userInfo: userInfo
      })
      if (scene && scene !== "undefined") {
        wx.reLaunch({
          url: util.formatStr('/pages/carddtl/cardDtl?tenant={0}', [scene]),
        })
      } else {
        wx.reLaunch({
          url: "/pages/mbrcards/mbrcards",
        })
      }
    })
  }
})
