package com.tsingtec.follow.vo.resp.review;

import com.tsingtec.follow.entity.Examination;
import com.tsingtec.follow.entity.mini.Information;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author lj
 * @Date 2021/12/10 11:57
 * @Version 1.0
 */
@Data
public class ReviewPlanDetailRespVO {

    private Integer id;

    private Information information;

    private List<Examination> examination;//病历

    private String reply;//医生回复信息

    private String other;//其他

    private LocalDateTime updateTime;//最后登录时间
}
