var that;
const util = require("../../../utils/util");
const api = require("../../../config/api");
Page({
  data: {
    code:0,
    lists:[],
    title:""
  },

  onLoad: function (options) {
    that = this;
    that.getList(1);
  },
  async getList(pageNo){
    let res = await api.informationList({pageNum:pageNo,title:that.data.title});
    console.log(res);
    that.setData({
      code:res.code,
      msg:res.msg,
      lists:that.data.lists.concat(res.data.content),
      pageNo:pageNo,
      ifEnd:that.data.last
    })
  },
  search(e){
    let value = e.detail.value;
    that.setData({
      title:value,
      lists:[]
    })
    that.getList(1);
  },
  onReachBottom(){
    if(!that.data.ifEnd){
      that.getList((that.data.pageNo+1))
    }
  }
})