package com.tsingtec.follow.vo.resp.review;

import com.tsingtec.follow.entity.Examination;
import com.tsingtec.follow.entity.mini.Check;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * @Author lj
 * @Date 2021/7/27 17:06
 * @Version 1.0
 */
@Data
public class ReviewPlanRespVO {

    private Integer id;

    private Boolean show=false;

    private List<Examination> examination;//病历

    private LocalDate reviewTime;//复查时间

    private String remark;//备注

    private List<Check> checks;

}
