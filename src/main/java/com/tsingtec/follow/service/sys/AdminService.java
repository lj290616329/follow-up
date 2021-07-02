package com.tsingtec.follow.service.sys;

import com.tsingtec.follow.entity.sys.Admin;
import com.tsingtec.follow.entity.sys.Role;
import com.tsingtec.follow.exception.BusinessException;
import com.tsingtec.follow.exception.code.BaseExceptionType;
import com.tsingtec.follow.repository.sys.AdminRepository;
import com.tsingtec.follow.repository.sys.RoleRepository;
import com.tsingtec.follow.vo.req.sys.admin.AdminAddReqVO;
import com.tsingtec.follow.vo.req.sys.admin.AdminPageReqVO;
import com.tsingtec.follow.vo.req.sys.admin.AdminPwdReqVO;
import com.tsingtec.follow.vo.req.sys.admin.AdminUpdateReqVO;
import com.tsingtec.follow.vo.resp.sys.admin.AdminRoleRespVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author lj
 * @Date 2020/3/29 13:54
 * @Version 1.0
 */
@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private RoleRepository roleRepository;

    private static final String HASH_ALGORITHM = "SHA-256";
    private static final int HASH_INTERATIONS = 1024;
    private static final int SALT_SIZE = 8;

    public static String generateSalt() {
        int byteLen = SALT_SIZE >> 1;
        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
        return secureRandom.nextBytes(byteLen).toHex();
    }

    /**
     * 获取加密后的密码，需要指定 hash迭代的次数
     * @param password       需要加密的密码
     * @param salt           盐
     * @return 加密后的密码
     */
    public static String encryptPassword(String password, String salt) {
        SimpleHash hash = new SimpleHash(HASH_ALGORITHM, password, salt, HASH_INTERATIONS);
        return hash.toString();
    }

    public Admin findById(Integer id) {
        return adminRepository.findById(id).get();
    }

    @Transactional
    public void save(Admin admin) {
        adminRepository.save(admin);
    }

    public Page<Admin> pageInfo(AdminPageReqVO vo) {
        Pageable pageable = PageRequest.of(vo.getPageNum()-1, vo.getPageSize(), Sort.Direction.DESC,"id");
        return adminRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<Predicate>();

            if (!StringUtils.isEmpty(vo.getName())){
                predicates.add(criteriaBuilder.like(root.get("name"),"%"+vo.getName()+"%"));
            }

            if (!StringUtils.isBlank(vo.getLoginName())){
                predicates.add(criteriaBuilder.like(root.get("loginName"),"%"+vo.getLoginName()+"%"));
            }
            return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        },pageable);
    }

    @Transactional
    public void insert(AdminAddReqVO vo) {
        Admin admin = findByLoginName(vo.getLoginName());
        if(admin!=null){
            throw new BusinessException(BaseExceptionType.USER_ERROR,"该登录账号已存在,请修改后再保存");
        }
        admin = new Admin();
        String salt = generateSalt();
        String password = encryptPassword(vo.getPassword(),salt);
        admin.setSalt(salt);
        admin.setName(vo.getName());
        admin.setLoginName(vo.getLoginName());
        admin.setPassword(password);
        admin.setStatus(vo.getStatus());
        adminRepository.save(admin);
    }

    public Admin findByLoginName(String loginName) {
        return adminRepository.findByLoginName(loginName);
    }

    @Transactional
    public void update(AdminUpdateReqVO vo) {
        Admin admin = findByLoginName(vo.getLoginName());
        //不是本身这个账号
        if(admin!=null && !admin.getId().equals(vo.getId())){
            throw new BusinessException(BaseExceptionType.USER_ERROR,"该登录账号已存在,请修改后再保存");
        }
        admin = adminRepository.getOne(vo.getId());
        String salt = generateSalt();
        String password = encryptPassword(vo.getPassword(),salt);
        admin.setSalt(salt);
        admin.setName(vo.getName());
        admin.setLoginName(vo.getLoginName());
        admin.setPassword(password);
        admin.setStatus(vo.getStatus());
        adminRepository.save(admin);
    }

    @Transactional
    public void updatePwd(Integer id, AdminPwdReqVO vo) {
        Admin admin = adminRepository.getOne(id);
        String salt = admin.getSalt();
        if(admin.getPassword().equals(encryptPassword(vo.getOldPwd(),salt))){
            String password = encryptPassword(vo.getNewPwd(),salt);
            admin.setPassword(password);
            if(!StringUtils.isEmpty(vo.getName())){
                admin.setName(vo.getName());
            }
            adminRepository.save(admin);
        }else{
            throw new BusinessException(BaseExceptionType.USER_ERROR,"老密码不对");
        }
    }

    @Transactional
    public void deleteBatch(List<Integer> aids) {
        adminRepository.deleteBatch(aids);
    }

    private List<Integer>  getRidsByAid(Integer aid){
        Admin admin = findById(aid);
        Set<Role> roles = admin.getRoles();
        List<Integer> rids = roles.stream().map(role -> role.getId()).collect(Collectors.toList());
        return rids;
    }

    public AdminRoleRespVO getAdminRole(Integer aid) {
        List<Role> roles = roleRepository.findAll();
        List<Integer> rids = getRidsByAid(aid);
        AdminRoleRespVO adminRoleRespVO = new AdminRoleRespVO();
        adminRoleRespVO.setAllRole(roles);
        adminRoleRespVO.setOwnRoles(rids);
        return adminRoleRespVO;
    }

    @Transactional
    public void setAdminRole(Integer aid, List<Integer> roleIds) {
        List<Role> roles = roleRepository.findAllById(roleIds);
        Admin admin = findById(aid);
        admin.setRoles(new HashSet<>(roles));
        adminRepository.save(admin);
    }
}
