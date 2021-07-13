// pages/doctor/patient/detail.js
var that;
const config = require("../../../config/config");
const util = require("../../../utils/util");
Page({
  data: {
    types:["肝脏肿瘤-HCC","肝脏肿瘤-ICC","肝脏肿瘤-其他","肝道肿瘤-肝门","肝道肿瘤-肝门","肝道肿瘤-其他","胰腺肿瘤-胰腺","胰腺肿瘤-其他","其他"],
  },
  async onLoad(options) {
    that = this;
    let res =await util.sendAjax(config.InformationById+options.id,{},"get");
    console.log(res);
    that.setData({
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
  }  
})