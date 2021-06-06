package com.tsingtec.follow.service.mini;

import com.tsingtec.follow.entity.mini.Information;
import com.tsingtec.follow.repository.mini.InformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author lj
 * @Date 2021/5/26 15:30
 * @Version 1.0
 */
@Service
public class InformationService {

    @Autowired
    private InformationRepository informationRepository;

    public Information findById(Integer id){
        return  informationRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Information information){
        informationRepository.save(information);
    }

    public Information findByUid(Integer uid) {
        return informationRepository.findByMaUser_Id(uid);
    }


    public List<Information> getByDid(Integer did){
        return informationRepository.getByMaUser_DidOrderByPathologyAsc(did);
    }
}
