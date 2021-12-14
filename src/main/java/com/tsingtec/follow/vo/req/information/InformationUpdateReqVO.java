package com.tsingtec.follow.vo.req.information;

import com.tsingtec.follow.entity.Examination;
import com.tsingtec.follow.entity.mini.Doctor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author lj
 * @Date 2021/6/2 11:30
 * @Version 1.0
 */
@Data
public class InformationUpdateReqVO {

    @NotNull(message = "id不能为空")
    private Integer id;

    @NotNull(message="姓名不能为空")
    private String name;//姓名

    private Doctor doctor;

    private String recordNo;//病历号码

    private String phone;//手机号码

    private Integer type;//病种

    private String otherType;

    private List<Examination> examination;//病历

    private String other;
}
