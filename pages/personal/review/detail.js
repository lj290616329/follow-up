var that;
const util = require('../../../utils/util');
const config = require('../../../config/config');
const app = getApp();
Page({
  data: {
    code:0,
    compare:app.globalData.compare    
  },
  async onLoad(options) {
    that = this;
    let res = await util.sendAjax(config.ReviewPlan,{id:options.id},"get");
    console.log(res);
    that.setData({
      code:res.code,
      msg:res.msg,
      plan:res.data,
      information:res.data.review
    });
  },
  
  showpic(e){
    let urls = e.currentTarget.dataset.urls;
    let current = e.currentTarget.dataset.current;
    wx.previewImage({
      urls: urls,
      current:current
    })
  },
})