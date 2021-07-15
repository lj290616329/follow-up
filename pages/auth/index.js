var that;
const util = require('../../utils/util');
const config = require('../../config/config');
Page({
  data: {
    msg:'扫描成功',
    type:'success',
    backMsg:'取消',
    authBtn:true
  },
  onLoad(options) {
    that = this;
    if(options.scene){
      that.setData({
        options:options
      })
    }else{
      that.setData({
        type:'warn',
        msg:'请扫描二维码进入',
        backMsg:'确认',
        authBtn:false
      })
    }
  },
  async submit(){
    let res = await util.sendAjax(config.Agree,that.data.options,"post");
    if(res.code==0){
      wx.showToast({
        title: '操作成功',//提示文字
        duration:3000,//显示时长
        icon:'success', //图标，支持"success"、"loading"  
        success:function(){ 
          wx.reLaunch({
            url: '/pages/doctor/index',
          })
        }
     })
    }else{
      wx.showToast({
        title: res.msg,//提示文字
        duration:3000,//显示时长
        icon:'error', //图标，支持"success"、"loading"  
        success:function(){ 
          wx.reLaunch({
            url: '/pages/index/index',
          })
        }
     })
    }
  },
  back(){
    wx.reLaunch({
      url: '/pages/index/index',
    })
  }
})