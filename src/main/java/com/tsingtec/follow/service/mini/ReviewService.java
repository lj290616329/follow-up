package com.tsingtec.follow.service.mini;

import com.tsingtec.follow.entity.mini.Review;
import com.tsingtec.follow.repository.mini.ReviewRepository;
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

    public Review findById(Integer id){
        return  reviewRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Review review){
        reviewRepository.save(review);
    }

}
