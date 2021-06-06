package com.tsingtec.follow.service.sys;

import com.tsingtec.follow.entity.sys.Menu;
import com.tsingtec.follow.entity.sys.Role;
import com.tsingtec.follow.exception.BusinessException;
import com.tsingtec.follow.exception.code.BaseExceptionType;
import com.tsingtec.follow.repository.sys.RoleRepository;
import com.tsingtec.follow.vo.req.sys.role.RoleAddReqVO;
import com.tsingtec.follow.vo.req.sys.role.RolePageReqVO;
import com.tsingtec.follow.vo.req.sys.role.RoleUpdateReqVO;
import com.tsingtec.follow.vo.resp.sys.menu.MenuRespNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private MenuService menuService;

	@Transactional
	public Role addRole(RoleAddReqVO vo) {
		Role role = new Role();
		role.setDescription(vo.getDescription());
		role.setName(vo.getName());
		role.setStatus(vo.getStatus());
		return roleRepository.save(role);
	}

	@Transactional
	public void updateRole(RoleUpdateReqVO vo) {
		Role role = roleRepository.getOne(vo.getId());
		if (null==role){
			log.error("传入 的 id:{}不合法",vo.getId());
			throw new BusinessException(BaseExceptionType.USER_ERROR,"id不合法");
		}

		BeanUtils.copyProperties(vo,role);

		List<Menu> menus = menuService.findAllById(vo.getMids());
		role.setMenus(new HashSet<>(menus));
		roleRepository.save(role);
	}

	@Transactional
	public Role findById(Integer id) {
		Role role = roleRepository.findById(id).get();
		if(null==role){
			return null;
		}
		Set<Menu> menus = role.getMenus();
		Set<Integer> checkList = menus.stream().map(menu -> menu.getId()).collect(Collectors.toSet());
		List<MenuRespNode> menuRespNodes = menuService.selectAllByTree();
		setheckced(menuRespNodes,checkList);
		role.setChilids(menuRespNodes);
		return roleRepository.findById(id).get();
	}

	private void setheckced(List<MenuRespNode> menuRespNodes, Set<Integer> checkList){
		for(MenuRespNode node:menuRespNodes){
			if(checkList.contains(node.getId()) && (node.getChildren()==null || node.getChildren().isEmpty())){
				node.setChecked(true);
			}
			setheckced((List<MenuRespNode>) node.getChildren(),checkList);
		}
	}


	public Page<Role> pageInfo(RolePageReqVO vo) {
		Pageable pageable = PageRequest.of(vo.getPageNum()-1, vo.getPageSize(), Sort.Direction.DESC,"id");
		return roleRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<Predicate>();

			if (!StringUtils.isEmpty(vo.getRoleName())){
				predicates.add(criteriaBuilder.like(root.get("name"),"%"+vo.getRoleName()+"%"));
			}

			if (null != vo.getStartTime()){
				predicates.add(criteriaBuilder.greaterThan(root.get("createTime"),vo.getStartTime()));
			}

			if (null != vo.getEndTime()){
				predicates.add(criteriaBuilder.lessThan(root.get("createTime"),vo.getEndTime()));
			}
			return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
		},pageable);
	}

	@Transactional
	public void deleteBatch(List<Integer> rids) {

		roleRepository.deleteBatch(rids);

		roleRepository.cancelMenuJoin(rids);

		roleRepository.cancelMenuJoin(rids);
	}

}
