package com.tsingtec.follow.service.mini;

import com.google.common.collect.Lists;
import com.tsingtec.follow.constants.Constants;
import com.tsingtec.follow.entity.mini.Disease;
import com.tsingtec.follow.repository.mini.DiseaseRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author lj
 * @Date 2021/5/28 17:28
 * @Version 1.0
 */
@Service
public class DiseaseService {

    @Autowired
    private DiseaseRepository diseaseRepository;

    public Disease findById(Integer id){
        return diseaseRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Disease disease) {
        diseaseRepository.save(disease);
    }

    public Page<Disease> page(Disease disease,Pageable pageable) {
        return diseaseRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<Predicate>();
            if (StringUtils.isNotBlank(disease.getTitle())){
                predicates.add(criteriaBuilder.like(root.get("title"),"%"+disease.getTitle()+"%"));
            }
            return criteriaQuery.where(predicates.toArray(new Predicate[0])).getRestriction();
        },pageable);
    }

    public List<Disease> getParentDisease(){
        List<Disease> diseases = Lists.newArrayList();
        Disease parent = new Disease();
        parent.setId(0);
        parent.setTitle("原发病诊断");
        diseases.add(parent);
        diseases.addAll(diseaseRepository.findByPid(Constants.FATHER));
        return diseases;
    }


    public List<Disease> getAllTree(Integer id){
        List<Disease> diseases = diseaseRepository.findAll();
        diseases.removeIf(disease -> disease.getId() == id);
        Disease disease = new Disease();
        disease.setPid(0);
        disease.setId(-1);
        disease.setTitle("一级分类");
        diseases.add(disease);

        return getChildAll(0,diseases);
    }

    public List<Disease> getTreeDisease(){
        List<Disease> diseases = diseaseRepository.findAll();
        return getChildAll(Constants.FATHER,diseases);
    }

    private List<Disease> getChildAll(Integer pid, List<Disease> all){
        //子类
        List<Disease> children = all.stream().filter(x -> x.getPid()==pid).collect(Collectors.toList());

        //后辈中的非子类
        List<Disease> successor = all.stream().filter(x -> x.getPid()!=pid).collect(Collectors.toList());

        children.forEach(x ->{
                getChildAll(x.getId(),successor).forEach(
                        y -> {
                            x.getChildren().add(y);
                        }
                );
            }
        );
        return children;
    }





    @Transactional
    public void delete(List<Integer> ids) {
        List<Disease> diseass = diseaseRepository.findAllById(ids);
        diseass.forEach(diseas -> {
            diseas.setDelStatus(Constants.DELETE_FLAG);
        });

        diseaseRepository.saveAll(diseass);
    }

    public List<Disease> findAll() {
        return diseaseRepository.findAll();
    }
}
