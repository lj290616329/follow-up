var that;
const api = require('../../config/api');
const util = require('../../utils/util');
const app = getApp();
Page({
  data: {
    code:0,
    index:0,
    tmplModal:false,
    tmplMsg:"为了能及时收到提醒以及回复消息，小程序需要在复查后对您发送消息。",
    settingModal:false,
    settingMsg:"您的消息订阅未打开,如需要消息推送服务，请点击确定跳转设置页面打开授权后再次尝试。"
  },
  async onLoad(options) {
    that = this;
    let res = await api.personalIndex({});    
    that.setData({
      code:res.code,
      msg:res.msg,
      near:res.data.near,
      reply:res.data.reply
    })
  },
  async onShow(){
    if(app.globalData.showModal) return;
    app.globalData.showModal = true;
    let tmplId = 'Uw13UmFttdBbvz3YDHNQ1VghuFaoUUFOa2oEH3c-nqE';
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
    let tmplId = 'Uw13UmFttdBbvz3YDHNQ1VghuFaoUUFOa2oEH3c-nqE';
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
    let tmplId = 'Uw13UmFttdBbvz3YDHNQ1VghuFaoUUFOa2oEH3c-nqE';
    wx.requestSubscribeMessage({tmplIds:[tmplId]})
      .then(res=>{
        console.log(res);
        api.subscription({
          tmplId:tmplId,
          accept:res[tmplId]==="accept"?true:false
        })
      })
  }
})