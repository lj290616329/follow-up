var that;
const util = require("../../utils/util");
const api = require("../../config/api");
const app = getApp();
Page({
  data: {
    code:0,
    showModal:false,
    replyStatus:false,
    lists:[],
    compare:app.globalData.compare,
    replys: [], 
    quickReply:app.globalData.quickReply
  },
  switchUser(){
    wx.reLaunch({
      url: '/pages/personal/index',
    })
  },
  async onLoad(options){
    that = this;       
    let res = await api.doctorIndex({});
    that.setData({
      code:res.code,
      msg:res.msg,
      replys:res.data.reviewPlans,
      lists: res.data.informations
    })
  },
  userInfo(){
    wx.navigateTo({
      url: '/pages/doctor/info',
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
      index:e.currentTarget.dataset.index,      
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
  showpic(e){
    wx.previewImage({
      urls: e.currentTarget.dataset.pics,
    })
  },
  async submitForm(e) {
    let replys = that.data.replys;
    let index = that.data.index;
    console.log(e)
    let params = e.detail.value;
    if(params.reply==""){
      return util.prompt(that,"请输入回复内容后提交");
    }
    params.examine = replys[index].review.examine;
    let res = await api.reply(params);
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
  toUrl(e){
    console.log(e);
    let url = e.currentTarget.dataset.url;
    console.log(url)
    wx.navigateTo({
      url: url
    })
  }
})