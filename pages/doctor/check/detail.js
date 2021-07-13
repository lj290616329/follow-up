var that;
const util = require('../../../utils/util');
const config = require('../../../config/config');
Page({
  data: {
    compare:{
      cbc:'血常规',
      biochemistry:'生化',
      dic:'凝血',
      swelling:'肿标',
      bmode:'B超',
      ct:'CT',
      mri:'MRI'
    }    
  },
  async onLoad(options) {
    that = this;
    let res = await util.sendAjax(config.ReviewPlan,{id:options.id},"get");
    console.log(res);
    that.setData({
      plan:res.data
    });
  },
  showpic(e){
    let urls = e.currentTarget.dataset.pics;
    if(urls.length>0){
      wx.previewImage({
        urls: e.currentTarget.dataset.pics,
      })
    }else{
      util.prompt(that,'该项目未提交图片');
    }    
  },
})