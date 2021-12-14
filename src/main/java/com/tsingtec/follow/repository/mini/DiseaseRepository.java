package com.tsingtec.follow.repository.mini;

import com.tsingtec.follow.entity.mini.Disease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease, Integer>, JpaSpecificationExecutor<Disease> {
    List<Disease> findByPid(Integer pid);

    List<Disease> findByPidNot(Integer pid);
}