var that;
const config = require("../../config/config");
const util = require("../../utils/util");
import WxValidate from '../../utils/WxValidate';
Page({
  data: {
    code:0,
  },
  initValidate() {
    let rules = { 
      name:{
        required:true
      },
      pic:{
        required:true
      },
      goodAt:{
        required:true
      },
      des:{
        required:true
      }
    };
    let messages = {      
      name:{
        required:'请输入姓名'
      },
      pic:{
        required:'请上传头像'
      },
      goodAt:{
        required:'请输入擅长'
      },
      des:{
        required:'请输入个人简介'
      }
    };    
    that.WxValidate = new WxValidate(rules, messages)
  },
  async onLoad(options) {
    that = this;
    let res =await util.sendAjax(config.DoctorInfo,{},"get");
    that.setData({
      code:res.code,
      msg:res.msg,
      doctor:res.data
    })
    that.initValidate()
  },
  async uploadPic(e){
    let data = await that.upload();
    if(data.code==0){      
      that.setData({
        ['doctor.pic']:data.data.src
      })
    }
  },
  async upload(){
    let res = await wx.chooseImage({
      count: 1,
      sizeType:['original', 'compressed'],
      sourceType:['album', 'camera']
    });
    console.log(res)
    let data = await util.uploadFile(res.tempFilePaths[0]);
    return data;
  },
  async submitForm(e){
    const params = e.detail.value;
    console.log(params)
    //校验表单
    if (!that.WxValidate.checkForm(params)) {
      const error = that.WxValidate.errorList[0];
      return util.prompt(that,error.msg);
    }
    let res = await util.sendAjax(config.DoctorInfo,params,"put");
    if(res.code==0){
      wx.navigateBack();
    }
  }
})