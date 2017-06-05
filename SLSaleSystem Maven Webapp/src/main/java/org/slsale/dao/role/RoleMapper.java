package org.slsale.dao.role;

import java.util.List;

import org.slsale.pojo.Role;

public interface RoleMapper {
	/**
	 * 获取角色列表
	 * @return
	 * @throws Exception
	 */
	List<Role> getRoleList() throws Exception;
}
