var that;
const util = require('../../utils/util');
const config = require('../../config/config');
Page({
  data: {
    title:'',
    lists:[]
  },
  onLoad(options) {
    that = this;
    that.getList(1);
  },
  async getList(pageNo){
    let res = await util.sendAjax(config.Article,{pageNum:pageNo,title:that.data.title},'get');
    console.log(res);
    that.setData({
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