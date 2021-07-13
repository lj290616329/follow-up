var that;
const util = require("../../../utils/util");
const config = require("../../../config/config");
Page({
  data: {
    compare:{
      'cbc':'血常规',
      'biochemistry':'生化',
      'dic':'凝血',
      'swelling':'肿标',
      'bmode':'B超',
      'ct':'CT',
      'mri':'MRI',
      '其他':'其他'
    },
    reviewPlans:[]
  },
  async onLoad(options) {
    that = this;
    let res = await util.sendAjax(config.PersonalPlanList,{},"get");
    console.log(res);
    that.setData({
      reviewPlans:res.data
    })
  },
  detail(e){
    let id = e.currentTarget.dataset.id;
    wx.navigateTo({
      url: `/pages/personal/review/detail?id=${id}`,
    })
  },
  prompt(){
    util.prompt(that,"该用户暂未提交检测信息");
  }  

})