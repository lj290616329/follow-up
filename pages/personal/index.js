var that;
const api = require('../../config/api');
const util = require('../../utils/util');
Page({
  data: {
    code:0,
    index:0,
    tmplModal:false,
    tmplMsg:"为了能及时收到提醒以及回复消息，小程序需要在复查后对您发送消息。"
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
    let accept = await util.checkTmplId('Uw13UmFttdBbvz3YDHNQ1VghuFaoUUFOa2oEH3c-nqE');
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
      tmplIds:['Uw13UmFttdBbvz3YDHNQ1VghuFaoUUFOa2oEH3c-nqE'],
      success(res){
        console.log(res);
        api.subscription({
          tmplId:'Uw13UmFttdBbvz3YDHNQ1VghuFaoUUFOa2oEH3c-nqE',
          accept:res['Uw13UmFttdBbvz3YDHNQ1VghuFaoUUFOa2oEH3c-nqE']==="accept"?true:false
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
  }
})