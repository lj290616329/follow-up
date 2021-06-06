package com.tsingtec.follow.vo.req.mini;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author lj
 * @Date 2021/5/28 17:06
 * @Version 1.0
 */
@Data
public class WxLoginReqVO {

    @ApiModelProperty(value = "用户登录获取的code")
    private String code;            // 用户登录获取的code

    @ApiModelProperty(value = "包括敏感数据在内的完整用户信息的加密数据")
    private String encryptedData;   // 包括敏感数据在内的完整用户信息的加密数据

    @ApiModelProperty(value = "加密算法的初始向量")
    private String iv;              // 加密算法的初始向量

    @ApiModelProperty(value = "数据签名")
    private String signature;       // 数据签名

    @ApiModelProperty(value = "用户基本信息")
    private String rawData;         // 用户基本信息

    @NotNull(message = "医生id不能为空")
    private Integer did;

}
