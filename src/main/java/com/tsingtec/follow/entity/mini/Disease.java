package com.tsingtec.follow.entity.mini;

import com.google.common.collect.Lists;
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
import java.util.List;

/**
 * 病种
 * @Author lj
 * @Date 2021/12/10 16:08
 * @Version 1.0
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "app_disease")
@Where(clause="del_status="+ Constants.NO_DELETE_FLAG)
public class Disease extends BaseEntity {

    private String title;//检测名称

    private Integer pid = Constants.FATHER;

    private Short delStatus = Constants.NO_DELETE_FLAG;

    @Transient
    private String pidName;

    private Boolean optional = false;//是否选填项目

    @Transient
    private List<Disease> children= Lists.newArrayList();

}
