package org.slsale.service.role;

import java.util.List;

import javax.annotation.Resource;

import org.slsale.dao.role.RoleMapper;
import org.slsale.dao.user.UserMapper;
import org.slsale.pojo.Role;
import org.slsale.pojo.User;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
	@Resource
	private RoleMapper roleMapper;
	@Resource
	private UserMapper userMapper;

	@Override
	public List<Role> getRoleList() throws Exception {
		return roleMapper.getRoleList();
	}

	@Override
	public Role getRole(Role role) throws Exception {
		return roleMapper.getRole(role);
	}

	@Override
	public Role getRoleR(Role role) throws Exception {
		return roleMapper.getRoleR(role);
	}

	@Override
	public int addRole(Role role) throws Exception {
		return roleMapper.addRole(role);
	}

	@Override
	public int modifyRole(Role role) throws Exception {
		return roleMapper.modifyRole(role);
	}

	@Override
	public int deleteRole(Role role) throws Exception {
		return roleMapper.deleteRole(role);
	}

	@Override
	public List<Role> getRoleIdAndNameList() throws Exception {
		return roleMapper.getRoleIdAndNameList();
	}

	@Override
	public boolean lu_modifyRole(Role role) throws Exception {
		roleMapper.modifyRole(role);
		User user = new User();
		user.setRoleId(role.getId());
		user.setRoleName(role.getRoleName());
		if (user.getRoleName() != null && !("".equals(user.getRoleName()))) {
			userMapper.modifyUserRole(user);
		}
		return true;
	}

}
