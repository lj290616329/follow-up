package com.tsingtec.follow.service.mini;

import com.tsingtec.follow.entity.mini.ReviewPlan;
import com.tsingtec.follow.repository.mini.ReviewPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author lj
 * @Date 2021/5/26 15:30
 * @Version 1.0
 */
@Service
public class ReviewPlanService {

    @Autowired
    private ReviewPlanRepository reviewPlanRepository;

    public ReviewPlan findById(Integer id){
        return  reviewPlanRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(ReviewPlan reviewPlan){
        reviewPlanRepository.save(reviewPlan);
    }

    public List<ReviewPlan> findByDid(Integer did) {
        return reviewPlanRepository.getByMaUser_DidAndReview_ExamineNotNullAndReview_ReplyIsNullOrderByReview_CreateTimeDesc(did);
    }
}
