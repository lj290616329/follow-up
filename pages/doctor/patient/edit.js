const api = require("../../../config/api");
const util = require("../../../utils/util");
var that;
const app = getApp();
var searchTree = function(tree,id){
  let type1 = [];
  let type2 = [];
  let type = [0,0];
  let multiArray = [];
  let disease = {};
  tree.forEach((parent,i)=>{
    type1.push(parent);
    type2.push(parent.children);
    if(parent.id==id){
      disease = parent;
      type[0] = i;
    };
    for(let j=0;j<parent.children.length;j++){
      if(parent.children[j].id == id){
        disease = parent.children[j];
        type[1] = j;
      }
    }    
  });
  multiArray.push(type1);
  multiArray.push(type2[type[1]]);
  return {type:type,type1:type1,type2:type2,multiArray:multiArray,disease:disease};
};
Page({
  data: {
    code:0,  
    examinationStatus:false,
    delStatus:false    
  },
  async onLoad(options) {
    that = this;
    let res = await api.informationDetail({id:options.id});
    let checks = await api.checkAll({});
    let information = res.data.information;
    let diseaseTree = app.globalData.diseaseTree;
    
    let typeArray = searchTree(diseaseTree,information.type);
    console.log(typeArray);
    that.setData({
      checks:checks.data,
      information:information,
      diseaseTree:diseaseTree,
      type:typeArray.type,
      type1:typeArray.type1,
      type2:typeArray.type2,
      multiArray:typeArray.multiArray,
      disease:typeArray.disease
    })
  },
  async uploadPic(e){    
    let key = e.currentTarget.dataset.key;
    let pics = that.data.information.examination[key].pics;
    let res = await wx.chooseImage({
      count: 1,
      sizeType:['original', 'compressed'],
      sourceType:['album', 'camera']
    });
    let data = await api.uploadFile(res.tempFilePaths[0]);
    if(data.code==0){
      pics = pics.concat(data.data.src);
      let set_val = 'information.examination['+key+'].pics';
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
    let pics = that.data.information.examination[key].pics;
    that.delModal();
    that.yes =()=>{
      pics.splice(index,1);
      let set_val = 'information.examination['+key+'].pics';
      that.setData({
        [set_val]:pics
      })  
      that.delModal();
    }
  },
  bindPickerChange: function (e) {
    console.log(e);
    let type = e.detail.value;
    let treeNode = that.data.diseaseTree[type[0]];
    let infoType = 0;
    let children = treeNode.children;
    let disease = {};
    if(children.length>0){
      infoType = children[type[1]].id;
      disease = children[type[1]];
    }else{
      infoType = treeNode.id;
      disease = treeNode;
    }
    that.setData({
      type:type,
      disease:disease,
      ['information.type']:infoType
    })
  },
  bindColumnChange: function (e) {
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
  showTips(){
    util.prompt(that,"病种类型请在网页端修改");
  },
  checkChange(e){
    console.log(e);
    let examination = that.data.information.examination||[];
    let enName = e.currentTarget.dataset.enname;
    let name = e.currentTarget.dataset.name;
    if(e.detail.value){
      examination.push({
        name:name,
        enName:enName,
        pics:[]
      })
    }else{
      let index = examination.findIndex(e=>e.enName==enName);
      examination.splice(index,1);
    }
    that.setData({
      ['information.examination']:examination
    })
  },
  otherTypeChange(e){
    console.log(e);
    that.setData({
      ['information.otherType']:e.detail.value
    })
  },
  async upload(){
    let res = await wx.chooseImage({
      count: 1,
      sizeType:['original', 'compressed'],
      sourceType:['album', 'camera']
    });
    console.log(res)
    let data = await api.uploadFile(res.tempFilePaths[0]);
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
  examinationModal(){
    that.setData({
      examinationStatus:!that.data.examinationStatus
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
      examination:information.examination,
      otherType:information.otherType
    }
    let res = await api.informationUpdata(data);
    if(res.code==0){
      wx.reLaunch({
        url: '/pages/doctor/index',
      })
    }else{
      util.prompt(that,res.msg);
    }
  }
})