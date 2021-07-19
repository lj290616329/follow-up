var that;
const api = require('../../config/api');
Page({
  data: {
    code:0,
    index:0
  },
  async onLoad(options) {
    that = this;
    let res = await api.personalIndex({});
    that.setData({
      code:res.code,
      msg:res.msg,
      near:res.data.near,
      reply:res.data.reply
    })
  }
})