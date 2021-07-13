
import WxValidate from '../../utils/WxValidate';
var util = require('../../utils/util') ;
var config = require('../../config/config');
var that; 
Page({
  data: {
    phone:"",
    name:"",
  },
  async onLoad(options) {
    console.log(options)
    that = this;
    that.initValidate();
    let res = await util.login();
    console.log(res);
    if(res.code==0){
      //医生用户
      if(res.data.ifDoctor){
        wx.reLaunch({
          url: '/pages/doctor/index',
        })
      }else{
        //个人用户已填写个人信息
        wx.reLaunch({
          url: '/pages/personal/index',
        })
      }          
    }          
  },
  initValidate() {
    let rules = {
      name:{
        required: true,
        minlength:2
      }, 
      phone:{
        required:true
      }
    };
    let messages = {      
      name:{
        required: '请填写姓名',
        minlength:'请输入正确的名称'
      },
      phone:{
        required:'请授权获取手机号'
      }
    };
    that.WxValidate = new WxValidate(rules, messages)
  },
  async auth(e){
    let res = {};
    try {
      res = await wx.getUserProfile({
        desc: '用于完善会员资料', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
      })
    } catch (error) {
      return util.prompt(that,"授权失败,请重试~");
    }
    let code = await util.getCode();
    console.log(res)
    if(res.errMsg=="getUserProfile:ok"){
      let userInfo = res.userInfo
      wx.setStorageSync('userInfo', userInfo);      
      let authRes = await util.sendAjax(config.Auth,{
        code:code,
        encryptedData:res.encryptedData,
        iv:res.iv,
        signature:res.signature,
        rawData:res.rawData,
        did:wx.getStorageSync('did')
      },"post");
      if(authRes.code==0){
        wx.setStorageSync('token', authRes.data.token)
        that.informationModal();
      }else{
        util.prompt(that,authRes.msg);
      }
    }else{
      util.prompt(that,"授权失败,请重试~");
    }    
  },
  informationModal(){
    that.setData({
      informationStatus:!that.data.informationStatus
    })
  },
  visitor(){
    //let res = await util.auth();
    wx.navigateTo({
      url: '/pages/test/personal/index',
    })
    
  },
  async getPhoneNumber(e){
    console.log(e)
    let code = await util.getCode();
    console.log(e)
    if(e.detail.errMsg=='getPhoneNumber:ok'){
      let res = await util.phone({
        code:code,
        encryptedData:e.detail.encryptedData,
        iv:e.detail.iv,
        signature:e.detail.signature
      });
      console.log(res)
      if(res.code==0){
        that.setData({
          phone:res.data
        })
      }
    }
  },
  async submitForm(e) {
    console.log(e);
    const params = e.detail.value
    //校验表单
    if (!this.WxValidate.checkForm(params)) {
      const error = that.WxValidate.errorList[0];
      console.log(error.msg)
      util.prompt(that,error.msg);
      return false
    }
    let res = await util.sendAjax(config.InformationAuth,params,"post")
    if(res.code==0){
      let url = res.data?'/pages/doctor/index':'/pages/personal/index';
      wx.reLaunch({
        url: url,
      })
    }else{
      util.prompt(that,res.msg);
    }
  },

})
