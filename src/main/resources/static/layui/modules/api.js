layui.define(['jquery','ApiConfig'],function(exports){
    let ApiConfig = layui.ApiConfig;
    let pathParams = function (url, params = {}) {
        return Object.keys(params).map(function(key) {
            return url.replace(/(\\)?\{([^\{\}\\]+)(\\)?\}/g, params[key])
        }).join();
    };

    var request =  function(url,params,type){
        return new Promise((resolve, reject) => {
            var roleSaveLoading = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});
            var $ = layui.jquery;
            $.ajax({
                url: url,
                cache: false,
                data: params,
                type: type == undefined ? "GET" : type,
                contentType: 'application/json; charset=UTF-8',
                dataType: "json",
                success: function (res) {
                    console.log(res);
                    top.layer.close(roleSaveLoading);
                    if (res.code == 0) {
                        resolve(res);
                    } else if (res.code == 400) {
                        top.layer.msg(res.msg, {
                            offset: 't',
                            anim: 6,
                        });
                        reject(res);
                    } else if (res.code == 403) {
                        window.location.href = "/index/403"
                        reject(res);
                    } else if (res.code == -1) {
                        top.layer.msg(res.msg, {
                            offset: 't',
                            anim: 6,
                        }, function () {
                            top.window.location.href = "/index/login"
                        });
                        reject(res);
                    } else if (res.code == 500) {
                        top.layer.msg(res.msg, {
                            offset: 't',
                            anim: 6,
                        }, function () {
                            window.location.href = "/index/500"
                        });
                        reject(res);
                    } else {
                        top.layer.msg(res.msg, {
                            offset: 't',
                            anim: 6,
                        }, function () {
                            window.location.href = "/index/500"
                        });
                        reject(res);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    top.layer.close(roleSaveLoading);
                    if (XMLHttpRequest.status == 404) {
                        location.href = "/index/404";
                    } else {
                        layer.msg("服务器好像除了点问题！请稍后试试", function () {
                            window.location.href = "/index/500"
                        });
                    }
                    reject(XMLHttpRequest);
                },
                complete:function () {
                    top.layer.close(roleSaveLoading);
                }
            });
        })
    };

    var api = {
        getAdmins:async function(data){
            let res = await request(ApiConfig.adminApi,data,"get");
            return res
        },
        addAdmin:async function(data){
            let res = await request(ApiConfig.adminApi,data,"POST");
            return res;
        },
        updateAdmin:async function(data){
            let res = await request(ApiConfig.adminApi,data,"PUT");
            return res;
        },
        deleteAdmin:async function(data){
            let res = await request(ApiConfig.adminApi,data,"DELETE");
            return res;
        },
        getAdminRole:async function(data){
            let res = await request(ApiConfig.adminRoleApi,data,"GET");
            return res;
        },
        setAdminRole:async function(data){
            let res = await request(ApiConfig.adminRoleApi,data,"PUT");
            return res;
        },
        updatePwd:async function(data){
            let res = await request(ApiConfig.adminPwdApi,data,"PUT");
            return res;
        }
    };
    exports('Api', api);
})



