// pages/personal/review/form.js
var that;
const util = require('../../../../utils/util');
Page({
  data: {
    code:0,
    examination:[{
      name:'血常规',
      enName:"cbc",
      pics:["http://gridpic.tsing-tec.com/e0331c1c-49e4-4efd-b98c-701371a4978e.png","http://gridpic.tsing-tec.com/f6debe8d-9519-4e22-bdef-174f7d6b1b19.png"]
    },
    {
      name:"生化",
      enName:"biochemistry",
      pics:["http://gridpic.tsing-tec.com/7ad006fe-66ae-4386-95bb-4007fe8d9314.png","http://gridpic.tsing-tec.com/f3085581-04f4-48a4-9f84-cc72e78aa873.png","http://gridpic.tsing-tec.com/cd2d30b9-95d7-4cb3-9894-b96261036548.png"]
    },{
      name:"肿标",
      enName:"swelling",
      pics:["http://gridpic.tsing-tec.com/fdad0baa-af72-4cf2-a430-2f3abf7b3fb8.png"]
    },
    {
      enName:'bMode',
      name:'B超',
      pics:["http://gridpic.tsing-tec.com/a160ef3c-fc15-4c56-be6e-ca8839c5e36a.png"]
    },{
      enName:'ct',
      name:'CT',
      pics:["http://gridpic.tsing-tec.com/5720d520-6869-41d3-81ed-e5e3d8d572f8.png","http://gridpic.tsing-tec.com/065a3cf9-9037-4caa-a6ed-d67f916831c6.png"]
    },{
      enName:'mri',
      name:'MRI',
      pics:["http://gridpic.tsing-tec.com/80cae499-33ab-416a-9e6f-3140af7d53b8.png"]
    }]
    
  },
  onLoad: function (options) {
    that = this;
  },
  async uploadPic(e){
    console.log(e);    
    let key = e.currentTarget.dataset.key;
    let pics = that.data.examination[key].pics;
    let res = await wx.chooseImage({
      count: 1,
      sizeType:['original', 'compressed'],
      sourceType:['album', 'camera']
    });
    util.prompt(that,"未授权模式,图片不会进行上传!")
   
    pics = pics.concat(res.tempFilePaths[0]);
    let set_val = 'examination['+key+'].pics';
    that.setData({
      [set_val]:pics
    })
  },
  delImg(e){
    let key = e.currentTarget.dataset.key;
    let index = e.currentTarget.dataset.index;
    let pics = that.data.examination[key].pics;
    that.delModal();
    that.yes =()=>{
      pics.splice(index,1);
      let set_val = 'examination['+key+'].pics';
      that.setData({
        [set_val]:pics
      })
      that.delModal();
    }
  },
  delModal(){
    that.setData({
      delStatus:!that.data.delStatus
    })
  }
})