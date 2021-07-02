const adminApi = "/manager/admin";
const adminRoleApi = "/manager/admin/role";
const adminPwdApi = "/manager/admin/psd";
layui.define(function(exports){
    let ApiConfig ={
        adminApi,
        adminRoleApi,
        adminRoleApi,
        adminPwdApi
    }
    exports('ApiConfig', ApiConfig);
})