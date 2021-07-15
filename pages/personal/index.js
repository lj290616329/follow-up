var that;
const util = require('../../utils/util');
const config = require('../../config/config');
Page({
  data: {
    code:0,
    index:0
  },
  async onLoad(options) {
    that = this;
    let res = await util.sendAjax(config.PersonalIndex,{},"get");
    that.setData({
      code:res.code,
      msg:res.msg,
      near:res.data.near,
      reply:res.data.reply
    })
  }
})