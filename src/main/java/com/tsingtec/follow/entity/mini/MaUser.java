package com.tsingtec.follow.entity.mini;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * @Date 2020/4/4 22:48
 * @Version 1.0
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Proxy(lazy = false)
@Table(name = "ma_common_user")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class MaUser extends BaseEntity {

    @JsonIgnore
    @Column(unique = true)
    private String openId;

    private String nickName;

    private String gender;

    private String language;

    private String city;

    private String province;

    private String country;

    private String avatarUrl;

    @JsonIgnore
    @Column(unique = true)
    private String unionId;
}
