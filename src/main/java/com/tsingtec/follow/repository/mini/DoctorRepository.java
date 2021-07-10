package com.tsingtec.follow.repository.mini;

import com.tsingtec.follow.entity.mini.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer>, JpaSpecificationExecutor<Doctor> {

    Doctor findByMaUser_Id(Integer uid);
    Doctor findByPhone(String phone);

    @Modifying
    @Query("delete from Doctor a where a.id in (?1)")
    void deleteBatch(@Param(value = "ids") List<Integer> ids);
}