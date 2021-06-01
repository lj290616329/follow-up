
import WxValidate from '../../utils/WxValidate';
var util = require('../../utils/util') ;
var config = require('../../config/config');
const app = getApp();
var that; 
Page({
  data: {
    phone:"",
    name:"",
    recordNo:"",
    ifDoctor:true
  },
  async onLoad(options) {
    console.log(options)
    that = this;
    let did = options.did || wx.getStorageSync('did');
    if(did==undefined || did==""){
      return util.prompt(that,"请扫面二维码进入");
    }
    wx.setStorageSync('did', did);
    if(did>0){
      that.setData({
        ifDoctor:false
      })
    }
    that.initValidate();    
    
    let res = await util.login();
    console.log(res);
    if(res.code==0){
      app.globalData.ifInformation = res.data.ifInformation;
      if(res.data.ifDoctor){
        //医生用户
        wx.reLaunch({
          url: '/pages/doctor/index',
        })
      }else{
        //个人用户已填写个人信息
        if(res.data.ifInformation){
          wx.reLaunch({
            url: '/pages/personal/index',
          })
        }else{
          that.informationModal();            
        }
      }          
    }
        
  },
  initValidate() {
    let rules = { 
      phone:{
        required:true
      }
    };
    let messages = {      
      phone:{
        required:'请授权获取手机号'
      }
    };
    if(!that.data.ifDoctor){
      rules.name = {
        required: true,
        minlength:2
      };
      rules.recordNo = {
        required: true
      };
      messages.name = {
        required: '请填写姓名',
        minlength:'请输入正确的名称'
      };
      messages.recordNo={
        required: '请输入病历号'
      };
    }
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


    // wx.getUserProfile({
    //   desc: '用于完善会员资料', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
    //   success: (res) => {
    //     console.log(res);
    //     wx.setStorageSync('userInfo', res.userInfo);        
    //     resolve(res);
    //   }
    // }) 
      // let res = await util.auth();
      // console.log(res)
      // if(res.code==0){
      //   that.informationModal();
      // }
    
  },
  informationModal(){
    that.setData({
      informationStatus:!that.data.informationStatus
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
    let path = that.data.ifDoctor?config.DoctorAuth:config.InformationAuth;
    let url = that.data.ifDoctor?'/pages/doctor/index':'/pages/personal/index';
    let res = await util.sendAjax(path,params,"post")
    if(res.code==0){
      wx.reLaunch({
        url: url,
      })
    }else{
      util.prompt(that,res.msg);
    }
  }
})
