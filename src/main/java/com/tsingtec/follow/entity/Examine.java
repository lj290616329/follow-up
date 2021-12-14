package com.tsingtec.follow.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * @Author lj
 * @Date 2021/5/27 15:34
 * @Version 1.0
 */
@Data
public class Examine {
    @JSONField(ordinal=1)
    private List<String> cbc = Lists.newArrayList();//血常规
    @JSONField(ordinal=2)
    private List<String> biochemistry= Lists.newArrayList();;//生化
    @JSONField(ordinal=3)
    private List<String> dic= Lists.newArrayList();;//凝血
    @JSONField(ordinal=4)
    private List<String> swelling= Lists.newArrayList();;//肿标
    @JSONField(ordinal=5)
    private List<String> bmode= Lists.newArrayList();;//b超摄影图
    @JSONField(ordinal=6)
    private List<String> ct= Lists.newArrayList();;//ct摄影图
    @JSONField(ordinal=7)
    private List<String> mri= Lists.newArrayList();;//MRI
}
