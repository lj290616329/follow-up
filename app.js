require('./common');
const api = require('./config/api');
App({
  onLaunch() {
    let that = this;
    api.diseaseTree({}).then(res=>{
      that.globalData.diseaseTree = res.data;
    });
    api.diseaseAll({}).then(res=>{
      that.globalData.diseases = res.data;
    });    
  },
  globalData: {
    showModal:false,
    userInfo: null,
    quickReply:[
      '感谢上传最新复查报告，谢谢！ 如最近进入新的治疗阶段请及时反馈于个案管理师，便于调整随访计划。',
      '感谢上传最新复查报告，谢谢！ 请依照复查计划按时进行复查，并上传结果。'
    ]
  }
})
