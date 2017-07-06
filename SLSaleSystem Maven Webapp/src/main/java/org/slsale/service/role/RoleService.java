package org.slsale.service.role;

import java.util.List;

import org.slsale.pojo.Role;

public interface RoleService {

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

	/**
	 * 修改role和user表roleName，需要事务控制
	 * @param role
	 * @return
	 * @throws Exception
	 */
	boolean lu_modifyRole(Role role) throws Exception;

	/**
	 * 获取roleList
	 * @return
	 * @throws Exception
	 */
	List<Role> getRoleList() throws Exception;
}
