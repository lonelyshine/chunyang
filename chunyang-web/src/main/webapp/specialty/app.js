//app.js
App({
  onLaunch: function() {
    //调用API从本地缓存中获取数据
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)
  },

  /*getUserInfo: function(cb) {
    var that = this
    if (this.globalData.userInfo) {
      typeof cb == "function" && cb(this.globalData.userInfo)
    } else {
      //调用登录接口
      wx.getUserInfo({
        withCredentials: false,
        success: function(res) {
          that.globalData.userInfo = res.userInfo
          typeof cb == "function" && cb(that.globalData.userInfo)
        }
      })
    }
  },*/
  //通过SessionInfo判断用户是否已经在用户是否已经登录
  getSessionInfo: function (cb) {
    var me = this, sessionInfo = this.globalData.sessionInfo;
    if (sessionInfo) {
      /*如果存在session则跳转到相关页面  下面的cb表示的是其他页面调用该getSessionInfo方法时传入的方法 例如function（）{wx.reLaunch({
        url: '',
      })}*/
      typeof cb == 'function' && cb(sessionInfo);  //把参数cb标识为方法，然后执行该cb方法
    } else {
      //session不存在的话需要登录验证
      wx.login({
        success: function (res) {//登录接口调用成功后的回调函数
          var code = res.code;
          if (code) {//用户允许登录后，回调内容会带上code（有效时间为5分钟），需要将code发送到后台，使用code换取session_key api,将code换成openid和session_key
            wx.getUserInfo({
              withCredentials: true,//withCredentials为true时会要求此前有调用过 wx.login 且登录态尚未过期，此时                                        //返回的数据会包含 encryptedData, iv 等敏感信息；当 withCredentials 为                                            //false 时，不要求有登录态，返回的数据不包含 encryptedData, iv 等敏感信息。
              success: function (res) {//接口调用成功后的回调函数
                me.globalData.userInfo = res.userInfo;
                wx.request({
                  url: me.getServerUrl() + '/decodeUserInfo',
                  method: 'POST',
                  data: {
                    code: code,
                    iv: res.iv,
                    encryptedData: res.encryptedData
                  },
                  header: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                  },
                  success: function (res) {
                    me.globalData.sessionInfo = res.data;
                    //这里同样处理页面的跳转
                    // example 如上
                    typeof cb == 'function' && cb(me.globalData.userInfo);
                  },
                  fail:function(){
                    console.log("系统错误");
                  }
                })

              },
              fail: function (res) {//接口调用失败后回调函数，获取用户信息失败，这里需要处理用户没有授权的相关操作
               console.log("获取用户信息失败");
                wx.redirectTo({
                  url: '',
                })

              }
            })
          } else {
            console.log('获取用户状态失败！' + res.errMsg);
          }
        },
        fail:function(){
            console.log("登录失败");
        }
      })
    }
  },

  getServerUrl: function () {
    return this.globalData.serverUrl
  },

  globalData: {
    serverUrl: 'http://127.0.0.1:8083',
    userInfo: null,
    sessionInfo: null
  }
})
