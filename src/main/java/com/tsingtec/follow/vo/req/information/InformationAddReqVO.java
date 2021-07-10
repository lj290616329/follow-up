package com.tsingtec.follow.vo.req.information;

import com.google.common.collect.Lists;
import com.tsingtec.follow.entity.Examine;
import com.tsingtec.follow.entity.mini.Doctor;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
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

    @Type(type = "json")
    @Column(columnDefinition = "json comment '病理'")
    private List<String> pathology= Lists.newArrayList();//病理

    @Type(type = "json")
    @Column(columnDefinition = "json comment '个人病历信息'")
    private Examine examine = new Examine();//病历

    private String other;
}
