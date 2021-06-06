package com.tsingtec.follow.vo.resp.doctor;

import com.tsingtec.follow.entity.mini.Information;
import com.tsingtec.follow.entity.mini.ReviewPlan;
import lombok.Data;

import java.util.List;

/**
 * @Author lj
 * @Date 2021/6/2 16:25
 * @Version 1.0
 */
@Data
public class DoctorIndexRespVO {
    private List<Information> informations;
    private List<ReviewPlan> reviewPlans;
}
