package com.tsingtec.follow.service.mini;

import com.tsingtec.follow.entity.mini.MaUser;
import com.tsingtec.follow.exception.BusinessException;
import com.tsingtec.follow.exception.code.BaseExceptionType;
import com.tsingtec.follow.repository.mini.MaUserRepository;
import com.tsingtec.follow.utils.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import org.springframework.transaction.annotation.Transactional;

@Service
public class MaUserService{

    @Autowired
    private MaUserRepository maUserRepository;

    public MaUser findByOpenId(String openId) {
        System.out.println(openId);
        System.out.println(StringUtils.hasText(openId));
        if(!StringUtils.hasText(openId)){
            throw new BusinessException(BaseExceptionType.USER_ERROR,"非法openid");
        }
        return maUserRepository.findByOpenId(openId);
    }

    public MaUser get(Integer id) {
        return maUserRepository.findById(id).orElse(null);
    }

    @Transactional
    public MaUser save(MaUser maUser) {
        MaUser saveUser = findByOpenId(maUser.getOpenId());
        saveUser = (saveUser != null) ? saveUser : new MaUser();
        BeanMapper.mapExcludeNull(maUser,saveUser);
        return maUserRepository.save(saveUser);
    }
}
