// pages/personal/review/form.js
const util = require("../../../utils/util");
const api = require("../../../config/api")
var that;
const app = getApp();
Page({
  data: {
    code:0,
    compare:app.globalData.compare,
    change:false,
    information:{
      other:'',
      examine:{
        "cbc":[],
        "biochemistry":[],
        "dic":[],
        "swelling":[],
        "bMode":[],
        "ct":[],
        "mri":[]
      }  
    }
  },
  onLoad: function (options) {
    that = this;
    let information = wx.getStorageSync('formData');
    if(information){
      that.setData({
        information:information
      })
    }
    that.setData({
      ['information.id']:options.id
    });
  },
  otherChange(e){
    that.setData({
      change:true,
      ['information.other']:e.detail.value
    })
  },
  async uploadPic(e){    
    let key = e.currentTarget.dataset.key;
    let pics = that.data.information.examine[key];
    let res = await wx.chooseImage({
      count: 1,
      sizeType:['original', 'compressed'],
      sourceType:['album', 'camera']
    });
    console.log(res)
    let data = await api.uploadFile(res.tempFilePaths[0]);
    if(data.code==0){
      pics = pics.concat(data.data.src);
      that.setData({
        change:true,
        ['information.examine.'+key]:pics
      })      
    }else{
      util.prompt(that,res.msg);
    }
  },
  delImg(e){
    let key = e.currentTarget.dataset.key;
    let index = e.currentTarget.dataset.index;
    let pics = that.data.information.examine[key];
    that.delModal();
    that.yes =()=>{
      pics.splice(index,1);
      that.setData({
        change:true,
        ['information.examine.'+key]:pics
      })
      that.delModal();
    }
  },
  delModal(){
    that.setData({
      delStatus:!that.data.delStatus
    })
  },
  async submit(){
    let res = await api.addReview(that.data.information);
    console.log(res);
    if(res.code==0){
      wx.removeStorageSync('formData');
      that.setData({
        change:false
      });
      wx.reLaunch({
        url: '/pages/personal/index',
      });
    }else{
      util.prompt(that,res.msg);
    }
  },
  draft(){
    wx.setStorageSync('formData', that.data.information);
    wx.navigateBack();
  },
  onUnload(){
    if(that.data.change){
      wx.setStorageSync('formData', that.data.information);
    }
  },
})