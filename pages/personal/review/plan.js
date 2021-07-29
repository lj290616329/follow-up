var that;
const util = require("../../../utils/util");
const api = require("../../../config/api");
const app = getApp();
Page({
  data: {
    code:0,
    compare:app.globalData.compare,
    reviewPlans:[],
    more:false
  },
  async onLoad(options) {
    that = this;
    let res = await api.personalPlanList({});
    console.log(res);
    that.setData({
      code:res.code,
      msg:res.msg,
      reviewPlans:res.data||[]
    })
  },
  showMore(){
    that.setData({
      more:true
    })
  },
  detail(e){
    let id = e.currentTarget.dataset.id;
    wx.navigateTo({
      url: `/pages/personal/review/detail?id=${id}`,
    })
  },
  prompt(){
    util.prompt(that,"您暂未提交检测信息");
  }
})