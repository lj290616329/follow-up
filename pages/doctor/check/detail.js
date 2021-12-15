var that;
const util = require('../../../utils/util');
const api = require('../../../config/api');
const app = getApp();
Page({
  data: {
    code:0,
    compare:app.globalData.compare,
    replyStatus:false,
    quickReply:app.globalData.quickReply    
  },
  async onLoad(options) {
    that = this;
    let res = await api.reviewPlanDetail({id:options.id});
    console.log(res);
    that.setData({
      code:res.code,
      msg:res.msg,
      plan:res.data,
      id:options.id
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
  quickReply(e){
    console.log(e);
    that.setData({
      reply:that.data.quickReply[e.currentTarget.dataset.index]
    })
  },
  replyModal(e){
    console.log(e)
    that.setData({
      replyStatus:!that.data.replyStatus,
      id:e.currentTarget.dataset.id
    })
  },
  authModal(){
    let showModal = that.data.showModal;
    that.setData({
      showModal:!showModal
    })
  },
  async submitForm(e) {
    let params = e.detail.value;
    if(params.reply==""){
      return util.prompt(that,"请输入回复内容后提交");
    }
    let res = await api.reply(params);
    if(res.code==0){
      util.prompt(that,"提交成功");
      that.replyModal({currentTarget:{
        dataset:{}
      }});
      that.back();
    }else{
      util.prompt(that,res.msg);
    }
  },
})