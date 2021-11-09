package com.tsingtec.follow.entity.mini;

import com.tsingtec.follow.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 订阅id表
 * @Author lj
 * @Date 2021/11/3 10:51
 * @Version 1.0
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "app_subscription")
public class Subscription extends BaseEntity {

    private String tmplId;//模板id

    private Integer uid; //用户id

    private Boolean accept = true;//是否订阅

}
