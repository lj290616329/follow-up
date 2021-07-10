package com.tsingtec.follow.entity.mini;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Lists;
import com.tsingtec.follow.entity.BaseEntity;
import com.tsingtec.follow.entity.Examine;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.util.List;

/**
 * @Author lj
 * @Date 2021/5/26 11:50
 * @Version 1.0
 */
@Data
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@AllArgsConstructor
@NoArgsConstructor
@Proxy(lazy = false)
@Table(name = "ma_common_information")
@TypeDef(name = "json", typeClass = JsonStringType.class)
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Information extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(columnDefinition ="integer comment '用户信息id'")
    private MaUser maUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(columnDefinition ="comment '医生id'")//指定外键名称
    private Doctor doctor;

    private String name;//姓名
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
