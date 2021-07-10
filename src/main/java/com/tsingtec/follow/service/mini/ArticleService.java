package com.tsingtec.follow.service.mini;

import com.tsingtec.follow.entity.mini.Article;
import com.tsingtec.follow.exception.BusinessException;
import com.tsingtec.follow.exception.code.BaseExceptionType;
import com.tsingtec.follow.repository.mini.ArticleRepository;
import com.tsingtec.follow.service.sys.AdminService;
import com.tsingtec.follow.utils.BeanMapper;
import com.tsingtec.follow.utils.BeanUtils;
import com.tsingtec.follow.vo.req.news.ArticleAddReqVO;
import com.tsingtec.follow.vo.req.news.ArticleUpdateReqVO;
import com.tsingtec.follow.vo.req.news.ArticlePageReqVO;
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
 * @Date 2021/5/28 17:28
 * @Version 1.0
 */
@Service
public class ArticleService {

    @Autowired
    private AdminService adminService;


    @Autowired
    private ArticleRepository articleRepository;

    public Article get(Integer id){
        return articleRepository.findById(id).orElse(null);
    }


    /**
     * 小程序端使用
     * @param article
     */
    @Transactional
    public void save(Article article) {
        articleRepository.save(article);
    }


    public Page<Article> findAll(ArticlePageReqVO vo) {
        Pageable pageable = PageRequest.of(vo.getPageNum()-1, vo.getPageSize(), Sort.Direction.DESC,"id");
        return articleRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<Predicate>();
            if (!StringUtils.isEmpty(vo.getTitle())){
                predicates.add(criteriaBuilder.like(root.get("title"),"%"+vo.getTitle()+"%"));
            }
            return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        },pageable);
    }



    @Transactional
    public void insert(ArticleAddReqVO vo){
        Article article = new Article();
        BeanMapper.mapExcludeNull(vo, article);
        articleRepository.save(article);
    }

    @Transactional
    public void update(ArticleUpdateReqVO vo){

        Article article = articleRepository.getOne(vo.getId());
        if(null==article){
            throw new BusinessException(BaseExceptionType.USER_ERROR,"您修改的图文不存在!");
        }
        BeanUtils.copyPropertiesIgnoreNull(vo,article);
        articleRepository.save(article);
    }


    @Transactional
    public void delete(List<Integer> ids) {
        articleRepository.deleteBatch(ids);
    }

    public List<Article> getAll() {
        return articleRepository.findAll();
    }
}
