const config = require("../config/config")
const log = require("./log")
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
/**
 * 获取授权状态
 */
function getAuthStatus(){
  return new Promise(function (resolve, reject) {
    wx.getSetting({
      withSubscriptions: true,
      success(res){
        console.log(res);
        if (res.authSetting['scope.userInfo']) { 
          resolve(true);
        }else{
          resolve(false);
        }
      }
    })
  })
};
/**
 * 获取token
 */
const  getToken = async function(){
  console.log("获取token")
  let local_token = wx.getStorageSync('token');
  let now = new Date().getTime();
  /**
   * 判断条件
   * 1:是否为空
   * 2:是否超时
   * 3:刷新token过期时间是否超时
   */
  if(local_token && local_token.expireTime < now && local_token.refreshExpireTime > now){
    console.log("刷新token")
    let res = await refreshToken(local_token)
    return res.token;
  }
  return local_token ? local_token.token : "";
}

/**
 * 根据刷新token获取新token
 * @param {刷新token} token 
 */
 const refreshToken = function(token){
  return new Promise((resolve, reject)=>{
    wx.request({
      url: config.RefreshToken,
      data: {refreshToken:token.refreshToken},		
      method: 'GET',// 默认值GET，如果有需要改动，在options中设定其他的method值
      success: (res) => {
        console.log(res);        
        wx.getStorageSync('token', res.data.data);
        resolve(res.data.data)
      },
      fail: (err) =>{
        console.error("刷新token失败");
        reject({code:1,msg:JSON.stringify(err),data:null});
      }
    })
  })
}
/**
 * http请求接口
 * @param {接口} url 
 * @param {参数} data 
 * @param {方法} method 
 */
function sendAjax(url, data = {}, method = "GET") {
  console.log({url:url,data:data,method:method});
  log.info({url:url,data:data,method:method});
  return new Promise(function (resolve, reject) {
    wx.request({
      url: url,
      data: data,
      method: method,
      header: {
        'Content-Type': 'application/json',
        'token':getToken()
      },
      dataType:"json",
      success: function (res) {
        console.log(res);
        if(res.data.code != 0){
          log.error({param:{url:url,data:data,method:method},res:res});
        };  
        resolve(res.data);   
      },
      fail: function (err) {               
        console.log(err)
        resolve({code:-2,msg:"未知错误请稍后再试!"})
      }
    })
  });
};

function getCode() {
  return new Promise(function (resolve, reject) {
    wx.login({
      success: function (res) {
        resolve(res.code);
      },
      fail: function (err) {
        reject({code:-1,msg:"未知错误请稍后再试!"});
      }
    });      
  });
};

function getUserInfo(){
  return new Promise(function (resolve, reject) {    
    wx.getUserInfo({
      withCredentials: true,
      success: (res) => {
        wx.setStorageSync('userInfo', res.userInfo);        
        resolve(res);
      }
    })
  })
}
async function auth(){
  let code = await getCode();
  let useInfo = await getUserInfo();
  let res = await sendAjax(config.Auth,{
    code:code,
    encryptedData:useInfo.encryptedData,
    iv:useInfo.iv,
    signature:useInfo.signature,
    rawData:useInfo.rawData,
    did:wx.getStorageSync('did')
  },"post");
  if(res.code==0){
    wx.setStorageSync('token', res.data.token);
  }
  return res;
}

/**
 * 小程序直接登录获取信息
 */
const login = async function(){
  let code = await getCode();
  let res = await sendAjax(config.Login,{
    code:code
  },'get');
  if(res.code==0){
    wx.setStorageSync('token', res.data.token);
  }
  return res;
}
async function phone(data){
  let res = await sendAjax(config.Phone,data,"post");
  return res;
}

function uploadFile(filePath){
  wx.showLoading({
    title: '图片上传中,请稍后...',
  });
  return new Promise(function (resolve, reject) {    
    wx.uploadFile({
      filePath: filePath,
      name: 'file',
      url: config.Upload,
      header:{
        'token':getToken()
      },
      success:function(res){
        let data = JSON.parse(res.data);
        console.log(data)
        resolve(data);
      },
      error:function(err){
        resolve({code:-1,msg:"文件上传失败"})
      },
      complete:function(status,res,msg){
        wx.hideLoading()
      }
    })
  })  
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

module.exports = {
  formatTime,
  getAuthStatus,
  prompt,
  sendAjax,
  login,
  auth,
  getCode,
  phone,
  uploadFile
}
