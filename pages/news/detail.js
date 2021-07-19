var util = require('../../utils/util');
var api = require('../../config/api');
var that;
Page({
  data: { 
    code:0,   
    tags:[],
    newsLists:{},
    sTop:0
  },
  async onLoad(options) {
    that = this;
    let res = await api.articleDetail({id:options.id});
    let article = res.data;
    article.content = article.content.replace(/\<img/gi, '<img style="max-width:100%;height:auto;display:block;margin:0 auto;"');
    that.setData({
      code:res.code,
      msg:res.msg,
      article:article
    })
    wx.setNavigationBarTitle({
      title: res.data.title
    })
  }
})