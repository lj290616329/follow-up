package com.tsingtec.follow.entity;

import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author lj
 * @Date 2021/5/27 15:34
 * @Version 1.0
 */
@Data
public class Examination implements Serializable {
    private String name;
    private String enName;
    private List<String> pics = Lists.newArrayList();
}
