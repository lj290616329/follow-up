var that;
Page({
  data: {
    index:0
  },
  onLoad: function (options) {
    that = this;
    that.setData({
      userInfo:wx.getStorageSync('userInfo')
    })
  },
  switchUser(){
    wx.reLaunch({
      url: '/pages/doctor/index',
    })
  }
})