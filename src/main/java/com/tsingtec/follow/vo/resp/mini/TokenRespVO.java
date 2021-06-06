package com.tsingtec.follow.vo.resp.mini;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * @Author lj
 * @Date 2021/5/26 16:18
 * @Version 1.0
 */
@Data
@ApiOperation("登录返回")
public class TokenRespVO {

    @ApiModelProperty("token")
    private String token;

    @ApiModelProperty("token过期时间")
    private Long expireTime;

    @ApiModelProperty("refreshToken,可用来刷新token")
    private String refreshToken;

    @ApiModelProperty("refreshToken过期时间")
    private Long refreshExpireTime;

    public TokenRespVO(String token, Long expireTime, String refreshToken, Long refreshExpireTime){
        this.refreshToken = refreshToken;
        this.token = token;
        this.expireTime = expireTime;
        this.refreshExpireTime = refreshExpireTime;
    };
}

