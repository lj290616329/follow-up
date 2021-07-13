layui.define(function(exports){
    let ApiConfig ={
        pathParams:function (url, params = {}) {
            return Object.keys(params).map(function(key) {
                return url.replace(/(\\)?\{([^\{\}\\]+)(\\)?\}/g, params[key])
            }).join();
        },
        uploadFileApi:"/api/upload/file/qiniu",
        adminApi:"/manager/admin",
        adminRoleApi:"/manager/admin/role",
        adminPwdApi:"/manager/admin/psd",
        doctorApi:"/manager/doctor",
        doctorAllApi:"/manager/doctors",
        doctorDetailApi:"/manager/doctor/{id}",
        informationApi:"/manager/information",
        informationDetailApi:"/manager/information/{id}",
        planListApi:"/manager/reviewPlan/{iid}",
        planApi:"/manager/reviewPlan",
        articleApi:"/manager/article",
        articleDetailApi:"/manager/article/{id}",
        reviewApi:"/manager/review"
    }
    exports('ApiConfig', ApiConfig);
});