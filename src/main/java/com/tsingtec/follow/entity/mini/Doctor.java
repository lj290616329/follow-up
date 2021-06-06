package com.tsingtec.follow.entity.mini;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tsingtec.follow.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

/**
 * @Author lj
 * @Date 2021/5/27 11:39
 * @Version 1.0
 */
@Data
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@AllArgsConstructor
@NoArgsConstructor
@Proxy(lazy = false)
@Table(name = "app_doctor")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Doctor extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(columnDefinition ="comment '用户id'")//指定外键名称
    private MaUser maUser;//用户id
    private String name; //姓名
    private String pic;//头像
    private String phone; //电话
    private String goodAt; //擅长
    private String des; //简介
}
