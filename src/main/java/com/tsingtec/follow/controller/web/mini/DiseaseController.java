package com.tsingtec.follow.controller.web.mini;

import com.tsingtec.follow.entity.mini.Disease;
import com.tsingtec.follow.exception.DataResult;
import com.tsingtec.follow.service.mini.DiseaseService;
import com.tsingtec.follow.utils.BeanMapper;
import com.tsingtec.follow.vo.req.disease.DiseaseEditReqVO;
import com.tsingtec.follow.vo.req.disease.DiseasePageReqVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author lj
 * @Date 2021/7/9 15:59
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/manager")
@Api(tags = "管理模块-检查项目管理")
public class DiseaseController {

    @Autowired
    private DiseaseService diseaseService;

    @GetMapping("disease")
    public DataResult page(DiseasePageReqVO vo){
        Disease disease = BeanMapper.map(vo,Disease.class);
        Pageable pageable = PageRequest.of(vo.getPageNum()-1, vo.getPageSize(), Sort.Direction.DESC,"id");
        return DataResult.success(diseaseService.page(disease,pageable).map(d->{
            Disease parent = diseaseService.findById(d.getPid());
            d.setPidName(parent==null?"一级分类":parent.getTitle());
            return d;
        }));
    }

    @GetMapping("disease/all")
    public DataResult all(){
        return DataResult.success(diseaseService.findAll());
    }

    @GetMapping("disease/parent")
    public DataResult parent(){
        return DataResult.success(diseaseService.getParentDisease());
    }

    @GetMapping("disease/tree")
    public DataResult tree(){
        return DataResult.success(diseaseService.getTreeDisease());
    }

    @GetMapping("disease/{id}")
    public DataResult detail(@PathVariable("id")Integer id){
        Disease disease = diseaseService.findById(id);
        disease = disease==null?new Disease():disease;
        disease.setChildren(diseaseService.getAllTree(id));
        return DataResult.success(disease);
    }

    @PostMapping("disease")
    public DataResult add(@RequestBody DiseaseEditReqVO vo){
        log.info(vo.toString());
        Disease disease = BeanMapper.map(vo,Disease.class);
        diseaseService.save(disease);
        return DataResult.success();
    }

    @PutMapping("disease")
    public DataResult update(@RequestBody DiseaseEditReqVO vo){
        Disease disease = diseaseService.findById(vo.getId());
        BeanMapper.mapExcludeNull(vo,disease);
        diseaseService.save(disease);
        return DataResult.success();
    }

    @DeleteMapping("disease")
    public DataResult del(@RequestBody List<Integer> ids){
        diseaseService.delete(ids);
        return DataResult.success();
    }
    
}
