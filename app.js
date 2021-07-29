require('./common');
App({
  onLaunch() {

  },
  globalData: {
    userInfo: null,
    compare:{
      'cbc':'血常规',
      'biochemistry':'生化',
      'dic':'凝血',
      'swelling':'肿标',
      'bmode':'B超',
      'ct':'CT',
      'mri':'MRI',
      '其他':'其他'
    },
    types:[
      "肝脏肿瘤-HCC",
      "肝脏肿瘤-ICC",
      "肝脏肿瘤-其他",
      "肝道肿瘤-肝门",
      "肝道肿瘤-肝门",
      "肝道肿瘤-其他",
      "胰腺肿瘤-胰腺",
      "胰腺肿瘤-其他",
      "其他"
    ],
    quickReply:[
      '感谢上传最新复查报告，谢谢！ 如最近进入新的治疗阶段请及时反馈于个案管理师，便于调整随访计划。',
      '感谢上传最新复查报告，谢谢！ 请依照复查计划按时进行复查，并上传结果。'
    ]
  }
})
