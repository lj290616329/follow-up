package com.tsingtec.follow.service.mini;

import com.tsingtec.follow.constants.Constants;
import com.tsingtec.follow.entity.mini.Check;
import com.tsingtec.follow.repository.mini.CheckRepository;
import com.tsingtec.follow.utils.ChangeToPinYinJP;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author lj
 * @Date 2021/5/28 17:28
 * @Version 1.0
 */
@Service
public class CheckService {

    @Autowired
    private CheckRepository checkRepository;

    public Check get(Integer id){
        return checkRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Check check) {
        if(StringUtils.isBlank(check.getEnName())){
            check.setEnName(ChangeToPinYinJP.toFirstChar(check.getName()));
        }
        checkRepository.save(check);
    }

    public Page<Check> page(Check check,Pageable pageable) {
        return checkRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<Predicate>();
            if (StringUtils.isNotBlank(check.getName())){
                predicates.add(criteriaBuilder.like(root.get("name"),"%"+check.getName()+"%"));
            }
            return criteriaQuery.where(predicates.toArray(new Predicate[0])).getRestriction();
        },pageable);
    }

    @Transactional
    public void delete(List<Integer> ids) {
        List<Check> checks = checkRepository.findAllById(ids);

        checks.forEach(check -> {
            check.setDelStatus(Constants.DELETE_FLAG);
        });

        checkRepository.saveAll(checks);
    }

    public List<Check> getAll() {
        return checkRepository.findAll();
    }
}
