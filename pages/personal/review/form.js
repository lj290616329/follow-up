// pages/personal/review/form.js
const util = require("../../../utils/util");
const api = require("../../../config/api")
var that;
Page({
  data: {
    code:0,
    change:false,
    explainStatus:false,
    delStatus:false,
    readEnd:wx.getStorageSync('readEnd')||0
  },
  onLoad:async function (options) {
    that = this;
    let res = await api.reviewPlanDetail({id:options.id});
    that.setData({
      reviewPlan:res.data
    })
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
      ['reviewPlan.other']:e.detail.value
    })
  },
  async uploadPic(e){    
    let key = e.currentTarget.dataset.key;
    let pics = that.data.reviewPlan.examination[key].pics;
    let res = await wx.chooseImage({
      count: 1,
      sizeType:['original', 'compressed'],
      sourceType:['album', 'camera']
    });
    let data = await api.uploadFile(res.tempFilePaths[0]);
    if(data.code==0){
      pics = pics.concat(data.data.src);
      let set_val = 'reviewPlan.examination['+key+'].pics';
      that.setData({
        [set_val]:pics
      })      
    }else{
      util.prompt(that,res.msg);
    }
  },
  delImg(e){
    let key = e.currentTarget.dataset.key;
    let index = e.currentTarget.dataset.index;
    let pics = that.data.reviewPlan.examination[key].pics;
    that.delModal();
    that.yes =()=>{
      pics.splice(index,1);
      let set_val = 'reviewPlan.examination['+key+'].pics';
      that.setData({
        [set_val]:pics
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
    let data = that.data.reviewPlan.examination;
    let pics = data.filter(item => {
      return item.pics.length>0;
    })
    if(pics.length<=0){
      return util.prompt(that,'请至少在一个类目下上传检测结果!');
    }
    if(!that.data.readEnd){
      return util.prompt(that,'请同意隐私协议再进行提交!');
    }
    let res = await api.reviewPlanUpdate(that.data.reviewPlan);
    if(res.code==0){
      wx.reLaunch({
        url: '/pages/personal/index',
      });
    }else{
      util.prompt(that,res.msg);
    }
  }
})