package com.tsingtec.follow.repository.mini;

import com.tsingtec.follow.entity.mini.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer>, JpaSpecificationExecutor<Doctor> {

    Doctor findByMaUser_Id(Integer uid);
    Doctor findByPhone(String phone);
}