var that;
const util = require("../../utils/util");
Page({
  data: {
    showModal:false,
    replyStatus:false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    lists:[{
      avatarUrl:"http://gridpic.tsing-tec.com/11554e8d-998c-4831-9b4e-760348157800.png",
      nickName:"1"
    }],
    compare:{
      cbc:'血常规',
      biochemistry:'生化',
      dic:'凝血',
      swelling:'肿标',
      bMode:'B超',
      ct:'CT',
      mri:'MRI'
    },
    replys:[
      {
        id:1,
        maUser:{
          nickName:'张三',
          avatarUrl:"http://gridpic.tsing-tec.com/11554e8d-998c-4831-9b4e-760348157800.png"
        },
        createTime:"2020年11月10日 12:13:14",
        review:{
          examine:{
            "cbc":["http://gridpic.tsing-tec.com/e0331c1c-49e4-4efd-b98c-701371a4978e.png","http://gridpic.tsing-tec.com/f6debe8d-9519-4e22-bdef-174f7d6b1b19.png"],
            "biochemistry":["http://gridpic.tsing-tec.com/7ad006fe-66ae-4386-95bb-4007fe8d9314.png","http://gridpic.tsing-tec.com/f3085581-04f4-48a4-9f84-cc72e78aa873.png","http://gridpic.tsing-tec.com/cd2d30b9-95d7-4cb3-9894-b96261036548.png"],
            "dic":["http://gridpic.tsing-tec.com/90635da4-60bb-4da1-873d-d8e392e500cb.png"],
            "swelling":["http://gridpic.tsing-tec.com/fdad0baa-af72-4cf2-a430-2f3abf7b3fb8.png"],
            "bMode":["http://gridpic.tsing-tec.com/a160ef3c-fc15-4c56-be6e-ca8839c5e36a.png"],
            "ct":["http://gridpic.tsing-tec.com/5720d520-6869-41d3-81ed-e5e3d8d572f8.png","http://gridpic.tsing-tec.com/065a3cf9-9037-4caa-a6ed-d67f916831c6.png",],
            "mri":["http://gridpic.tsing-tec.com/80cae499-33ab-416a-9e6f-3140af7d53b8.png"]
          }
        }
      },
      {
        id:2,
        maUser:{
          nickName:'李四',
          avatarUrl:"http://gridpic.tsing-tec.com/11554e8d-998c-4831-9b4e-760348157800.png"
        },
        createTime:"2020年11月10日 12:13:14",
        review:{
          examine:{
            "cbc":["http://gridpic.tsing-tec.com/e0331c1c-49e4-4efd-b98c-701371a4978e.png","http://gridpic.tsing-tec.com/f6debe8d-9519-4e22-bdef-174f7d6b1b19.png"],
            "biochemistry":["http://gridpic.tsing-tec.com/7ad006fe-66ae-4386-95bb-4007fe8d9314.png","http://gridpic.tsing-tec.com/f3085581-04f4-48a4-9f84-cc72e78aa873.png","http://gridpic.tsing-tec.com/cd2d30b9-95d7-4cb3-9894-b96261036548.png"],
            "dic":["http://gridpic.tsing-tec.com/90635da4-60bb-4da1-873d-d8e392e500cb.png"],
            "swelling":["http://gridpic.tsing-tec.com/fdad0baa-af72-4cf2-a430-2f3abf7b3fb8.png"],
            "bMode":["http://gridpic.tsing-tec.com/a160ef3c-fc15-4c56-be6e-ca8839c5e36a.png"],
            "ct":["http://gridpic.tsing-tec.com/5720d520-6869-41d3-81ed-e5e3d8d572f8.png","http://gridpic.tsing-tec.com/065a3cf9-9037-4caa-a6ed-d67f916831c6.png",],
            "mri":["http://gridpic.tsing-tec.com/80cae499-33ab-416a-9e6f-3140af7d53b8.png"]
          }
        }
      },
      {
        id:3,
        maUser:{
          nickName:'王五',
          avatarUrl:"http://gridpic.tsing-tec.com/11554e8d-998c-4831-9b4e-760348157800.png"
        },
        createTime:"2020年11月10日 12:13:14",
        review:{
          examine:{
            "cbc":["http://gridpic.tsing-tec.com/e0331c1c-49e4-4efd-b98c-701371a4978e.png","http://gridpic.tsing-tec.com/f6debe8d-9519-4e22-bdef-174f7d6b1b19.png"],
            "biochemistry":["http://gridpic.tsing-tec.com/7ad006fe-66ae-4386-95bb-4007fe8d9314.png","http://gridpic.tsing-tec.com/f3085581-04f4-48a4-9f84-cc72e78aa873.png","http://gridpic.tsing-tec.com/cd2d30b9-95d7-4cb3-9894-b96261036548.png"],
            "dic":["http://gridpic.tsing-tec.com/90635da4-60bb-4da1-873d-d8e392e500cb.png"],
            "swelling":["http://gridpic.tsing-tec.com/fdad0baa-af72-4cf2-a430-2f3abf7b3fb8.png"],
            "bMode":["http://gridpic.tsing-tec.com/a160ef3c-fc15-4c56-be6e-ca8839c5e36a.png"],
            "ct":["http://gridpic.tsing-tec.com/5720d520-6869-41d3-81ed-e5e3d8d572f8.png","http://gridpic.tsing-tec.com/065a3cf9-9037-4caa-a6ed-d67f916831c6.png",],
            "mri":["http://gridpic.tsing-tec.com/80cae499-33ab-416a-9e6f-3140af7d53b8.png"]
          }
        }
      },
      {
        id:4,
        maUser:{
          nickName:'赵六',
          avatarUrl:"http://gridpic.tsing-tec.com/11554e8d-998c-4831-9b4e-760348157800.png"
        },
        createTime:"2020年11月10日 12:13:14",
        review:{
          examine:{
            "cbc":["http://gridpic.tsing-tec.com/e0331c1c-49e4-4efd-b98c-701371a4978e.png","http://gridpic.tsing-tec.com/f6debe8d-9519-4e22-bdef-174f7d6b1b19.png"],
            "biochemistry":["http://gridpic.tsing-tec.com/7ad006fe-66ae-4386-95bb-4007fe8d9314.png","http://gridpic.tsing-tec.com/f3085581-04f4-48a4-9f84-cc72e78aa873.png","http://gridpic.tsing-tec.com/cd2d30b9-95d7-4cb3-9894-b96261036548.png"],
            "dic":["http://gridpic.tsing-tec.com/90635da4-60bb-4da1-873d-d8e392e500cb.png"],
            "swelling":["http://gridpic.tsing-tec.com/fdad0baa-af72-4cf2-a430-2f3abf7b3fb8.png"],
            "bMode":["http://gridpic.tsing-tec.com/a160ef3c-fc15-4c56-be6e-ca8839c5e36a.png"],
            "ct":["http://gridpic.tsing-tec.com/5720d520-6869-41d3-81ed-e5e3d8d572f8.png","http://gridpic.tsing-tec.com/065a3cf9-9037-4caa-a6ed-d67f916831c6.png",],
            "mri":["http://gridpic.tsing-tec.com/80cae499-33ab-416a-9e6f-3140af7d53b8.png"]
          }
        }
      },
      {
        id:5,
        maUser:{
          nickName:'孙七',
          avatarUrl:"http://gridpic.tsing-tec.com/11554e8d-998c-4831-9b4e-760348157800.png"
        },
        createTime:"2020年11月10日 12:13:14",
        review:{
          examine:{
            "cbc":["http://gridpic.tsing-tec.com/e0331c1c-49e4-4efd-b98c-701371a4978e.png","http://gridpic.tsing-tec.com/f6debe8d-9519-4e22-bdef-174f7d6b1b19.png"],
            "biochemistry":["http://gridpic.tsing-tec.com/7ad006fe-66ae-4386-95bb-4007fe8d9314.png","http://gridpic.tsing-tec.com/f3085581-04f4-48a4-9f84-cc72e78aa873.png","http://gridpic.tsing-tec.com/cd2d30b9-95d7-4cb3-9894-b96261036548.png"],
            "dic":["http://gridpic.tsing-tec.com/90635da4-60bb-4da1-873d-d8e392e500cb.png"],
            "swelling":["http://gridpic.tsing-tec.com/fdad0baa-af72-4cf2-a430-2f3abf7b3fb8.png"],
            "bMode":["http://gridpic.tsing-tec.com/a160ef3c-fc15-4c56-be6e-ca8839c5e36a.png"],
            "ct":["http://gridpic.tsing-tec.com/5720d520-6869-41d3-81ed-e5e3d8d572f8.png","http://gridpic.tsing-tec.com/065a3cf9-9037-4caa-a6ed-d67f916831c6.png",],
            "mri":["http://gridpic.tsing-tec.com/80cae499-33ab-416a-9e6f-3140af7d53b8.png"]
          }
        }
      }      
    ],
    quickReply:[
      '感谢上传最新复查报告，谢谢！ 如最近进入新的治疗阶段请及时反馈于个案管理师，便于调整随访计划。',
      '感谢上传最新复查报告，谢谢！ 请依照复查计划按时进行复查，并上传结果。'
    ]
  },
  switchUser(){
    wx.reLaunch({
      url: '/pages/personal/index',
    })
  },
  async onLoad(options){
    that = this;   
    let lists=[];
    for(let i=0;i<10;i++){
      lists.push({
        avatarUrl:"http://gridpic.tsing-tec.com/11554e8d-998c-4831-9b4e-760348157800.png",
        nickName:"昵称"+i
      })
    }
    let userInfo = wx.getStorageSync('userInfo');
    that.setData({
      lists:lists,
      userInfo:userInfo
    });
  },
  userInfo(){
    wx.navigateTo({
      url: '/pages/doctor/info',
    })
  },
  quickReply(e){
    console.log(e);
    that.setData({
      reply:that.data.quickReply[e.currentTarget.dataset.index]
    })
  },
  replyModal(e){
    console.log(e)
    that.setData({
      replyStatus:!that.data.replyStatus,
      id:e.currentTarget.dataset.id
    })
  },
  reply(e){
    console.log(e)
    that.setData({
      reply:e.detail.value
    })
  },
  authModal(){
    let showModal = that.data.showModal;
    that.setData({
      showModal:!showModal
    })
  },
  showpic(e){
    wx.previewImage({
      urls: e.currentTarget.dataset.pics,
    })
  },
  submitForm(e) {
    console.log(e)
    let params = e.detail.value;
    if(params.reply==""){
      return util.prompt(that,"请输入回复内容后提交");
    }
    util.prompt(that,"提交成功");
    that.replyModal({currentTarget:{
      dataset:{}
    }});
  },
  toUrl(e){
    console.log(e);
    let url = e.currentTarget.dataset.url;
    console.log(url)
    wx.navigateTo({
      url: url
    })
  }
})