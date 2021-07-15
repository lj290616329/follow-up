const config = require("../../../config/config");
const util = require("../../../utils/util");
var that;
const app = getApp();
Page({
  data: {
    code:0,
    types:app.globalData.types,
    compare:app.globalData.compare,
    type1:[[0,0],[0,1],[0,2]
      ,[1,0],[1,1],[1,2] 
      ,[2,0],[2,1]
      ,[3,0] 
    ],
    type2:[
      [{name:"HCC",value:"0"},{name:"ICC",value:"1"},{name:"其他",value:"2"}],
      [{name:"肝门",value:"3"},{name:"胆囊",value:"4"},{name:"其他",value:"5"}],
      [{name:"胰腺",value:"6"},{name:"其他",value:"7"}],
      [{name:"其他",value:"8"}]
    ],    
    type:[0,0],
    delStatus:false    
  },
  async onLoad(options) {
    that = this;
    let res = await util.sendAjax(config.InformationById+options.id,{},'get');
    let information = res.data.information;
    console.log(information);
    
    let type1 = that.data.type1;
    let type = type1[information.type];
    console.log(type);
    let type2 = that.data.type2;
    let multiArray = [
      [{name:"肝脏肿瘤"},{name:"肝道肿瘤"},{name:"胰腺肿瘤"},{name:"其他"}]
    ];
    multiArray.push(type2[type[0]]);
    that.setData({
      code:res.code,
      msg:res.msg,
      information:information,
      examine:information.examine,
      multiArray:multiArray,
      type:type
    })
  },
  async uploadPic(e){
    let data = await that.upload();
    let key = e.currentTarget.dataset.key;
    let pics = that.data.information.examine[key];
    console.log(data);
    if(data.code==0){
      pics.push(data.data.src);
      that.setData({
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
        ['information.examine.'+key]:pics
      })
      that.delModal();
    }
  },
  bindPickerChange: function (e) {
    let type2 = that.data.type2;
    that.setData({
      type: e.detail.value,
      ['information.type']:type2[e.detail.value[0]][e.detail.value[1]].value
    })
  },
  bindColumnChange: function (e) {
    console.log(e);
    console.log('修改的列为', e.detail.column, '，值为', e.detail.value);
    var data = {
      multiArray: that.data.multiArray,
      type: that.data.type
    };
    let type2 = that.data.type2;
    if (e.detail.column==0){
      data.type[0] = e.detail.value;
      data.type[1] = 0;
      data.multiArray[1] = type2[e.detail.value];         
      that.setData(data);
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
  async uploadCover(e){
    let data = await that.upload();
    let pics = that.data.information.pathology;
    if(data.code==0){
      pics.push(data.data.src);
      that.setData({
        ['information.pathology']:pics
      })
    }
  },
  delPic(e){    
    let index = e.currentTarget.dataset.index;
    let pics = that.data.information.pathology;
    that.delModal();
    that.yes =()=>{
      pics.splice(index,1);
      that.setData({
        ['information.pathology']:pics
      })
      that.delModal();
    }
  },
  delModal(){
    that.setData({
      delStatus:!that.data.delStatus
    })
  },
  showpic(e){
    let urls = e.currentTarget.dataset.urls;
    let current = e.currentTarget.dataset.current;
    wx.previewImage({
      urls: urls,
      current:current
    })
  },
  async submit() {
    let information = that.data.information;
    let data = {
      id:information.id,
      type:information.type,
      pathology:information.pathology,
      examine:information.examine
    }
    let res = await util.sendAjax(config.Information,data,"put");
    console.log(res)
    if(res.code==0){
      wx.reLaunch({
        url: '/pages/doctor/index',
      })
    }else{
      util.prompt(that,res.msg);
    }
  }
})