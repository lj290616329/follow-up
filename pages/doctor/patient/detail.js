// pages/doctor/patient/detail.js
var that;
const config = require("../../../config/config");
const util = require("../../../utils/util");
const app = getApp();
Page({
  data: {
    code:0,
    types:app.globalData.types
  },
  async onLoad(options) {
    that = this;
    let res =await util.sendAjax(config.InformationById+options.id,{},"get");
    console.log(res);
    that.setData({
      code:res.code,
      msg:res.msg,
      information:res.data.information,
      reviewPlans:res.data.reviewPlans
    })
  },
  detail(e){
    let id = e.currentTarget.dataset.id;
    wx.navigateTo({
      url: '/pages/doctor/check/detail?id='+id,
    })
  },
  prompt(){
    util.prompt(that,"该用户暂未提交检测信息");
  },
  callPhone(e){
    let phone = e.currentTarget.dataset.phone;
    wx.makePhoneCall({
      phoneNumber: phone, //仅为示例，并非真实的电话号码
      fail(){
        util.prompt(that,"调用拨号功能失败~请稍后再试!")
      }
    })
  }  
})