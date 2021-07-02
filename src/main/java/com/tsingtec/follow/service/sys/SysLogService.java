package com.tsingtec.follow.service.sys;

import com.tsingtec.follow.entity.sys.SysLog;
import com.tsingtec.follow.repository.sys.SysLogRepository;
import com.tsingtec.follow.vo.req.sys.log.SysLogPageReqVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author lj
 * @Date 2020/3/29 13:54
 * @Version 1.0
 */
@Service
public class SysLogService {

    @Autowired
    private SysLogRepository logRepository;


    public Page<SysLog> pageInfo(SysLogPageReqVO vo) {
        Pageable pageable = PageRequest.of(vo.getPageNum()-1, vo.getPageSize(), Sort.Direction.DESC,"id");
        return logRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<Predicate>();

            if (!StringUtils.isEmpty(vo.getOperation())){
                predicates.add(criteriaBuilder.like(root.get("operation"),"%"+vo.getOperation()+"%"));
            }

            if (!StringUtils.isBlank(vo.getUsername())){
                predicates.add(criteriaBuilder.equal(root.get("username"),vo.getUsername()));
            }

            if (null != vo.getAid()){
                predicates.add(criteriaBuilder.equal(root.get("aid"),vo.getAid()));
            }

            return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        },pageable);
    }

    @Transactional
    public void insert(SysLog sysLog) {
        logRepository.save(sysLog);
    }

    @Transactional
    public void delete(List<Integer> ids) {
        logRepository.deleteBatch(ids);
    }

}
