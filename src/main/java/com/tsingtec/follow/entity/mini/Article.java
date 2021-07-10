package com.tsingtec.follow.entity.mini;

import com.tsingtec.follow.entity.BaseEntity;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * @Author lj
 * @Date 2021/7/9 14:58
 * @Version 1.0
 */
@Data
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "app_article")
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class Article extends BaseEntity {

    private String title;

    private String pic;

    private String des;

    private String content;

    @Type(type = "json")
    @Column(columnDefinition = "json comment '图文标签'")
    private List<String> tags;
}
