package com.tsingtec.follow.service.mini;

import com.tsingtec.follow.entity.mini.Review;
import com.tsingtec.follow.entity.mini.ReviewPlan;
import com.tsingtec.follow.repository.mini.ReviewPlanRepository;
import com.tsingtec.follow.repository.mini.ReviewRepository;
import com.tsingtec.follow.vo.req.review.ReviewReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author lj
 * @Date 2021/5/26 15:30
 * @Version 1.0
 */
@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewPlanRepository reviewPlanRepository;

    public Review findById(Integer id){
        return  reviewRepository.findById(id).orElse(null);
    }

    @Transactional
    public void add(ReviewReqVO vo){
        ReviewPlan reviewPlan = reviewPlanRepository.getOne(vo.getId());
        Review review = new Review();
        review.setExamine(vo.getExamine());
        review.setOther(vo.getOther());
        reviewPlan.setReview(reviewRepository.save(review));
        reviewPlanRepository.save(reviewPlan);
    }

    @Transactional
    public void update(ReviewReqVO vo) {
        Review review = reviewRepository.getOne(vo.getId());
        review.setExamine(vo.getExamine());
        review.setReply(vo.getReply());
        reviewRepository.save(review);
    }
}
