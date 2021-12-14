package com.tsingtec.follow.repository.mini;

import com.tsingtec.follow.entity.mini.Information;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InformationRepository extends JpaRepository<Information, Integer>, JpaSpecificationExecutor<Information> {

    Information findByMaUser_Id(Integer uid);

    List<Information> getByDoctor_idOrderByExaminationAsc(Integer did);

    Information findByPhone(String phone);

    @Modifying
    @Query("delete from Information a where a.id in (?1)")
    void deleteBatch(@Param(value = "ids") List<Integer> ids);
}