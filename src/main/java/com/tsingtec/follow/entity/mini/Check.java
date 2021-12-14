package com.tsingtec.follow.entity.mini;

import com.tsingtec.follow.constants.Constants;
import com.tsingtec.follow.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 检测项目
 * @Author lj
 * @Date 2021/12/9 14:22
 * @Version 1.0
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "app_check")
@Where(clause="del_status="+ Constants.NO_DELETE_FLAG)
public class Check extends BaseEntity {

    private String name;//检测名称

    private String enName;//检测英文名

    private String category;//所属类别

    private Short delStatus = Constants.NO_DELETE_FLAG;


    @Transient
    private Boolean checked = false;

}
