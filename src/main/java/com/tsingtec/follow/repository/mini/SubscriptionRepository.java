package com.tsingtec.follow.repository.mini;

import com.tsingtec.follow.entity.mini.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer>, JpaSpecificationExecutor<Subscription> {

    @Modifying
    @Query("delete from Subscription a where a.id in (?1)")
    void deleteBatch(@Param(value = "ids") List<Integer> ids);

    Subscription findByUidAndTmplId(Integer uid,String tmplId);
}