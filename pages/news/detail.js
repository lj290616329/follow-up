var util = require('../../utils/util');
var config = require('../../config/config');
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
    console.log(options)
    let id = options.id;    
    let res = await util.sendAjax(config.ArticleDetail+id,{},"get");
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