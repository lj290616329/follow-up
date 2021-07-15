// pages/personal/review/form.js
var that;
const app = getApp();
Page({
  data: {
    compare:app.globalData.compare,
    information:{
      examine:{
        "cbc":[],
        "biochemistry":[],
        "dic":[],
        "swelling":[],
        "bMode":[],
        "ct":[],
        "mri":[]
      }  
    },  
    
  },
  onLoad: function (options) {
    that = this;
  },
  async uploadPic(e){    
    let key = e.currentTarget.dataset.key;
    let pics = that.data.information.examine[key];
    let res = await wx.chooseImage({
      count: 1,
      sizeType:['original', 'compressed'],
      sourceType:['album', 'camera']
    });
    console.log(res);   
   
    pics = pics.concat(res.tempFilePaths[0]);
    that.setData({
      ['information.examine.'+key]:pics
    })
    
  },
  delImg(e){
    let key = e.currentTarget.dataset.key;
    let index = e.currentTarget.dataset.index;
    let pics = that.data.information.examine[key];
    that.delModal();
    that.yes =()=>{
      pics.splice(index,1);
      that.setData({
        ['information.examine.'+key]:pics
      })
      that.delModal();
    }
  },
  delModal(){
    that.setData({
      delStatus:!that.data.delStatus
    })
  }
})