package com.tsingtec.follow.entity.mini;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tsingtec.follow.entity.BaseEntity;
import com.tsingtec.follow.entity.Examination;
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
@DynamicInsert
@DynamicUpdate
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
    private String otherType;//其他病种

    @Type(type = "json")
    @Column(columnDefinition = "json comment '复查结果信息'")
    private List<Examination> examination;//病历

    private String other;

    @Transient
    private List<Check> checks;
}
