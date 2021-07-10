package com.tsingtec.follow.repository.mini;

import com.tsingtec.follow.entity.mini.ReviewPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewPlanRepository extends JpaRepository<ReviewPlan, Integer>, JpaSpecificationExecutor<ReviewPlan> {


    /**
     * 根据uid 获取本人的复查计划~
     * @param iid
     * @return
     */
    List<ReviewPlan> findByInformation_IdOrderByReviewTimeDesc(@Param("information_Id") Integer iid);


    ReviewPlan getTopByInformation_IdAndReviewIsNullOrderByReviewTimeAsc(@Param("information_Id") Integer iid);

    ReviewPlan getTopByInformation_IdAndReviewIsNotNullAndReview_ReplyIsNotNullOrderByReview_UpdateTimeDesc(@Param("information_Id") Integer iid);

    /**
     * 根据医生id 获取未回复的复查结果
     * @param did
     * @return
     */
    List<ReviewPlan> getByInformation_Doctor_IdAndReview_ExamineNotNullAndReview_ReplyIsNullOrderByReview_CreateTimeDesc(@Param("did") Integer did);

    @Modifying
    @Query("delete from ReviewPlan a where a.id in (?1)")
    void deleteBatch(@Param(value = "ids") List<Integer> ids);



    //List<ReviewPlan> getByInformation_Doctor_IdReview_ExamineNotNullAndReview_ReplyIsNullOrderByReview_CreateTimeDesc(@Param("did") Integer did);//     MaUser_DidAndReview_ReplyIsNullOrderByCreateTimeDesc(@Param("did") Integer did);
}