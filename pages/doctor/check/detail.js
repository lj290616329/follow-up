var that;
const util = require('../../../utils/util');
const config = require('../../../config/config');
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
    params.examine = that.data.information.examine;
    let res = await util.sendAjax(config.DoctorReply,params,"put");
    if(res.code==0){
      util.prompt(that,"提交成功");
      replys.splice(that.data.index,1);
      that.setData({
        replys:replys
      })
      that.replyModal({currentTarget:{
        dataset:{}
      }});
    }else{
      util.prompt(that,res.msg);
    }
  },
})