package org.slsale.dao.user;

import java.util.List;

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
	 * 判断loginCode是否存在
	 * @param user
	 * @return 大于0表示存在，等于0表示不存在
	 * @throws Exception
	 */
	int loginCodeIsExist(User user) throws Exception;

	/**
	 * 修改用户信息
	 * @param user
	 * @return 修改是否成功
	 * @throws Exception
	 */
	int modifyUser(User user) throws Exception;

	/**
	 * 获取总记录数
	 * @param user
	 * @return
	 * @throws Exception
	 */
	int count(User user) throws Exception;

	/**
	 * 获取分页用户列表
	 * @param user
	 * @return
	 */
	List<User> getUserList(User user) throws Exception;

	/**
	 * 新增用户
	 * @param user
	 * @return
	 */
	int addUser(User user) throws Exception;

	/**
	 * 删除用户图片
	 * @param user
	 * @return
	 * @throws Exception
	 */
	int delUserPic(User user) throws Exception;

	/**
	 * 通过id查找对应的user
	 * @param user
	 * @return
	 * @throws Exception
	 */
	User getUserById(User user) throws Exception;

	/**
	 * 删除用户信息
	 * @param user
	 * @return
	 */
	int delUser(User user);

	/**
	 * getUserListBySearch
	 * @param user
	 * @return
	 */
	public List<User> getUserListBySearch(User user) throws Exception;

	/**
	 * modifyUserRole
	 * @param user
	 * @return
	 * @throws Exception
	 */
	int modifyUserRole(User user) throws Exception;

}
