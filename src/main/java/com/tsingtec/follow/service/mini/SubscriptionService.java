package com.tsingtec.follow.service.mini;

import com.tsingtec.follow.entity.mini.Subscription;
import com.tsingtec.follow.repository.mini.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author lj
 * @Date 2021/5/28 17:28
 * @Version 1.0
 */
@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public Subscription findByUidAndTmplId(Integer uid,String temlId){
        return subscriptionRepository.findByUidAndTmplId(uid,temlId);
    }

    @Transactional
    public void save(Subscription subscription) {
        subscriptionRepository.save(subscription);
    }

}
