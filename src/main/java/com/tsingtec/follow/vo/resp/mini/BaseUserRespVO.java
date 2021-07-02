package com.tsingtec.follow.vo.resp.mini;

import lombok.Data;

@Data
public class BaseUserRespVO {
    private Boolean ifDoctor=false;//是否填写个人信息
    private TokenRespVO token;//token
    private Boolean ifAuth = false;
}