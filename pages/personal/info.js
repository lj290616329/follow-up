var that;
const util = require('../../utils/util');
const config = require('../../config/config');
const app = getApp();
Page({
  data: {
    code:0,
    compare:app.globalData.compare,
    types:app.globalData.types,
    information:{
      pathology:["http://gridpic.tsing-tec.com/3984b675-a304-4abb-a66e-6ee749934ab1.png"],
      examine:{
        "cbc":["http://gridpic.tsing-tec.com/e0331c1c-49e4-4efd-b98c-701371a4978e.png","http://gridpic.tsing-tec.com/f6debe8d-9519-4e22-bdef-174f7d6b1b19.png"],
        "biochemistry":["http://gridpic.tsing-tec.com/7ad006fe-66ae-4386-95bb-4007fe8d9314.png","http://gridpic.tsing-tec.com/f3085581-04f4-48a4-9f84-cc72e78aa873.png","http://gridpic.tsing-tec.com/cd2d30b9-95d7-4cb3-9894-b96261036548.png"],
        "dic":["http://gridpic.tsing-tec.com/90635da4-60bb-4da1-873d-d8e392e500cb.png"],
        "swelling":["http://gridpic.tsing-tec.com/fdad0baa-af72-4cf2-a430-2f3abf7b3fb8.png"],
        "bMode":["http://gridpic.tsing-tec.com/a160ef3c-fc15-4c56-be6e-ca8839c5e36a.png"],
        "ct":["http://gridpic.tsing-tec.com/5720d520-6869-41d3-81ed-e5e3d8d572f8.png","http://gridpic.tsing-tec.com/065a3cf9-9037-4caa-a6ed-d67f916831c6.png",],
        "mri":["http://gridpic.tsing-tec.com/80cae499-33ab-416a-9e6f-3140af7d53b8.png"]
      }
    }    
  },
  async onLoad(options) {
    that = this;
    let res = await util.sendAjax(config.Information,{},"get");
    console.log(res);
    that.setData({
      code:res.code,
      msg:res.msg,
      information:res.data
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