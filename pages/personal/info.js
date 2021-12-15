var that;
const util = require('../../utils/util');
const api = require('../../config/api');
const app = getApp();
Page({
  data: {
    code:0,
    types:app.globalData.types,
    diseases:app.globalData.diseases   
  },
  async onLoad(options) {
    that = this;
    let res = await api.personalInfo({});
    console.log(res);
    let diseases = app.globalData.diseases;
    let disease = diseases.find(d=>d.id==res.data.type);  
    console.log(disease)
    that.setData({
      code:res.code,
      msg:res.msg,
      information:res.data,
      disease:disease 
    })
  },
  showpic(e){
    console.log(e);
    let urls = e.currentTarget.dataset.urls;
    let current = e.currentTarget.dataset.current;
    wx.previewImage({
      urls: urls,
      current:current
    })
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