package com.tsingtec.follow.entity.mini;

import com.tsingtec.follow.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * 复查计划
 * @Author lj
 * @Date 2021/5/28 16:53
 * @Version 1.0
 */
@Data
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@AllArgsConstructor
@NoArgsConstructor
@Proxy(lazy = false)
@Table(name = "app_review_plan")
public class ReviewPlan extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(columnDefinition ="comment '用户id'")//指定外键名称
    private MaUser maUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(columnDefinition ="comment '检查id'")//指定外键名称
    private Review review;

    private LocalDate reviewTime;//复查时间
}
