const formatTime = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()

  return `${[year, month, day].map(formatNumber).join('/')} ${[hour, minute, second].map(formatNumber).join(':')}`
}

const formatNumber = n => {
  n = n.toString()
  return n[1] ? n : `0${n}`
}

//提示
function prompt(that, msg) {
  that.setData({
    prompt: true,
    promptMsg: msg
  });
  setTimeout(function () {
    that.setData({
      prompt: false,
      promptMsg: ''
    })
  }, 1500);
};

function checkTmplId(tmplId){
  return new Promise((resolve, reject)=>{
    wx.getSetting({
      withSubscriptions: true,
    }).then(res=>{
      console.log(res)      
      if(res.subscriptionsSetting && res.subscriptionsSetting.itemSettings && res.subscriptionsSetting.itemSettings[tmplId]==='accept'){
        resolve(true);
      }else{
        resolve(false);
      }
    })
  })  
};


module.exports = {
  formatTime,
  prompt,
  checkTmplId  
}
