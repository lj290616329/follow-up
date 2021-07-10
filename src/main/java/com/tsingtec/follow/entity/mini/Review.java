package com.tsingtec.follow.entity.mini;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tsingtec.follow.entity.BaseEntity;
import com.tsingtec.follow.entity.Examine;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 复查信息
 * @Author lj
 * @Date 2021/5/27 15:42
 * @Version 1.0
 */
@Data
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@AllArgsConstructor
@NoArgsConstructor
@Proxy(lazy = false)
@Table(name = "app_review")
@TypeDef(name = "json", typeClass = JsonStringType.class)
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Review extends BaseEntity{

    @Type(type = "json")
    @Column(columnDefinition = "json comment '复查结果信息'")
    private Examine examine;//病历

    private String other;//其他

    private String reply;//医生回复信息
}
