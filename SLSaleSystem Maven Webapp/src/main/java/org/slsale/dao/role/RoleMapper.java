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

	/**
	 * getRole
	 * @param role
	 * @return
	 * @throws Exception
	 */
	Role getRole(Role role) throws Exception;

	/**
	 * getRoleR
	 * @param role
	 * @return
	 * @throws Exception
	 */
	Role getRoleR(Role role) throws Exception;

	/**
	 * addRole
	 */
	int addRole(Role role) throws Exception;

	/**
	 * modifyRole
	 * @param role
	 * @return
	 * @throws Exception
	 */
	int modifyRole(Role role) throws Exception;

	/**
	 * deleteRole
	 * @param role
	 * @return
	 * @throws Exception
	 */
	int deleteRole(Role role) throws Exception;

	/**
	 * getRoleIdAndNameList
	 * @return
	 * @throws Exception
	 */
	List<Role> getRoleIdAndNameList() throws Exception;
}
