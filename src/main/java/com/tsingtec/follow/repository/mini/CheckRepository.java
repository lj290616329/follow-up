package com.tsingtec.follow.repository.mini;

import com.tsingtec.follow.entity.mini.Check;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckRepository extends JpaRepository<Check, Integer>, JpaSpecificationExecutor<Check> {

    @Modifying
    @Query("delete from Check a where a.id in (?1)")
    void deleteBatch(@Param(value = "ids") List<Integer> ids);

    List<Check> findByEnNameIn(List<String> enNames);
}