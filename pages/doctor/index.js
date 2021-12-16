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
    tmplMsg:"为了能更好、更方便的与患者进行交流，小程序需要在复查后对您发送消息。",
    settingModal:false,
    settingMsg:"您的消息订阅未打开,如需要消息推送服务，请点击确定跳转设置页面打开授权后再次尝试。"
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
    if(app.globalData.showModal) return;
    app.globalData.showModal = true;
    let tmplId = 'iNbbQStWha87QupCzPHTmNmXvedPSmbKuAz7M4rJwt4';
    let res = await util.checkTmplId(tmplId);
    console.log(res)
    if(!res.flag){
      if(res.setting){
        that.setData({
          settingModal:true,
          settingMsg:res.msg
        })
      }else{
        that.tmplModel()
      }      
    }else{
      api.subscription({
        tmplId:tmplId,
        accept:true
      })
    }    
  },
  tmplModel(){
    that.setData({
      tmplModal:!that.data.tmplModal
    })
  },
  settingModel(){
    that.setData({
      settingModal:!that.data.settingModal
    })
  },
  openSetting(){
    that.settingModel();
    let tmplId = 'iNbbQStWha87QupCzPHTmNmXvedPSmbKuAz7M4rJwt4';
    wx.openSetting({withSubscriptions: true})
      .then(res=>{
        console.log(res);
        /**
         * 在有订阅消息返回时,主开关打开了,存在有订阅id的记录
         * 则告诉一下服务器
         */
        if(res.subscriptionsSetting && res.subscriptionsSetting.mainSwitch && res.subscriptionsSetting.itemSettings[tmplId]){
          api.subscription({
            tmplId:tmplId,
            accept:res.subscriptionsSetting.itemSettings[tmplId]==="accept"?true:false
          })
        }else{
          api.subscription({
            tmplId:tmplId,
            accept:false
          })
        }
      })
  },
  subscribeMessage(){
    that.tmplModel();
    let tmplId = 'iNbbQStWha87QupCzPHTmNmXvedPSmbKuAz7M4rJwt4';
    wx.requestSubscribeMessage({tmplIds:[tmplId]})
      .then(res=>{
        console.log(res);
        api.subscription({
          tmplId:tmplId,
          accept:res[tmplId]==="accept"?true:false
        })
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