package com.tsingtec.follow.vo.req.information;

import com.google.common.collect.Lists;
import com.tsingtec.follow.entity.Examine;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author lj
 * @Date 2021/6/3 10:13
 * @Version 1.0
 */
@Data
public class InfoEditReqVO {

    @NotNull(message = "id不能为空")
    private Integer id;

    private Integer type;

    private List<String> pathology= Lists.newArrayList();//病理

    private Examine examine = new Examine();//病历
}
