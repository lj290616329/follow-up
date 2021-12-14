package com.tsingtec.follow.vo.req.information;

import com.tsingtec.follow.entity.Examination;
import com.tsingtec.follow.entity.mini.Doctor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author lj
 * @Date 2021/6/2 10:31
 * @Version 1.0
 */
@Data
public class InformationAddReqVO {

    @NotNull(message="姓名不能为空")
    private String name;//姓名

    private Doctor doctor;

    private String recordNo;//病历号码

    private String phone;//手机号码

    private Integer type;//病种

    private List<Examination> examination;//病理

    private String otherType;

    private String other;
}
