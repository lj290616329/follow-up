package com.tsingtec.follow.repository.mini;

import com.tsingtec.follow.entity.mini.Information;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InformationRepository extends JpaRepository<Information, Integer>, JpaSpecificationExecutor<Information> {

    Information findByMaUser_Id(Integer uid);

    List<Information> getByMaUser_DidOrderByPathologyAsc(Integer did);
}