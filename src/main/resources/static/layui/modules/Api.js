layui.define(['jquery','ApiConfig'],function(exports){
    let ApiConfig = layui.ApiConfig;
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
                            top.window.location.href = ""
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
        getAdmins: data=>request(ApiConfig.adminApi,data,"get"),
        addAdmin:data=>request(ApiConfig.adminApi,data,"POST"),
        updateAdmin:data=>request(ApiConfig.adminApi,data,"PUT"),
        deleteAdmin:data=>request(ApiConfig.adminApi,data,"DELETE"),
        getAdminRole:data=>request(ApiConfig.adminRoleApi,data,"GET"),
        setAdminRole:data=>request(ApiConfig.adminRoleApi,data,"PUT"),
        updatePwd:data=>request(ApiConfig.adminPwdApi,data,"PUT"),
        //医生
        getDoctor:data=>request(ApiConfig.doctorApi,data,"GET"),
        //医生
        getAllDoctor:data=>request(ApiConfig.doctorAllApi,data,"GET"),
        doctorDetail:data=>request(ApiConfig.pathParams(ApiConfig.doctorDetailApi,data),"","GET"),
        addDoctor:data=>request(ApiConfig.doctorApi,data,"POST"),
        updateDoctor:data=>request(ApiConfig.doctorApi,data,"PUT"),
        deleteDoctor:data=>request(ApiConfig.doctorApi,data,"DELETE"),
        //用户信息
        getInformation:data=>request(ApiConfig.informationApi,data,"GET"),
        informationDetail:data=>request(ApiConfig.pathParams(ApiConfig.informationDetailApi,data),"","GET"),
        addInformation:data=>request(ApiConfig.informationApi,data,"POST"),
        updateInformation:data=>request(ApiConfig.informationApi,data,"PUT"),
        deleteInformation:data=>request(ApiConfig.informationApi,data,"DELETE"),
        //复查计划
        getPlan:data=>request(ApiConfig.planApi,data,"GET"),
        addPlan:data=>request(ApiConfig.planApi,data,"POST"),
        updatePlan:data=>request(ApiConfig.planApi,data,"PUT"),
        deletePlan:data=>request(ApiConfig.planApi,data,"DELETE"),
        //图文计划
        getArticle:data=>request(ApiConfig.articleApi,data,"GET"),
        articleDetail:data=>request(ApiConfig.pathParams(ApiConfig.articleDetailApi,data),data,"GET"),
        addArticle:data=>request(ApiConfig.articleApi,data,"POST"),
        updateArticle:data=>request(ApiConfig.articleApi,data,"PUT"),
        deleteArticle:data=>request(ApiConfig.articleApi,data,"DELETE"),
        reviewDetail:data=>request(ApiConfig.reviewApi,data,"GET")
    };
    exports('Api', api);
});



