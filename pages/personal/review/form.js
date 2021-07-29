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
    explainStatus:false,
    delStatus:false,
    readEnd:wx.getStorageSync('readEnd')||0,
    information:{
      other:'',
      examine:{
        "cbc":[],
        "biochemistry":[],
        "dic":[],
        "swelling":[],
        "bmode":[],
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
  checkChange(e){
    let readEnd = e.detail.value.indexOf("1")>-1?1:0;    
    wx.setStorageSync('readEnd', readEnd);
    that.setData({
      readEnd:readEnd
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
  explainModal(){
    that.setData({
      explainStatus:!that.data.explainStatus
    })
  },
  async submit(){
    let data = that.data.information.examine;
    let pics = Object.values(data).some(item=>{
      console.log(item)
      return item.length>0;
    });

    if(!pics){
      return util.prompt(that,'请至少在一个类目下上传检测结果!');
    }
    if(!that.data.readEnd){
      return util.prompt(that,'请同意隐私协议再进行提交!');
    }

    let res = await api.addReview(that.data.information);
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