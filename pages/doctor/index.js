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
    replys: [], 
    tmplModal:false,
    quickReply:app.globalData.quickReply,
    tmplMsg:"为了能更好、更方便的与患者进行交流，小程序需要在复查后对您发送消息。"
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
  async onShow(){
    let accept = await util.checkTmplId('iNbbQStWha87QupCzPHTmNmXvedPSmbKuAz7M4rJwt4');
    console.log(accept)
    if(!accept){
      that.setData({
        tmplModal:true
      })
    }
  },
  tmplModel(){
    that.setData({
      tmplModal:!that.data.tmplModal
    })
  },
  subscribeMessage(){
    that.tmplModel();
    wx.requestSubscribeMessage({
      tmplIds:['iNbbQStWha87QupCzPHTmNmXvedPSmbKuAz7M4rJwt4'],
      success(res){
        console.log(res);
        api.subscription({
          tmplId:'iNbbQStWha87QupCzPHTmNmXvedPSmbKuAz7M4rJwt4',
          accept:res['iNbbQStWha87QupCzPHTmNmXvedPSmbKuAz7M4rJwt4']==="accept"?true:false
        })
      },
      fail(err){
        console.log("err"+err)
        console.log(err)
      },
      complete(com){
        console.log("com")
        console.log(com)
      }
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
    console.log(e)
    let replys = that.data.replys;
    console.log(e)
    let params = e.detail.value;
    if(params.reply==""){
      return util.prompt(that,"请输入回复内容后提交");
    }
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
  },
})