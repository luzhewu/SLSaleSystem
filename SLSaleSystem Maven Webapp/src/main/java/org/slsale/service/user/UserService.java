package org.slsale.service.user;

import org.slsale.pojo.user.User;

public interface UserService {
	/**
	 * 用户登陆
	 * @param user
	 * @return
	 * @throws Exception
	 */
	User login(User user) throws Exception;

	/**
	 * 注册用户
	 * @param user
	 * @return
	 * @throws Exception
	 */
	int register(User user) throws Exception;
}
