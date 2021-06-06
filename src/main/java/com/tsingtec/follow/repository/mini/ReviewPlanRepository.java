package com.tsingtec.follow.repository.mini;

import com.tsingtec.follow.entity.mini.ReviewPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewPlanRepository extends JpaRepository<ReviewPlan, Integer>, JpaSpecificationExecutor<ReviewPlan> {


    /**
     * 根据uid 获取本人的复查计划~
     * @param uid
     * @return
     */
    List<ReviewPlan> getByMaUser_IdOrderByReviewTimeAsc(@Param("uid") Integer uid);

    /**
     * 根据医生id 获取未回复的复查结果
     * @param did
     * @return
     */
    List<ReviewPlan> getByMaUser_DidAndReview_ExamineNotNullAndReview_ReplyIsNullOrderByReview_CreateTimeDesc(@Param("did") Integer did);


    //List<ReviewPlan> getByMaUser_DidAndReview_ReplyIsNullOrderByCreateTimeDesc(@Param("did") Integer did);
}