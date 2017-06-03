package org.slsale.dao.user;

import org.slsale.pojo.User;

public interface UserMapper {
	/**
	 * 用户登陆，获取登录用户
	 * @param user 前台传入的user对象
	 * @return 登陆后的对象
	 * @throws Exception
	 */
	User getLoginUser(User user) throws Exception;

	/**
	 * 判断userCode是否存在
	 * @param user
	 * @return 大于0表示存在，等于0表示不存在
	 * @throws Exception
	 */
	int userCodeIsExist(User user) throws Exception;

	/**
	 * 修改用户信息
	 * @param user
	 * @return 修改是否成功
	 * @throws Exception
	 */
	int modifyUser(User user) throws Exception;
}
