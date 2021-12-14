package com.tsingtec.follow.vo.req.information;

import com.tsingtec.follow.entity.Examination;
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

    private List<Examination> examination;//病理

    private String otherType;

}
